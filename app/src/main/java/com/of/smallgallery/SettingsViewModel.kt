package com.of.smallgallery

import android.arch.lifecycle.ViewModel
import android.os.Environment
import android.util.Log
import com.of.smallgallery.db.Image
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

private const val FOLDER = "/testImages/"
private const val DEFAULT_IMAGE_NUMBER = 10
private val DEFAULT_IMAGE_EXTENSIONS = arrayListOf("jpg", "png")

class SettingsViewModel: ViewModel() {

    private val database = App.getAppInstance().getDatabase()
    private var maxImportImageNumber = DEFAULT_IMAGE_NUMBER

    fun setMaxImportImageNumber(value: Int) {
        maxImportImageNumber = if (value == 0) Int.MAX_VALUE else value
    }

    fun importImages() {
        val imageFolder = File("${Environment.getExternalStorageDirectory()}$FOLDER")
        var importedImagePaths: ArrayList<Image> = ArrayList(0)
        if (imageFolder.exists()) {
            var count = 0
            for (file in imageFolder.listFiles()) {
                if (DEFAULT_IMAGE_EXTENSIONS.contains(file.extension) && count < maxImportImageNumber) {
                    importedImagePaths.add(Image(file.absolutePath, file.lastModified()))
                    count++
                }
            }

            getCompletableToClearDB()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                Completable.fromAction { database?.imageDao()?.insert(importedImagePaths) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { Log.d("image inserting", "images inserted") }
            }
        }
    }

    private fun getCompletableToClearDB():Completable {
        return Completable.fromAction { database?.imageDao()?.deleteAll() }
    }

    fun clearDB () {
        getCompletableToClearDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.d("image deleting", "images deleted") }
    }


}