package com.zhlian.module_bdvoice.mvp.p;

import com.zhlian.module_bdvoice.mvp.base.BasePresenter;
import com.zhlian.module_bdvoice.mvp.base.BaseView;

/**
 * Created by 64553 on 2018-11-16.
 * Power By ZHLian
 */

public interface BDVoiceContract {

    interface View extends BaseView<Presenter>{
        void changeResultText(String resultText);

    }

    interface Presenter extends BasePresenter{
        void startRec();
        void stopRec();
        void release();

    }

}
