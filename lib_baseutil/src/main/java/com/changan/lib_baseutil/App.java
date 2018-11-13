package com.changan.lib_baseutil;

import android.app.Application;

import com.changan.lib_baseutil.util.ThirdAppUtil;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ThirdAppUtil.getInstance().init(this);
        GreenDaoUtil.getInstance().init(this);
    }
}
