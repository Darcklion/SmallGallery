package com.of.smallgallery.db

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface ImageDao {
    @Query("SELECT * FROM Image")
    fun getAll(): Flowable<List<Image>>

    @Query("DELETE FROM Image")
    fun deleteAll()

    @Insert
    fun insert(image: Image)

    @Insert
    fun insert(image: ArrayList<Image>)

    @Update
    fun update(image: Image)

    @Delete
    fun delete(image: Image)

    @Delete
    fun delete(image: ArrayList<Image>)
}