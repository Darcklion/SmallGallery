package com.of.smallgallery

import android.app.Application
import android.arch.persistence.room.Room
import com.of.smallgallery.db.AppDB


class App: Application() {
    private var database: AppDB? = null

    companion object {
        private lateinit var instance: App
        fun getAppInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDB::class.java, "database").build()
    }

    fun getDatabase() = database
}