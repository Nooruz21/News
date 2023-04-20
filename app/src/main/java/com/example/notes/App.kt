package com.example.notes

import android.app.Application
import androidx.room.Room
import com.example.notes.room.AppDatabase

class App : Application() {
    companion object {

        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

}