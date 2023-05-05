package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_ong.databinding.FragmentSecondBinding

class ONGListaFragment : Fragment(R.layout.fragment_o_n_g_lista) {
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var miRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_n_g_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //se recogen datos de BD para llenar RecyclerView
        (activity as MainActivity).miViewModel.listaRegistrosObjetos.observe(activity as MainActivity){
                registros->
            miRecyclerView = binding.rvRegistros
            miRecyclerView.layoutManager = LinearLayoutManager(activity)
            miRecyclerView.adapter=Adaptador(registros as MutableList<CondicionMeteorologicaClase>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).miViewModel.listaRegistrosObjetos.removeObservers(activity as MainActivity)
    }
}