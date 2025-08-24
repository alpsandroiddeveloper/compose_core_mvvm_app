package com.alps.compose_mvvm_strcucture.db

import android.media.ThumbnailUtils
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("product")
data class DbProduct(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val prodTitle : String,
    val description: String,
    val thumbnailUtils: String
)
