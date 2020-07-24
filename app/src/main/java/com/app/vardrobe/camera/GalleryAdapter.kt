package com.app.vardrobe.camera

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagedListAdapter
import com.app.vardrobe.DiffUtilImage
import com.app.vardrobe.Holder
import com.app.vardrobe.R
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter() : PagedListAdapter<ImageModel, Holder>(
    DiffUtilImage
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.let{
            it.gallery.setImageBitmap(getItem(position)?.tumbnail)
        }
    }
}