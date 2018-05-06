package com.of.smallgallery

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.of.smallgallery.db.Image



class GridAdapter(private val context: Activity, private val images: ArrayList<Image>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var gridView: View

        if (convertView == null) {
            gridView = View(context)
            gridView = inflater.inflate(R.layout.grid_item_view, null)
            val imageView = gridView.findViewById(R.id.image) as ImageView
            Glide.with(context).load(images[position].path).into(imageView)
        } else {
            gridView = convertView
        }

        return gridView
    }

    override fun getItem(position: Int) = images[position]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = images.size

}