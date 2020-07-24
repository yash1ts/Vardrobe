package com.app.vardrobe.camera

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class ImageDataSourceFactory(val application: Application) : DataSource.Factory<Int, ImageModel>() {
    val sourceLiveData = MutableLiveData<ImageSource>()

    override fun create(): DataSource<Int, ImageModel> {
        val latestSource = ImageSource(application)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}