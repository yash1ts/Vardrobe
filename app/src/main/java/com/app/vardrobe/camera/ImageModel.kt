package com.app.vardrobe.camera

import android.graphics.Bitmap
import android.net.Uri

data class ImageModel (
    val id: Long,
    val name: String,
val type:String,
val uri: Uri,
val tumbnail: Bitmap)