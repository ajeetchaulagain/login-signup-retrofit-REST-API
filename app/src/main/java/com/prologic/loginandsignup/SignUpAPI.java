package com.prologic.loginandsignup;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Ajeet on 9/16/2016.
 */
public interface SignUpAPI {
    @FormUrlEncoded
    @POST("/Login/insert.php")
    public void insertUser(

            @Field("username") String username,
            @Field("email") String email,
            @Field("level") String level,
            @Field("faculty") String faculty,
            @Field("year") String year,
            @Field("password") String password,

            Callback<Response> callback);
}
