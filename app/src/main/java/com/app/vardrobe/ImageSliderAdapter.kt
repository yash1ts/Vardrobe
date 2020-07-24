package com.app.vardrobe

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.vardrobe.R.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_image_slider.view.*


class ImageSliderAdapter(private val imageList: List<Drawable>):
    RecyclerView.Adapter<Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layout.item_image_slider,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.image.setImageDrawable(imageList[position])
    }

}