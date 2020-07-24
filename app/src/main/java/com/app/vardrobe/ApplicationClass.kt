package com.app.vardrobe

import android.app.Application
import android.content.ContentResolver
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

class ApplicationClass : Application(), CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

}