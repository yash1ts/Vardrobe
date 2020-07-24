package com.app.vardrobe.camera

import android.view.View

interface CameraActions {
    fun onCapture(view: View)
    fun openGallery(view:View)
}