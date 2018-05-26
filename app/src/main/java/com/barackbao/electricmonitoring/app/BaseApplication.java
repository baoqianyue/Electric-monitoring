package com.barackbao.electricmonitoring.app;

import android.app.Application;

import com.fengmap.android.FMMapSDK;

/**
 * Created by Baoqianyue on 2018/5/26.
 * <p>
 * 初始化各类SDK
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FMMapSDK.init(this);
    }
}
