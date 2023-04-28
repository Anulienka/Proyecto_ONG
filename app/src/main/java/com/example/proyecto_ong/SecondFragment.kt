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

        //en menu esta iconito para anadir datos
        /*val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_listadatos, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miInsertar -> {
                        //no se porque es registrarDatos Fragment2
                        findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)*/


        //datos se anaden con boton
        binding.bAnadirDatos.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_registrarDatosFragment)
        }

        miRecyclerView = binding.rvDatosUsuario
        miRecyclerView.layoutManager = LinearLayoutManager(activity)
        miRecyclerView.adapter=Adaptador((activity as MainActivity).condicionesMeteorologicos)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}