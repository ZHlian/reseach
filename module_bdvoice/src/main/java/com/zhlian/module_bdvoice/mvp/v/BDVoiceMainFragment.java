package com.zhlian.module_bdvoice.mvp.v;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhlian.module_bdvoice.R;
import com.zhlian.module_bdvoice.mvp.p.BDVoiceContract;
import com.zhlian.module_bdvoice.mvp.p.BDVoiceContractPresenterImpl;

import org.w3c.dom.Text;

/**
 * Created by 64553 on 2018-11-16.
 * Power By ZHLian
 */

public class BDVoiceMainFragment extends Fragment implements BDVoiceContract.View,View.OnTouchListener{
//    private static BDVoiceMainFragment sInstance;
    private BDVoiceContractPresenterImpl mPresenter;
    private TextView mTextView;
    private Button mButton;

//    private BDVoiceMainFragment newInstance(){
//        return sInstance;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BDVoiceContractPresenterImpl(this,getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bdvoice,null);
        mTextView = view.findViewById(R.id.recog_state);
        mButton = view.findViewById(R.id.speak_btn);
        mButton.setOnTouchListener(this);
        return view;
    }

    @Override
    public void setPresenter(BDVoiceContract.Presenter presenter) {

    }

    @Override
    public void changeResultText(String resultText) {
        mTextView.setText(resultText);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPresenter.startRec();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mPresenter.stopRec();
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.release();
    }
}
