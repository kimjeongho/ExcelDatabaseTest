package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kimjh on 2016-04-30.
 */
public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }//??

    public void insert(String cell1, String cell2){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EXCEL_CELL1,cell1);
        contentValues.put(DatabaseHelper.EXCEL_CELL2,cell2);
        database.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.EXCEL_CELL1, DatabaseHelper.EXCEL_CELL2 };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
