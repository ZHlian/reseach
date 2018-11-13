package com.zhlian.lib_baseutil.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.zhlian.lib_baseutil.MainActivity;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class ThirdAppJumper {
    private PackageManager packageManager;
    private Context mContext;
    public ThirdAppJumper(Context context){
        packageManager = context.getPackageManager();
        mContext = context;
    }

    public void jump(String pkgName){
        Intent intent = packageManager.getLaunchIntentForPackage(pkgName);
        if (null != intent){
            mContext.startActivity(intent);
        }
    }
}
