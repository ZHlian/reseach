package network;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created on 2019-01-04.
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String token = NetWorkManager.getInstance().getToken();
        Logger.e("token is:"+token);
        if (TextUtils.isEmpty(token)){
            return chain.proceed(request);
        }else {
//            FormBody.Builder builder = new FormBody.Builder();
//            builder.addEncoded("access_token",token);
//            Request tokenRequest = chain.request().newBuilder().put(builder.build()).build();
            return chain.proceed(request);
        }
    }
}
