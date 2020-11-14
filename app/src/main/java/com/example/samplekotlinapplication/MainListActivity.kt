package com.example.samplekotlinapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.samplekotlinapplication.adapters.TestAdapter
import com.example.samplekotlinapplication.persistence.TestEntity
import com.example.samplekotlinapplication.utils.Resource
import com.example.samplekotlinapplication.viewmodels.TestListViewModel

import java.util.ArrayList


class MainListActivity : AppCompatActivity() {

    private var mRecipeListViewModel: TestListViewModel? = null
    private var entitiesRecycleView: RecyclerView? = null

    private var testAdapter: TestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        entitiesRecycleView = findViewById<View>(R.id.entitiesRecycleView) as RecyclerView?
        mRecipeListViewModel = ViewModelProviders.of(this).get(TestListViewModel::class.java)
        subscribeObservers()
        mRecipeListViewModel!!.executeSearch()
    }

    private fun subscribeObservers() {
        mRecipeListViewModel!!.entities.observe(this, Observer { listResource ->
            if (listResource != null) {
                Log.d(TAG, "onChanged: status: " + listResource.status)

                if (listResource.data != null) {
                    when (listResource.status) {
                        Resource.Status.LOADING -> {
                        }

                        Resource.Status.ERROR -> {
                            Log.e(TAG, "onChanged: Error.")
                        }

                        Resource.Status.SUCCESS -> {
                            Log.e(TAG, "onChanged: Success.")

                            val arrayList = listResource.data as ArrayList<TestEntity>?
                            testAdapter = TestAdapter(this@MainListActivity, arrayList!!)
                            entitiesRecycleView!!.layoutManager =
                                LinearLayoutManager(this@MainListActivity)
                            entitiesRecycleView!!.adapter = testAdapter

                        }
                    }
                }
            }
        })


    }

    companion object {

        private val TAG = "TestListActivity"
    }


}

















