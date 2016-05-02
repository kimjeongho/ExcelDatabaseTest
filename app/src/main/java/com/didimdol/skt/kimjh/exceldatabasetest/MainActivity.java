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

    Presenter mPresenter;

    EditText cellText1;
    EditText cellText2;
    EditText resultText;
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

        mPresenter = new PresenterImple(MainActivity.this);

        sqliteInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cell1 = cellText1.getText().toString();
                String cell2 = cellText2.getText().toString();

               mPresenter.dataSet(cell1,cell2);

                cellText1.setText("");
                cellText2.setText("");
            }
        });

        excellInsertBtn = (Button) findViewById(R.id.btn_excelinsert);
        excellInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onExcelClick();
            }
        });
    }

    public void printlnToUser(String str) {
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
