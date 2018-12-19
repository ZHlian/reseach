package com.changan.module_network.net;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hua on 2018-11-28.
 * Power By ZHLian
 */


public interface TestService {
    @GET("/ZHlian/reseach")
    Call<ResponseBody> getTestData();
    @GET("users/{user}/repos")
    Call<List<String>> listRepos(@Path("user") String user);
}
