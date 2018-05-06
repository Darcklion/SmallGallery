package com.of.smallgallery.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey



@Entity
class Image(var path: String, var createDate: Long) {
    @PrimaryKey (autoGenerate = true)
    var id: Long = 0

}