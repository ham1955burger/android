package com.example.user.constrainttest;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by user on 12/13/16.
 */

public interface InterfaceAPI {
    @GET("/list/")
    Call<ArrayList<HouseholdAccountBook>> getHABList();

    @FormUrlEncoded
    @POST("/list/")
    Call<Void> postHAB(
            @Field("state") String state,
            @Field("date") String date,
            @Field("price") int price,
            @Field("category") String category,
            @Field("memo") String memo
    );

//    @Headers( "Content-Type: application/json" )
    @PUT("/detail/{pk}")
    Call<Void> putHAB(
            @Path("pk") int pk,
            @Body HABDataSend habDataSend
    );

    @DELETE("/detail/{pk}")
    Call<Void> deleteHAB(
            @Path("pk") int pk
    );

    @GET("/photo")
    Call<ArrayList<PhotoBook>> getPhotoList();

    @Multipart
    @POST("/photo")
    Call<Void> postPhoto(
            @Part MultipartBody.Part image,
            @Part("description") RequestBody description
    );

    //    @Headers( "Content-Type: application/json" )
    @PUT("/photo/detail/{pk}")
    Call<Void> putPhoto(
            @Path("pk") int pk,
            @Part MultipartBody.Part image,
            @Body PhotoBook photoBook
    );

    @DELETE("/photo/detail/{pk}")
    Call<Void> deletePhoto(
            @Path("pk") int pk
    );

}