package com.app.vardrobe.camera

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.toLiveData
import com.app.vardrobe.LANDSCAPE_SPAN
import com.app.vardrobe.PROTRAIT_SPAN
import com.github.gabrielbb.cutout.CutOut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val executor by lazy { Executors.newSingleThreadExecutor() }
    private var outputImage = MutableLiveData<Uri>()
    private lateinit var image: File

    val imageList by lazy {
        val pagingConfig = Config(
            pageSize = 50,
            prefetchDistance = 100,
            enablePlaceholders = false
        )
        ImageDataSourceFactory(application).toLiveData(pagingConfig, 0)
    }

    fun getOutPutImage(): LiveData<Uri> = outputImage

    val imageCapture by lazy { ImageCapture.Builder().build() }

    private fun setupOutput(): ImageCapture.OutputFileOptions {
        val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(Date())
        val pictureFile = "Image_$timeStamp"
        val storageDir = getApplication<Application>().externalCacheDir

        image = File.createTempFile(pictureFile, ".jpg", storageDir)
        return ImageCapture.OutputFileOptions.Builder(image).build()
    }

    fun takePicture() {
        runBlocking {
            val outputFileOptions = viewModelScope
                .async(Dispatchers.IO) { setupOutput() }.await()
            imageCapture.takePicture(outputFileOptions, executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        Log.d("ERROR_O", error.message ?: "")
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        outputImage.postValue(image.toUri())
                    }
                })
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE.toInt()) {
            image.delete()
            when (resultCode) {
                Activity.RESULT_OK ->
                    CutOut.getUri(data)
                CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE.toInt() ->
                    CutOut.getError(data)
                else -> print("User cancelled the CutOut screen")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }

}