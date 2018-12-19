package com.zhlian.module_launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

/**
 * Created by Hua on 2018-11-21.
 * Power By ZHLian
 */

public class TestActivity extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        startActivity(new Intent(getApplicationContext(), Launcher.class));
    }
}
