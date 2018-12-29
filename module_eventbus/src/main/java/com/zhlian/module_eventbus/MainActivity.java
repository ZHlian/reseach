package com.zhlian.module_eventbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private static final String USB_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    private static final String USB_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    private TestBraodcastRecevier recevier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_test);
        recevier = new TestBraodcastRecevier();
        IntentFilter filter = new IntentFilter();
        filter.addAction(USB_POWER_DISCONNECTED);
        filter.addAction(USB_POWER_CONNECTED);
//        filter.addAction();
        registerReceiver(recevier,filter);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TestMessageEvent event) {
        textView.setText(event.msgContnt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        TestMessageEvent event1 = new TestMessageEvent();
        event1.msgContnt = "action is:" + event.getAction();
        EventBus.getDefault().post(event1);
        return super.onTouchEvent(event);
    }

    private void sendToast(final String info){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
            }
        });
    }


    class TestBraodcastRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case USB_POWER_CONNECTED:
                    sendToast(USB_POWER_CONNECTED);
                    Log.e("chiji",USB_POWER_CONNECTED);
                    break;
                case USB_POWER_DISCONNECTED:
                    sendToast(USB_POWER_DISCONNECTED);
                    Log.e("chiji",USB_POWER_DISCONNECTED);
                    break;
            }

        }
    }
}
