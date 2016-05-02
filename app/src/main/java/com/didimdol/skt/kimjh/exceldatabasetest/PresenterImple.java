package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tenqube on 2016-05-02.
 */
public class PresenterImple implements Presenter {
    private MainActivity mActivity;
    private Model mModel;

    public PresenterImple(MainActivity mActivity){
        this.mActivity = mActivity;
        mModel = new Model(mActivity);
    }

    @Override
    public void onExcelClick() {
        Cursor cursor = mModel.excelInsert();
        try {
            AssetManager am = mActivity.getAssets();
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
                    row = sheet.createRow(i);
                    cell_t1 = row.createCell(10);
                    cell_t1.setCellValue(test1);
                    cell_t1.setCellStyle(cs1);

                    Cell cell_t2;
                    cell_t2 = row.createCell(11);
                    cell_t2.setCellValue(test2);
                    cell_t2.setCellStyle(cs1);

                } while (cursor.moveToNext());
            }
            cursor.close();
            String outFileName = "testsample.xlsx";
            mActivity.printlnToUser("writing file " + outFileName);
            File cacheDir = mActivity.getCacheDir();
            File outFile = new File(cacheDir, outFileName);
            OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            mActivity.printlnToUser("sharing file...");
            share(outFileName, mActivity.getApplicationContext());
        } catch (Exception e) {
            /* proper exception handling to be here */
            mActivity.printlnToUser(e.toString());
        }
    }

    @Override
    public void share(String fileName, Context context) {
        Uri fileUri = Uri.parse("content://" + mActivity.getPackageName() + "/" + fileName);
        mActivity.printlnToUser("sending " + fileUri.toString() + " ...");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/octet-stream");
        mActivity.startActivity(Intent.createChooser(shareIntent, mActivity.getResources().getText(R.string.send_to)));
    }

    @Override
    public void dataSet(String cell1, String cell2) {
        mModel.insert(cell1, cell2);
    }


}
