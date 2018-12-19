package com.zhlian.module_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Hua on 2018-12-18.
 * Power By ZHLian
 */

public class BootBroadCastReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG.NAME, "onReceive" + intent.getAction());
        Intent startServiceIntent = new Intent(context, LinkDaemonService.class);
        context.startService(startServiceIntent);
    }
}
