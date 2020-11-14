package com.example.samplekotlinapplication.repositories

import android.content.Context
import androidx.lifecycle.LiveData

import com.example.samplekotlinapplication.AppExecutors
import com.example.samplekotlinapplication.persistence.TestDAO
import com.example.samplekotlinapplication.persistence.TestDatabase
import com.example.samplekotlinapplication.persistence.TestEntity
import com.example.samplekotlinapplication.responses.ApiResponse
import com.example.samplekotlinapplication.responses.ApiGenerator
import com.example.samplekotlinapplication.responses.TestApiResponse
import com.example.samplekotlinapplication.utils.NetworkBoundResource
import com.example.samplekotlinapplication.utils.Resource

class TestRepository private constructor(context: Context) {
    private val testDAO: TestDAO

    init {
        testDAO = TestDatabase.getInstance(context).testDao
    }


    fun searchRecordsApi(): LiveData<Resource<List<TestEntity>>> {
        return object :
            NetworkBoundResource<List<TestEntity>, TestApiResponse>(AppExecutors.getInstance()) {

            override fun saveCallResult(item: TestApiResponse) {
                if (item.response != null) {
                    testDAO.deleteAll()
                    for (i in 0 until item.response.docs!!.size) {
                        val testEntity = TestEntity()
                        testEntity.id = item.response.docs!![i].id
                        testEntity.articleType = item.response.docs!![i].article_type
                        testEntity.publicationDate = item.response.docs!![i].publication_date
                        testEntity.abstract = item.response.docs!![i]._abstract[0]
                        testDAO.saveEntities(testEntity)
                    }
                    shouldFetchFromNetwork = false
                }
            }

            override fun shouldFetch(data: List<TestEntity>?): Boolean {
                return shouldFetchFromNetwork
            }

            override fun loadFromDb(): LiveData<List<TestEntity>> {
                return testDAO.allEntities
            }

            override fun createCall(): LiveData<ApiResponse<TestApiResponse>> {
                return ApiGenerator.testApi.getRecords("title:DNA")
            }
        }.asLiveData
    }



    companion object {

        private val TAG = "TestRepository"

        private var instance: TestRepository? = null
        private var shouldFetchFromNetwork = true

        fun getInstance(context: Context): TestRepository {
            if (instance == null) {
                instance = TestRepository(context)
            }
            return instance as TestRepository
        }
    }

}