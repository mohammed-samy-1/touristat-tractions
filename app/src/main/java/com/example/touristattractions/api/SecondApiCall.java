package com.example.touristattractions.api;

import com.example.touristattractions.classes.AttractionClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SecondApiCall
{
    @GET ("0.1/en/places/xid/{xid}")
    Call<AttractionClass> searchAttraction(@Path ("xid") String xid, @Query("apikey") String apiKey);
}
