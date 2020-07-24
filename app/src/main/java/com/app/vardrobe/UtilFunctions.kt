package com.app.vardrobe

import android.app.Application
import android.content.ContentUris
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import com.app.vardrobe.camera.ImageModel
import com.app.vardrobe.camera.ImageSource

fun getImages(application: Application,start:Int,size:Int): MutableList<ImageModel>{
    val cursor = application.contentResolver
        .query(ImageSource.uri.buildUpon().encodedQuery("limit=${start},${size}").build(),
            ImageSource.projection,
            null,
            null,
            ImageSource.sortOrder
        )
    val list = mutableListOf<ImageModel>()
    cursor?.use {
        while (cursor.moveToNext()) {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val typeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            while (cursor.moveToNext()) {
                val imageUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(idColumn)
                )
                val thumbNail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val x = application.resources.displayMetrics.widthPixels/3
                    val y = application.resources.displayMetrics.heightPixels/4
                    application.contentResolver.loadThumbnail(imageUri, Size(x,y),null)
                } else {
                    MediaStore.Images.Thumbnails.getThumbnail(
                        application.contentResolver,
                        cursor.getLong(idColumn),
                        MediaStore.Images.Thumbnails.MINI_KIND,
                        BitmapFactory.Options()
                    )
                }

                val model = ImageModel(
                    cursor.getLong(idColumn),
                    cursor.getString(nameColumn),
                    cursor.getString(typeColumn),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    thumbNail
                )
                list.add(model)
            }
        }
    }
    return list
}