package com.of.smallgallery.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.of.smallgallery.App

class ImageViewModel : ViewModel() {
    private var images: MutableLiveData<List<Image>>? = null
    fun getImages(): LiveData<List<Image>> {
        if (images == null) {
            images = MutableLiveData()
            loadImages()
        }
        return images as MutableLiveData<List<Image>>
    }

    private fun loadImages() {
        val database = App.getAppInstance().getDatabase()
        database?.imageDao()?.getAll()
    }

}