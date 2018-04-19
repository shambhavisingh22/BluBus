package com.example.shamb.busreservation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by shamb on 4/16/2018.
 */

public interface AddAPI {
    @POST("{endPoint}")
    Call<PostAPIResponse> getData(@Path("endPoint") String endPoint, @Body PostModel info);
}
