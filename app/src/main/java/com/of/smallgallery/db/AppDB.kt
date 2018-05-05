package com.of.smallgallery.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(Image::class)], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}