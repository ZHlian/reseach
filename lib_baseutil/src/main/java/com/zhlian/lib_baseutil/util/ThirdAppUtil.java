package com.zhlian.lib_baseutil.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.zhlian.lib_baseutil.AppBean;

import java.util.List;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class ThirdAppUtil {
    private static volatile ThirdAppUtil sInstance;
    private ThirdAppJumper jumper;
    private ThirdAppLoader loader;

    private ThirdAppUtil() {
    }

    public synchronized static ThirdAppUtil getInstance() {
        if (null == sInstance) {
            synchronized (ThirdAppUtil.class) {
                if (null == sInstance) {
                    sInstance = new ThirdAppUtil();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        jumper = new ThirdAppJumper(context);
        loader = new ThirdAppLoader(context);
    }

    public List<AppBean>loadAllThirdApp(){
        return loader.getAppList();
    }

    public void jumpWithPkgName(String pkgName){
        jumper.jump(pkgName);
    }

}
