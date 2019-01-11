package network.retro;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created on 2019-01-04.
 */

public interface FaceTokenApi {
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Observable<FaceToken> getFaceToken(@Field("grant_type")String grant_type
            ,@Field("client_id")String client_id
            ,@Field("client_secret")String client_secret);
}
