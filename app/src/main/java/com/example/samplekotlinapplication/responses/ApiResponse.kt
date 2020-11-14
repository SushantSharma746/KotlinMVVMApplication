package com.example.samplekotlinapplication.responses


import java.io.IOException

import retrofit2.Response


open class ApiResponse<T> {

    fun create(error: Throwable): ApiResponse<T> {
        return ApiErrorResponse((if (error.message == "") error.message else "Unknown error\nCheck network connection")!!)
    }

    fun create(response: Response<T>): ApiResponse<T> {

        if (response.isSuccessful) {
            val body = response.body()
            return if (body == null || response.code() == 204) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        } else {
            var errorMsg = ""
            try {
                errorMsg = response.errorBody()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()
                errorMsg = response.message()
            }

            return ApiErrorResponse(errorMsg)
        }
    }


     class ApiSuccessResponse<T> internal constructor(val body: T) : ApiResponse<T>()


     class ApiErrorResponse<T> internal constructor(val errorMessage: String) :
        ApiResponse<T>()



     class ApiEmptyResponse<T> : ApiResponse<T>()

}





















