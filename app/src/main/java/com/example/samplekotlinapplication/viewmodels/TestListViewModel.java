package com.example.samplekotlinapplication.viewmodels;


import android.app.Application;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.samplekotlinapplication.persistence.TestEntity;
import com.example.samplekotlinapplication.repositories.TestRepository;
import com.example.samplekotlinapplication.utils.Resource;

import java.util.List;

public class TestListViewModel extends AndroidViewModel {

    private static final String TAG = "TestListViewModel";

    private MediatorLiveData<Resource<List<TestEntity>>> entities = new MediatorLiveData<>();
    private TestRepository testRepository;


    public TestListViewModel(@NonNull Application application) {
        super(application);
        testRepository = TestRepository.Companion.getInstance(application);

    }



    public LiveData<Resource<List<TestEntity>>> getEntities(){
        return entities;
    }


    public void executeSearch(){
        final LiveData<Resource<List<TestEntity>>> repositorySource = testRepository.searchRecordsApi();
        entities.addSource(repositorySource, new Observer<Resource<List<TestEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<TestEntity>> listResource) {
                if(listResource != null){
                    if(listResource.getStatus() == Resource.Status.SUCCESS){
                        entities.setValue(listResource);
                        entities.removeSource(repositorySource);
                    }
                    else if(listResource.getStatus() == Resource.Status.ERROR){
                        entities.setValue(listResource);
                        entities.removeSource(repositorySource);
                    }
                }
                else{
                    entities.setValue(listResource);
                    entities.removeSource(repositorySource);
                }
            }
        });
    }


}















