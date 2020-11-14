package com.example.samplekotlinapplication.responses

import androidx.lifecycle.LiveData

import retrofit2.http.GET
import retrofit2.http.Query

interface TestApi {

    @GET("search")
    fun getRecords(
        @Query("q") q: String
    ): LiveData<ApiResponse<TestApiResponse>>


}




