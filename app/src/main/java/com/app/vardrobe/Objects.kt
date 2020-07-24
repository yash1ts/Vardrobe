package com.app.vardrobe

import androidx.recyclerview.widget.DiffUtil
import com.app.vardrobe.camera.ImageModel

object DiffUtilImage: DiffUtil.ItemCallback<ImageModel>(){
    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
        oldItem.uri == oldItem.uri



}