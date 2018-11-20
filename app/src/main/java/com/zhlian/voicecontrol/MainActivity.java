package com.zhlian.voicecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_bdvoice).setOnClickListener(this);
        findViewById(R.id.btn_xfvoice).setOnClickListener(this);
        findViewById(R.id.btn_otherapp).setOnClickListener(this);
//        ARouter.

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bdvoice:
                ARouter.getInstance().build("/module_bdvoice/MainActivity").navigation();
                break;
            case R.id.btn_xfvoice:
                ARouter.getInstance().build("/module_xfvoice/MainActivity").navigation();
                break;
            case R.id.btn_otherapp:
                ARouter.getInstance().build("/lib_baseutil/MainActivity").navigation();
                break;
        }

    }
}
