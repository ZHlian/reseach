package com.zhlian.module_bdvoice.mvp.p;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.client.TtsMode;
import com.orhanobut.logger.Logger;
import com.zhlian.module_bdvoice.mvp.m.BDVoiceModel;
import com.zhlian.module_bdvoice.util.recog.RecogResult;
import com.zhlian.module_bdvoice.util.recog.listener.IRecogListener;
import com.zhlian.module_bdvoice.util.tts.TTSManager;
import com.zhlian.module_bdvoice.util.tts.control.InitConfig;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.zhlian.lib_common.utils.Utils.checkNotNull;

/**
 * Created by 64553 on 2018-11-16.
 * Power By ZHLian
 */

public class BDVoiceContractPresenterImpl implements BDVoiceContract.Presenter{
    private static final String TAG = BDVoiceContractPresenterImpl.class.getName();
    private BDVoiceModel mBDVoiceModel = new BDVoiceModel();
    private BDVoiceContract.View mView;
    private AsrListener mListener;
    private TTSManager mTTSManager;

    protected String appId = "11005757";

    protected String appKey = "Ovcz19MGzIKoDDb3IsFFncG1";

    protected String secretKey = "e72ebb6d43387fc7f85205ca7e6706e2";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.ONLINE;
    // ================选择TtsMode.ONLINE  不需要设置以下参数; 选择TtsMode.MIX 需要设置下面2个离线资源文件的路径
    private static final String TEMP_DIR = "/sdcard/baiduTTS"; // 重要！请手动将assets目录下的3个dat 文件复制到该目录

    // 请确保该PATH下有这个文件
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";


    public BDVoiceContractPresenterImpl(@NotNull  BDVoiceContract.View view
            , @NotNull Context context){
        mListener = new AsrListener();
        this.mView = view;
        Map<String, String> params = new HashMap<>();
        mTTSManager = new TTSManager();
        mTTSManager.initTTSEngine(context,new InitConfig(appId, appKey, secretKey, ttsMode, params, null));
        mBDVoiceModel.initModel(context,mListener);
    }

    @Override
    public void start() {
        Logger.d(TAG,"presenter start");
    }


    @Override
    public void startRec() {
        Logger.d(TAG,"stasrtRec");
        mBDVoiceModel.startRecognize();
    }

    @Override
    public void stopRec() {
        Logger.d(TAG,"stopRec");
        mBDVoiceModel.stopRecognize();
    }

    @Override
    public void release() {
        mBDVoiceModel.release();
    }


    class AsrListener implements IRecogListener{

        @Override
        public void onAsrReady() {
            Logger.d(TAG,"onAsrReady");

        }

        @Override
        public void onAsrBegin() {
            Logger.d(TAG,"onAsrBegin");

        }

        @Override
        public void onAsrEnd() {
            Logger.d(TAG,"onAsrEnd");

        }

        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {
            Logger.d(TAG,"onAsrPartialResult");

        }

        @Override
        public void onAsrOnlineNluResult(String nluResult) {
            Logger.d(TAG,"onAsrOnlineNluResult");

        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            Logger.d(TAG,"onAsrFinalResult"+recogResult.getOrigalJson());
            mView.changeResultText(checkNotNull(results[0]));
            mTTSManager.play(results[0]);
        }

        @Override
        public void onAsrFinish(RecogResult recogResult) {
            Logger.d(TAG,"onAsrFinish");

        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String descMessage, RecogResult recogResult) {
            Logger.d(TAG,"onAsrFinishError");

        }

        @Override
        public void onAsrLongFinish() {
            Logger.d(TAG,"onAsrLongFinish");

        }

        @Override
        public void onAsrVolume(int volumePercent, int volume) {
            Logger.d(TAG,"onAsrVolume");

        }

        @Override
        public void onAsrAudio(byte[] data, int offset, int length) {
            Logger.d(TAG,"onAsrAudio");

        }

        @Override
        public void onAsrExit() {
            Logger.d(TAG,"onAsrExit");

        }

        @Override
        public void onOfflineLoaded() {
            Logger.d(TAG,"onOfflineLoaded");

        }

        @Override
        public void onOfflineUnLoaded() {
            Logger.d(TAG,"onOfflineUnLoaded");

        }
    }
}
