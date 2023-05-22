package com.example.proyecto_ong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextClock
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


//PARA RECYCLEDVIEW
class Adaptador(var registros: List<Registro>) : RecyclerView.Adapter<Adaptador.ViewHolder>() {
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        //datos de recycled view, de contenedor
        var tvDia: TextView
        var tvNiebla: TextView
        var tvLluvia: TextView
        var tvAgua: TextView
        var tvCaudalimetro: TextView

        //var tvFranja: TextView
        var id: Int = -1

        init {
            tvDia = v.findViewById(R.id.tvFechaView)
            tvNiebla = v.findViewById(R.id.tvNieblaCV)
            tvLluvia = v.findViewById(R.id.tvLLuviaCV)
            tvAgua = v.findViewById(R.id.tvAguaCV)
            tvCaudalimetro = v.findViewById(R.id.tvCaudalimetroCV)
            //tvFranja=v.findViewById(R.id.tvFranjaView)
            v.setOnClickListener {
                val bundle = bundleOf("id" to id)
                v.findNavController()
                    .navigate(R.id.action_SecondFragment_to_registrarDatosFragment, bundle)
            }
        }
    }

    fun actualizarRegistros(nuevosRegistros: List<Registro>) {
        registros = nuevosRegistros
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cadrdview_contenedor, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDia.text = "${registros[position].fecha}"
        holder.tvAgua.text = "Duración de cortes de agua: ${registros[position].agua}"
        holder.tvNiebla.text = "Densidad de niebla: ${registros[position].niebla}"
        holder.tvLluvia.text = "Duración de lluvia: ${registros[position].lluvia}"
        holder.tvCaudalimetro.text = "CAUDALÍMETRO:\n ${registros[position].m3} m3; ${registros[position].litros} l; ${registros[position].ml} ml"
        holder.id = position
    }

    override fun getItemCount(): Int {
        return registros.count()
    }
}