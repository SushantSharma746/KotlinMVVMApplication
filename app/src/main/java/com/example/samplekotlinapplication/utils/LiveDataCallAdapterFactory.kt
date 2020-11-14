package com.example.samplekotlinapplication.utils


import androidx.lifecycle.LiveData

import com.example.samplekotlinapplication.responses.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import retrofit2.CallAdapter
import retrofit2.Retrofit

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {


        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }


        val observableType =
            CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)

        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        require(!(rawObservableType !== ApiResponse::class.java)) { "Type must be a defined resource" }


        require(observableType is ParameterizedType) { "resource must be parameterized" }

        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Type>(bodyType)
    }
}




















