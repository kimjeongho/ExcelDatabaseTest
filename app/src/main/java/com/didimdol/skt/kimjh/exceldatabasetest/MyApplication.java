package com.didimdol.skt.kimjh.exceldatabasetest;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by tenqube on 2016-05-02.
 */
public class MyApplication extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context =this;
    }

    public static Context getContext(){
        return context;
    }
}
