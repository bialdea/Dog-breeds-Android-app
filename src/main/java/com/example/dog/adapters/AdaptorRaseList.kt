package com.example.dog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.dog.R

class AdaptorRaseList(private val context: Context, private val dataSource: List<String>) : ArrayAdapter<String>(context, R.layout.element_lista_rase, dataSource) {//este o apelare a constructorului clasei de bază ArrayAdapter, care necesită trei parametri: contextul, un ID al unui layout pentru elementul din listă și sursa de date
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(context)
        val rowView = convertView ?: layoutInflater.inflate(R.layout.element_lista_rase, parent, false)

        val textView = rowView.findViewById<TextView>(R.id.rasaTitluTextView)
        textView.text = dataSource[position]

        return rowView
    }
}