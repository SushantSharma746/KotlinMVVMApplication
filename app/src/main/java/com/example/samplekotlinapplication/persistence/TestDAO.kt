package com.example.samplekotlinapplication.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface TestDAO {

    @get:Query("SELECT * FROM testTable")
    val allEntities: LiveData<List<TestEntity>>

    @Insert(onConflict = IGNORE)
    fun saveEntities(testEntity: TestEntity)

    @Query("DELETE FROM testTable")
    fun deleteAll()


}