package com.of.smallgallery

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.of.smallgallery.db.Image
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageViewModel : ViewModel() {
    private val database = App.getAppInstance().getDatabase()
    private var images: MutableLiveData<List<Image>>? = null
    private val selectedImages = ArrayList<Image>(0)

    fun getImages(): LiveData<List<Image>> {
        if (images == null) {
            images = MutableLiveData()
            loadImages()
        }
        return images as MutableLiveData<List<Image>>
    }

    private fun loadImages() {
        database?.imageDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                {
                    images?.postValue(it)
                },
                {
                    Log.d("DB Error",  "Error on loading images")
                }, {}
        )
    }

    fun addSelectedImage(image: Image) {
        selectedImages.add(image)
    }

    fun removeSelectedImage(image: Image) {
        selectedImages.remove(image)
    }

    fun deleteSelectedImages() {
        Completable.fromAction { database?.imageDao()?.delete(selectedImages) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.d("image deleting", "images deleted") }
    }
}