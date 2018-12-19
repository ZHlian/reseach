package com.changan.module_network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.changan.module_network.fragment.BackgroundLongTimeTaskFragment;
import com.changan.module_network.fragment.BufferDemoFragment;
import com.changan.module_network.fragment.SearchFragment;
import com.changan.module_network.fragment.SimpleRxDemo;
import com.changan.module_network.net.Config;
import com.changan.module_network.net.TestService;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new SearchFragment()).commit();

        Observable okhttpcall = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new NetWorkInterceptor()).build();
                Request request = new Request.Builder().url("https://yq.aliyun.com/articles/591627").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG,"onFail-----");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String tmp = response.body().string();
                        emitter.onNext(tmp);
//                        Log.e(TAG,"onResponse"+response.body().string());
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observer<String>htmlObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        okhttpcall.subscribe(htmlObserver);
        testRetrofit();
//        okhttpcall.

    }

    private void testRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        TestService testService = retrofit.create(TestService.class);
        retrofit2.Call<ResponseBody> stringCall= testService.getTestData();
        stringCall.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.e(TAG,"response:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
