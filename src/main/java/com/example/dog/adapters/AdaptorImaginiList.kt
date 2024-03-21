package com.example.dog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import com.bumptech.glide.Glide
import com.example.dog.R

class AdaptorImaginiList(private val context: Context, private val dataSource: List<String>, private val favorites: Set<String>) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): String = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(R.layout.activity_images_dog, parent, false)
        val imageView: ImageView = view.findViewById(R.id.imageDogView)

        Glide.with(context).load(getItem(position)).into(imageView)

        val favoriteIndicator: ImageView = view.findViewById(R.id.star)
        if (dataSource[position] in favorites) {
            favoriteIndicator.visibility = View.VISIBLE
        } else {
            favoriteIndicator.visibility = View.GONE
        }

        return view
    }

}
