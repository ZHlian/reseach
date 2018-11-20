package com.zhlian.module_bdvoice.mvp.m;

/**
 * Created by 64553 on 2018-11-16.
 * Power By ZHLian
 */

public interface BDVoiceModelFeature {
    void startRecognize();
    void stopRecognize();

    void release();
}
