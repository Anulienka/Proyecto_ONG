package com.example.proyecto_ong

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_ong.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_listadatos, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miLogout -> {
                        guardarPreferencias()
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        //datos se anaden con boton
        binding.bAnadirDatos.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment)
        }

//        //se recogen datos de BD para llenar RecyclerView
//        (activity as MainActivity).miViewModel.registros.observe(activity as MainActivity){
//                registros->
//            miRecyclerView = binding.rvRegistros
//            miRecyclerView.layoutManager = LinearLayoutManager(activity)
//            //adaptador de RecyclerView
//            miRecyclerView.adapter=Adaptador(registros as MutableList<Registro>)
//        }

    }

    private fun guardarPreferencias() {

        val sharedPreferences = requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Se borran los datos de sharedPreferences
        editor.putString("nombre_usuario", "")
        editor.putString("contrasena", "")
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //(activity as MainActivity).miViewModel.listaRegistrosObjetosUsuario.removeObservers(activity as MainActivity)
    }
}