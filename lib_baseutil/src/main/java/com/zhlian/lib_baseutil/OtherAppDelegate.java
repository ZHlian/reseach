package com.zhlian.lib_baseutil;

import com.zhlian.lib_baseutil.util.ThirdAppUtil;
import com.zhlian.lib_common.base.IApplicationDelegate;
import com.zhlian.lib_common.utils.Utils;

/**
 * Created by Hua on 2018-11-20.
 * Power By ZHLian
 */

public class OtherAppDelegate implements IApplicationDelegate{
    @Override
    public void onCreate() {
        ThirdAppUtil.getInstance().init(Utils.getContext());
        GreenDaoUtil.getInstance().init(Utils.getContext());
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
