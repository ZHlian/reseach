package com.zhlian.module_service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Hua on 2018-12-18.
 * Power By ZHLian
 */

public class LinkDaemonService extends Service{


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG.NAME,"oncreate");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG.NAME,"onDestory");
        super.onDestroy();
        startServiceAuto();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(TAG.NAME,"onTastRemoved");
        startServiceAuto();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startServiceAuto(){
        Log.e(TAG.NAME,"startServiceAuto");

        Intent autoBroadcast = new Intent("com.tech.auto");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,autoBroadcast,0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (3 * 1000), pendingIntent);

    }
    private void sendCallUpServiceBroadCast(){
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("rcmirrorservice.boot");
        PendingIntent intent1 = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+3000,intent1);
    }

}
