package com.example.proyecto_ong

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdaptadorFranja (context: Context, val franjas: List<Franja>): ArrayAdapter<Franja>(context, android.R.layout.simple_spinner_item, franjas) {

    //recoge id de franja
    override fun getItemId(position: Int): Long {
        return franjas[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = franjas[position].hora
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = franjas[position].hora
        return view

    }
}