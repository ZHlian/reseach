package com.changan.module_network.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.changan.module_network.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Hua on 2018-12-11.
 * Power By ZHLian
 */

public class BufferDemoFragment extends Fragment{

    private static final String TAG = BufferDemoFragment.class.getName();
    @BindView(R.id.buffer_test)
    Button mBtn;
    @BindView(R.id.tv_buffertest)
    TextView mTextView;
    private Unbinder unbinder;
    int clickCount;

    PublishSubject clickSubject;
    CompositeDisposable compositeDisposable;
    DisposableObserver<List<Integer>> observer = new DisposableObserver<List<Integer>>() {
        @Override
        public void onNext(List<Integer> integer) {
            Log.e(TAG,"onNext---: clicked---"+integer.size());
            mTextView.setText("click :"+integer.size());
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG,"onError-----");

        }

        @Override
        public void onComplete() {
            Log.e(TAG,"onComplete-----");
        }
    };
    @OnClick(R.id.buffer_test)
    public void testBuffer(){
        clickSubject.onNext(clickCount++);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bufferdemo,null,false);
        unbinder = ButterKnife.bind(this,view);
        compositeDisposable = new CompositeDisposable();
        clickSubject = PublishSubject.create();
        clickSubject.buffer(3000,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(observer);
        compositeDisposable.add(observer);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        compositeDisposable.clear();
        super.onDestroyView();
    }
}
