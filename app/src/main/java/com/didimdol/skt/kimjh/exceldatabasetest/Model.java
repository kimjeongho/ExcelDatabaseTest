package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by tenqube on 2016-05-02.
 */
public class Model {
    private Context mContext;
    private DBManager dbManager;

    public Model(Context context){
        this.mContext = context;
        dbManager = new DBManager(context);
        dbManager.open();
    }

    public void insert(String cell1, String cell2){
        dbManager.insert(cell1, cell2);
    }

    public Cursor excelInsert(){
        Cursor cursor = dbManager.fetch();
        return cursor;
    }
}
