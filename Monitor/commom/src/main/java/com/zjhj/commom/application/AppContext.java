package com.zjhj.commom.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;



/**
 * Created by brain on 2016/6/14.
 */
public class AppContext extends Application {

    private static AppContext appContext;
    public static AppContext getInstance(){
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = (AppContext) getApplicationContext();

        System.loadLibrary("SystemTransform");
        MCRSDK.init();
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);

        Fresco.initialize(this);

    }
}
