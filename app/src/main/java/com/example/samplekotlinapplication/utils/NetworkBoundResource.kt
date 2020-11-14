package com.example.samplekotlinapplication.utils


import android.util.Log

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.example.samplekotlinapplication.AppExecutors
import com.example.samplekotlinapplication.responses.ApiResponse

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors) {
    private val results = MediatorLiveData<Resource<CacheObject>>()


    val asLiveData: LiveData<Resource<CacheObject>>
        get() = results

    init {
        init()
    }

    private fun init() {

        results.setValue(Resource.loading<Any>(null) as Resource<CacheObject>)

        val dbSource = loadFromDb()

        results.addSource(dbSource) { cacheObject ->
            results.removeSource(dbSource)

            if (shouldFetch(cacheObject)) {
                fetchFromNetwork(dbSource)
            } else {
                results.addSource(dbSource) { cacheObject ->
                    setValue(
                        Resource.success<CacheObject>(
                            cacheObject
                        )
                    )
                }
            }
        }
    }


    private fun fetchFromNetwork(dbSource: LiveData<CacheObject>) {

        Log.d(TAG, "fetchFromNetwork: called.")

        results.addSource(dbSource) { cacheObject -> setValue(Resource.loading<CacheObject>(cacheObject)) }

        val apiResponse = createCall()

        results.addSource(apiResponse) { requestObjectApiResponse ->
            results.removeSource(dbSource)
            results.removeSource(apiResponse)

            if (requestObjectApiResponse is ApiResponse.ApiSuccessResponse<*>) {

                appExecutors.diskIO().execute {

                    saveCallResult(processResponse((requestObjectApiResponse as ApiResponse.ApiSuccessResponse<*>)!!) as RequestObject)

                    appExecutors.mainThread().execute {
                        results.addSource(loadFromDb()) { cacheObject ->
                            setValue(Resource.success<CacheObject>(cacheObject))
                        }
                    }
                }
            } else if (requestObjectApiResponse is ApiResponse.ApiEmptyResponse<*>) {
                appExecutors.mainThread().execute {
                    results.addSource(loadFromDb()) { cacheObject ->
                        setValue(
                            Resource.success<CacheObject>(cacheObject)
                        )
                    }
                }
            } else if (requestObjectApiResponse is ApiResponse.ApiErrorResponse<*>) {
                results.addSource(dbSource) { cacheObject ->
                    setValue(
                        Resource.error<CacheObject>(
                            (requestObjectApiResponse as ApiResponse.ApiErrorResponse<*>).errorMessage,
                            cacheObject
                        )
                    )
                }
            }
        }
    }

    private fun processResponse(response: ApiResponse.ApiSuccessResponse<*>): CacheObject {
        return response.body as CacheObject
    }

    private fun setValue(newValue: Resource<CacheObject>) {
        if (results.value != newValue) {
            results.setValue(newValue)
        }
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestObject)


    @MainThread
    protected abstract fun shouldFetch(data: CacheObject?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>>

    companion object {

        private val TAG = "NetworkBoundResource"
    }
}




