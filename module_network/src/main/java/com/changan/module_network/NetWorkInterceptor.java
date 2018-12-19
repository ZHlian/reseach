package com.changan.module_network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hua on 2018-11-28.
 * Power By ZHLian
 */

public class NetWorkInterceptor implements Interceptor{
    private static final String TAG = NetWorkInterceptor.class.getName();
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        long time = System.currentTimeMillis();
        Log.d(TAG,"interceptor time :"+time+request.url()+chain.connection());
        final Response response = chain.proceed(request);
        Log.d(TAG,"cast time "+(System.currentTimeMillis()-time)+response.headers());
        return response;
    }


}
