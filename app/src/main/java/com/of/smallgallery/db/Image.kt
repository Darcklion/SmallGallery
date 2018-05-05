package com.of.smallgallery.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey



@Entity
class Image {
    @PrimaryKey (autoGenerate = true)
    var id: Long = 0
    var path: String = ""
    var createDate: String = ""
}