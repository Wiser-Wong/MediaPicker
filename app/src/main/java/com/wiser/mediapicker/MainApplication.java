package com.wiser.mediapicker;

import android.app.Application;

import com.wiser.picker.lib.MediaHelper;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MediaHelper.newBind().setBind(new CustomUiBind()).inject();
    }
}
