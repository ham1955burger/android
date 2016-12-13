package com.example.user.constrainttest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 12/13/16.
 */

public interface InterfaceAPI {
    @GET("list/")
    Call<ArrayList<HouseholdAccountBook>> getHABList();
}