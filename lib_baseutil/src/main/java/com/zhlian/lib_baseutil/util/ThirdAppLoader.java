package com.zhlian.lib_baseutil.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zhlian.lib_baseutil.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class ThirdAppLoader {
    private PackageManager mPackageManager;
    public ThirdAppLoader(Context context){
        mPackageManager = context.getPackageManager();
    }


    public List<AppBean> getAppList() {
        List<AppBean>appBeans = new ArrayList<>();
        List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                AppBean appBean = new AppBean();
                appBean.appName = packageInfo.applicationInfo.loadLabel(mPackageManager).toString();
                appBean.pkgName = packageInfo.packageName;
                appBean.icon = FormatTools.getInstance().Drawable2Bytes(packageInfo.applicationInfo.loadIcon(mPackageManager));
                appBeans.add(appBean);
            } else {
                // 系统应用
            }
        }
        return appBeans;
    }
}
