package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    EditText cellText1;
    EditText cellText2;
    EditText resultText;
    DBManager dbManager;
    Button sqliteInsertBtn;
    Button excellInsertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cellText1 = (EditText) findViewById(R.id.edit_cell1);
        cellText2 = (EditText) findViewById(R.id.edit_cell2);
        resultText = (EditText) findViewById(R.id.edit_result);

        sqliteInsertBtn = (Button) findViewById(R.id.btn_insert);

        dbManager = new DBManager(this);
        dbManager.open();

        sqliteInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cell1 = cellText1.getText().toString();
                String cell2 = cellText2.getText().toString();

                dbManager.insert(cell1, cell2);

                cellText1.setText("");
                cellText2.setText("");
            }
        });

        excellInsertBtn = (Button) findViewById(R.id.btn_excelinsert);
        excellInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbManager.fetch();
//                exportToExcel(cursor);
//                onWriteClick(v);
                onExcelClick(cursor);
            }
        });
    }

    public void onExcelClick(Cursor cursor) {
        printlnToUser("writing xlsx file");
        //XXX: Using blank template file as a workaround to make it work
        //Original library contained something like 80K methods and I chopped it to 60k methods
        //so, some classes are missing, and some things not working properly
        try {
            AssetManager am = this.getAssets();
            InputStream inStream;

            inStream = am.open("test_sample_sum.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(inStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
                if (cursor.moveToFirst()) {
                    do {
                        String test1 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXCEL_CELL1));
                        String test2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXCEL_CELL2));

                        int i = cursor.getPosition() + 7;

                        Row row;
                        Cell cell_t1;
                        CellStyle cs1 = workbook.createCellStyle();
                        int testNum1 = (int) (Math.random() * 10);
                        row = sheet.createRow(i);
                        cell_t1 = row.createCell(10);
                        cell_t1.setCellValue(test1);
                        cell_t1.setCellStyle(cs1);

                        int testNum2 = (int) (Math.random() * 10);
                        Cell cell_t2;
                        cell_t2 = row.createCell(11);
                        cell_t2.setCellValue(test2);
                        cell_t2.setCellStyle(cs1);

                } while (cursor.moveToNext());
            }
            cursor.close();
            String outFileName = "testsample.xlsx";
            printlnToUser("writing file " + outFileName);
            File cacheDir = getCacheDir();
            File outFile = new File(cacheDir, outFileName);
            OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            printlnToUser("sharing file...");
            share(outFileName, getApplicationContext());
        } catch (Exception e) {
            /* proper exception handling to be here */
            printlnToUser(e.toString());
        }


    }

    private void share(String fileName, Context context) {
        Uri fileUri = Uri.parse("content://" + getPackageName() + "/" + fileName);
        printlnToUser("sending " + fileUri.toString() + " ...");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/octet-stream");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

    private void printlnToUser(String str) {
        final String string = str;
        if (resultText.length() > 8000) {
            CharSequence fullOutput = resultText.getText();
            fullOutput = fullOutput.subSequence(5000, fullOutput.length());
            resultText.setText(fullOutput);
            resultText.setSelection(fullOutput.length());
        }
        resultText.append(string + "\n");
    }
}
