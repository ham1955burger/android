package com.example.user.constrainttest;

import java.util.ArrayList;

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
import retrofit2.http.Path;

/**
 * Created by user on 12/13/16.
 */

public interface InterfaceAPI {
    @GET("list/")
    Call<ArrayList<HouseholdAccountBook>> getHABList();

    @FormUrlEncoded
    @POST("list/")
    Call<Void> postHAB(
            @Field("state") String state,
            @Field("date") String date,
            @Field("price") int price,
            @Field("category") String category,
            @Field("memo") String memo
    );

//    @Headers( "Content-Type: application/json" )
    @PUT("detail/{pk}")
    Call<Void> putHAB(
            @Path("pk") int pk,
            @Body HABDataSend habDataSend
    );

    @DELETE("detail/{pk}")
    Call<Void> deleteHAB(
            @Path("pk") int pk
    );

}