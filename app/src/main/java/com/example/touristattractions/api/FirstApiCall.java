package com.example.touristattractions.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FirstApiCall
{
    @GET("0.1/en/places/bbox")
    Call<FirstResponse> searchAttractions(@Query("lon_min") String lonMin, @Query("lat_min") String latMin, @Query("lon_max") String lonMax, @Query("lat_max") String latMax, @Query("apikey") String apiKey);
}
