package la.opi.verificacionciudadana.api;

import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Jhordan on 24/02/15.
 */
public interface ApiPitagorasService {

    @GET(EndPoint.HOME)
    Observable<Response> getHome();

    @GET(EndPoint.MEXICO_STATES)
    Observable<Response> getMexicoStates();

    @FormUrlEncoded
    @POST(EndPoint.USER_SIGN_UP)
    Observable<Response> userSignUp(

            @Field(EndPoint.FIELD_AUTHENTICITY_TOKEN) String token,
            @Field(EndPoint.FIELD_UTF_8) String utf,
            @Field(EndPoint.FIELD_USER_NAME) String name,
            @Field(EndPoint.FIELD_USER_MAIL) String mail,
            @Field(EndPoint.FIELD_USER_ROLE) String role,
            @Field(EndPoint.FIELD_USER_STATE) String State,
            @Field(EndPoint.FIELD_USER_TOWN) String Twon,
            @Field(EndPoint.FIELD_USER_PASSWORD) String password,
            @Field(EndPoint.FIELD_USER_CONFIRMATION) String confirmation,
            @Field(EndPoint.FIELD_COMMIT) String commit
    );


    @FormUrlEncoded
    @POST(EndPoint.USER_SIGN_IN)
    Observable<Response> userSingIn(

            @Field(EndPoint.FIELD_UTF_8) String utf,
            @Field(EndPoint.FIELD_AUTHENTICITY_TOKEN) String auth_token,
            @Field(EndPoint.FIELD_USER_MAIL) String email,
            @Field(EndPoint.FIELD_USER_PASSWORD) String password,
            @Field(EndPoint.FIELD_USER_REMEMBER_ME) String remember,
            @Field(EndPoint.FIELD_COMMIT) String commit
    );


    @DELETE(EndPoint.USER_LOG_OUT)
    Observable<Response> userLogOut();

    @GET(EndPoint.OCURRENCES)
    Observable<Response> getOcurrences();

    @GET(EndPoint.USER_PROFILE)
    Observable<Response> getProfile();

    @FormUrlEncoded
    @PUT("/users/{id}/update.json")
    Observable<Response> userUpdate(

            @Path("id") String id,
            @Field(EndPoint.FIELD_USER_STATE) String State,
            @Field(EndPoint.FIELD_USER_TOWN) String twon
    );

    @FormUrlEncoded
    @POST("/answers")
    Observable<Response> sentAnswers(
            @Field("json") String json,
            @Field("authenticity_token") String auth_token,
            @Field("form_authenticity_token") String form_auth_token,
            @Field("utf8") String utf);



}
