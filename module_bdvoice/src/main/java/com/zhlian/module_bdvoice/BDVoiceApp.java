package com.zhlian.module_bdvoice;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zhlian.lib_common.utils.Utils;


/**
 * Created by Hua on 2018-11-19.
 * Power By ZHLian
 */

public class BDVoiceApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
