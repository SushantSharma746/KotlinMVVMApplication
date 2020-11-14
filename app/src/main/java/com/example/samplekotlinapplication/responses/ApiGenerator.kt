package com.example.samplekotlinapplication.responses



import com.example.samplekotlinapplication.utils.Constants
import com.example.samplekotlinapplication.utils.LiveDataCallAdapterFactory

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiGenerator {

    private val client = OkHttpClient.Builder()

        // establish connection to server
        .connectTimeout(Constants.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)

        // time between each byte read from the server
        .readTimeout(Constants.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)

        // time between each byte sent to server
        .writeTimeout(Constants.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)

        .retryOnConnectionFailure(false)

        .build()


    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    val testApi = retrofit.create(TestApi::class.java)
}
