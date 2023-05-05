package com.example.proyecto_ong

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_ong.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var miRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //datos se anaden con boton
        binding.bAnadirDatos.setOnClickListener {
            //findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment)
        }

        //se recogen datos de BD para llenar RecyclerView
        (activity as MainActivity).miViewModel.listaRegistrosObjetosUsuario.observe(activity as MainActivity){
                registros->
            miRecyclerView = binding.rvRegistros
            miRecyclerView.layoutManager = LinearLayoutManager(activity)
            //adaptador de RecyclerView
            miRecyclerView.adapter=Adaptador(registros as MutableList<CondicionMeteorologicaClase>)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).miViewModel.listaRegistrosObjetos.removeObservers(activity as MainActivity)
    }
}