package com.example.samplekotlinapplication.persistence

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TestEntity::class], version = 1)
abstract class TestDatabase : RoomDatabase() {

    abstract val testDao: TestDAO

    companion object {

        val DATABASE_NAME = "test_db"

        private var instance: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return instance as TestDatabase
        }
    }

}






