package com.app.vardrobe.camera

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.vardrobe.LANDSCAPE_SPAN
import com.app.vardrobe.PROTRAIT_SPAN
import com.app.vardrobe.R
import com.app.vardrobe.databinding.ActivityCameraBinding
import com.github.gabrielbb.cutout.CutOut
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CameraActivity : AppCompatActivity(), CameraActions {
    private val cameraViewModel by viewModels<CameraViewModel>()
    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        val span =
            if (application.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                PROTRAIT_SPAN
            else LANDSCAPE_SPAN
        val adapter = GalleryAdapter()
        binding.let {
            it.actions = this
            it.rvGallery.adapter = adapter
            it.rvGallery.layoutManager = GridLayoutManager(this, span)
        }
        cameraViewModel.imageList.observe(this, Observer { adapter.submitList(it) })
        cameraViewModel.getOutPutImage().observe(this, Observer {
            binding.fabCapture.isEnabled = true
            CutOut.activity()
                .src(it)
                .start(this@CameraActivity)
        })
        setUpCamera()
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(this@CameraActivity)

        cameraProviderFuture.addListener(Runnable {
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val preview = Preview.Builder().build()

            cameraProviderFuture.get()
                .bindToLifecycle(this,
                    cameraSelector,
                    cameraViewModel.imageCapture,
                    preview
                )
            preview.setSurfaceProvider(binding.viewFinder.createSurfaceProvider())
        }, ContextCompat.getMainExecutor(this)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraViewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCapture(view: View) {
        binding.fabCapture.isEnabled = false
        cameraViewModel.takePicture()
    }

    override fun openGallery(view: View) {
        BottomSheetBehavior.from(binding.bottomSheet).state =
            BottomSheetBehavior.STATE_HALF_EXPANDED
    }

}