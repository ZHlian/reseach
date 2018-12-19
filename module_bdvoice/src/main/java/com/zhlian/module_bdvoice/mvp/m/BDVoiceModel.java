package com.zhlian.module_bdvoice.mvp.m;

import android.content.Context;
import android.widget.TextView;

import com.baidu.speech.asr.SpeechConstant;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.zhlian.module_bdvoice.mvp.base.BaseModel;
import com.zhlian.module_bdvoice.util.recog.MyRecognizer;
import com.zhlian.module_bdvoice.util.recog.RecogResult;
import com.zhlian.module_bdvoice.util.recog.listener.IRecogListener;
import com.zhlian.module_bdvoice.util.recog.listener.RecogEventAdapter;
import com.zhlian.module_bdvoice.util.tts.TTSManager;
import com.zhlian.module_bdvoice.util.tts.control.InitConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 64553 on 2018-11-16.
 * Power By ZHLian
 *
 */

public class BDVoiceModel extends BaseModel implements BDVoiceModelFeature {
    private MyRecognizer mRecognizer;
    private RecogEventAdapter mRecorgEventAdapter;


    @Override
    public void initModel(Object ...objects) {
        mRecorgEventAdapter = new RecogEventAdapter((IRecogListener) objects[1]);
        mRecognizer = new MyRecognizer((Context) objects[0],mRecorgEventAdapter);

    }

    @Override
    public void startRecognize() {
        mRecognizer.start(getRecogParam());
    }

    private HashMap<String,Object>getRecogParam(){
        HashMap<String,Object>result = new HashMap<>();
        result.put(SpeechConstant.PID,"15361");
        return result;
    }

    @Override
    public void stopRecognize() {
        mRecognizer.stop();
    }

    @Override
    public void release() {
        mRecognizer.release();
    }
}
