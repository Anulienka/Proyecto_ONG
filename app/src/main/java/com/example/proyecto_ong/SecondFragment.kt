package com.example.proyecto_ong

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
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

        binding.menuBar.inflateMenu(R.menu.menu_listadatos)
        //binding.menuBar.title = "Mis registros"
        binding.menuBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miLogout -> {
                    guardarPreferencias()
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    true
                }
                else -> false
            }
        }


        //datos se anaden con boton
        binding.bAnadirDatos.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment)
        }

        //se recogen datos de BD para llenar RecyclerView
        (activity as MainActivity).miViewModel.mostrarRegistrosUsuario(usuarioid())
        (activity as MainActivity).miViewModel.listaRegistrosUsuario.observe(activity as MainActivity) {
            miRecyclerView = binding.rvRegistros
            miRecyclerView.layoutManager = LinearLayoutManager(activity)
            //adaptador de RecyclerView
            miRecyclerView.adapter = Adaptador(it)
        }
    }

    private fun guardarPreferencias() {

        val sharedPreferences =
            requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Se borran los datos de sharedPreferences
        editor.putString("nombre_usuario", null)
        editor.putString("contrasena", null)
        editor.apply()
    }

    private fun usuarioid(): String? {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        // Recupera los datos de inicio de sesión
        val id = sharedPreferences.getString("id", null)
        return id
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).miViewModel.listaRegistrosUsuario.removeObservers(activity as MainActivity)
    }
}

