package com.app.vardrobe.camera

import android.app.Application
import android.provider.MediaStore
import androidx.paging.PositionalDataSource
import com.app.vardrobe.getImages

class ImageSource(val application: Application) : PositionalDataSource<ImageModel>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ImageModel>) {
        callback.onResult(getImages(application,
            params.requestedStartPosition,
            params.requestedLoadSize
        ),params.requestedStartPosition )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ImageModel>) {
        callback.onResult(getImages(application,
            params.startPosition,
            params.loadSize
        )
        )
    }

    companion object {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
//        val selection = "${MediaStore.Images.Media.} >= ?"
    }


}