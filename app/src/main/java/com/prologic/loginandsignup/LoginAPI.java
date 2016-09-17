package com.prologic.loginandsignup;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Ajeet on 9/16/2016.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("/Login/login.php")
    public void loginUser(

            @Field("username") String username,
            @Field("password") String password,
            Callback<Response> callback);
}
