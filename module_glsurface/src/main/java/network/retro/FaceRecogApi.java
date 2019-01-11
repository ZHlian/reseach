package network.retro;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created on 2019-01-04.
 */

public interface FaceRecogApi {
    @POST("/rest/2.0/face/v3/detect")
    @Headers("Content-Type:application/json")
    @FormUrlEncoded
    Observable<FaceRecorgResult>getFaceRecgResult(@Query("access_token") String token, @Field("image")String image
            , @Field("image_type")String image_type
            , @Field("max_face_num")int max_face_num
            , @Field("face_type")String face_type
            , @Field("face_field")String faceFiled);
}
