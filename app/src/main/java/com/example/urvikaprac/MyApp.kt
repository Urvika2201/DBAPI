package com.example.urvikaprac

import android.app.Application
import androidx.room.Room
import com.example.urvikaprac.database.AppDatabase

class MyApp : Application() {

    companion object {
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "my_database").build()
    }
}