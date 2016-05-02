package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by tenqube on 2016-05-02.
 */
public interface Presenter {
    void onExcelClick();

    void share(String fileName, Context context);

    void dataSet(String cell1, String cell2);



}
