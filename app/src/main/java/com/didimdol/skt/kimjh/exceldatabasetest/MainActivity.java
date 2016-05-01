package com.didimdol.skt.kimjh.exceldatabasetest;

import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText cellText1;
    EditText cellText2;
    DBManager dbManager;
    Button sqliteInsertBtn;
    Button excellInsertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cellText1 = (EditText)findViewById(R.id.edit_cell1);
        cellText2 = (EditText)findViewById(R.id.edit_cell2);

        sqliteInsertBtn = (Button)findViewById(R.id.btn_insert);

        dbManager = new DBManager(this);
        dbManager.open();

        sqliteInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cell1 = cellText1.getText().toString();
                String cell2 = cellText2.getText().toString();

                dbManager.insert(cell1, cell2);
            }
        });

        excellInsertBtn = (Button)findViewById(R.id.btn_excelinsert);
        excellInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbManager.fetch();
                exportToExcel(cursor);
            }
        });
    }

    private void exportToExcel(Cursor cursor) {

        String fileName = "TestExcel.xlsx";
        File copyFile = Environment.getExternalStorageDirectory();

        File directory = new File(copyFile.getAbsolutePath() + "/ExcelTest");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }
    }
}
