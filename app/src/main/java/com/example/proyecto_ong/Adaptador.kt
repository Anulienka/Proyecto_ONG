package com.example.proyecto_ong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


//PARA RECYCLEDVIEW
class Adaptador(val condicionesMeteorologicos: MutableList<CondicionMeteorologicaClase>) : RecyclerView.Adapter<Adaptador.ViewHolder>() {
    inner class ViewHolder (v: View): RecyclerView.ViewHolder(v){
        //datos de recycled view, de contenedor
        var tvDia: TextView
        var tvFranja: TextView
        var id:Int=-1
        init{
            tvDia=v.findViewById(R.id.tvFechaView)
            tvFranja=v.findViewById(R.id.tvFranjaView)
            v.setOnClickListener{
                val bundle= bundleOf("id" to id)
                v.findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.cadrdview_contenedor, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDia.text="Fecha: ${condicionesMeteorologicos[position].dia}"
        holder.tvFranja.text="Franja: ${condicionesMeteorologicos[position].franja}"
        holder.id=position
    }

    override fun getItemCount(): Int {
        return condicionesMeteorologicos.count()
    }
}