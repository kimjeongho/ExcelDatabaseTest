package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimjh on 2016-04-30.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "TEST";

    // Table columns
    public static final String _ID = "_id";
    public static final String EXCEL_CELL1 = "cell1";
    public static final String EXCEL_CELL2 = "cell2";

    // Database Information
    static final String DB_NAME = "JAVA_EXCEL_TEST.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXCEL_CELL1 + " TEXT NOT NULL, " + EXCEL_CELL2 + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
