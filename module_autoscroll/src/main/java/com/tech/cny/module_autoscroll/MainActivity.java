package com.tech.cny.module_autoscroll;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Fragment mainFragment;
    boolean initState;
    private static final String TAG = "Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        if (null!=savedInstanceState)
            initState = savedInstanceState.getBoolean(TAG);
        if (!initState){
            mainFragment = new MainFragment();
            Log.e(TAG,"fragment init success");
            initState  =true;
            getSupportFragmentManager().beginTransaction().add(R.id.frag_container,mainFragment).commit();

        }else {
            Log.e(TAG,"fragment inited---"+mainFragment);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG,initState);
        super.onSaveInstanceState(outState);
        Log.e(TAG,"onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null != savedInstanceState){
            initState = savedInstanceState.getBoolean(TAG);
            if (initState){
                getSupportFragmentManager().beginTransaction().add(R.id.frag_container,mainFragment).commit();

            }
        }
        Log.e(TAG,"onRestoreInstanceState"+initState);

    }
}
