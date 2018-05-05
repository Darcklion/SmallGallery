package com.of.smallgallery.db

import android.arch.persistence.room.*

@Dao
interface ImageDao {
    @Query("SELECT * FROM Image")
    fun getAll(): List<Image>

    @Insert
    fun insert(employee: Image)

    @Update
    fun update(employee: Image)

    @Delete
    fun delete(employee: Image)
}