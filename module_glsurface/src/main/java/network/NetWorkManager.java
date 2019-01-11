package network;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhlian.module_glsurface.config.BDFaceInfo;
import com.zhlian.module_glsurface.messager.FaceResultMessager;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import network.retro.FaceRecogApi;
import network.retro.FaceRecorgResult;
import network.retro.FaceToken;
import network.retro.FaceTokenApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2019-01-04.
 */

public class NetWorkManager {
    private FaceToken mToken = new FaceToken();
    private OkHttpClient mNetWorkClient;
    private Retrofit mRetrofit;
    private volatile static NetWorkManager sInstance;
    private NetWorkManager(){
//        EventBus.getDefault().register(this);
    }

    public static NetWorkManager getInstance(){
        if (null == sInstance){
            synchronized (NetWorkManager.class){
                if (null == sInstance){
                    sInstance = new NetWorkManager();
                }
            }
        }
        return sInstance;
    }

    public String getToken(){
        assert null!=mToken;
        return mToken.access_token;
    }

    public void init(){
        mNetWorkClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new TokenInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        mRetrofit = new Retrofit.Builder().client(mNetWorkClient).baseUrl("https://aip.baidubce.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void addRecorgRequest(String base64Str){
        final FaceRecogApi faceRecogApi = mRetrofit.create(FaceRecogApi.class);
        Observable<FaceRecorgResult>faceRecorgResultObservable=faceRecogApi.getFaceRecgResult(NetWorkManager.getInstance().getToken(),base64Str,"BASE64"
                ,1,"LIVE","age,beauty,expression,face_shape,gender,glasses,landmark,race,quality,eye_status,emotion,face_type");
        faceRecorgResultObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Consumer<FaceRecorgResult>() {
            @Override
            public void accept(FaceRecorgResult faceRecorgResult) throws Exception {
                Logger.e("faceRecorgResult:"+new Gson().toJson(faceRecorgResult));
                EventBus.getDefault().postSticky(new FaceResultMessager(faceRecorgResult));
            }
        });
    }

    public void addTokenRequest(){
        FaceTokenApi faceTokenApi = mRetrofit.create(FaceTokenApi.class);
        final Observable<FaceToken> tokenObservable = faceTokenApi.getFaceToken("client_credentials", BDFaceInfo.API_KEY,BDFaceInfo.APP_SECRET);
        tokenObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Consumer<FaceToken>() {
            @Override
            public void accept(FaceToken faceToken) throws Exception {
                Logger.e("get token is "+faceToken);
                mToken = faceToken;
            }
        });
    }
}
