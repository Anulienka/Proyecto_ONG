package com.example.proyecto_ong

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class AdaptadorFranja (context: Context, val franjas: List<Franja>): ArrayAdapter<Franja>(context, android.R.layout.simple_spinner_item, franjas) {

    private val selectedItems = HashSet<Int>()

    //recoge id de franja
    override fun getItemId(position: Int): Long {
        return franjas[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val checkBox = view.findViewById<CheckBox>(android.R.id.text1)
        checkBox.isChecked = selectedItems.contains(position)

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                selectedItems.add(position)
            } else {
                selectedItems.remove(position)
            }
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val checkBox = view.findViewById<CheckBox>(android.R.id.text1)
        checkBox.isChecked = selectedItems.contains(position)

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                selectedItems.add(position)
            } else {
                selectedItems.remove(position)
            }
        }

        return view

    }
}