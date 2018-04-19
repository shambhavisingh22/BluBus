package com.example.shamb.busreservation;

/**
 * Created by shamb on 3/19/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Manjeet Singh on 8/2/2017.
 */

public interface API {// har ke liye alag
    @GET("/{endpoint}")
    public Call<Response> getApires(@Path("endpoint") String endPoint, @Query("source") String source, @Query("destination") String destination, @Query("date") String date);
}