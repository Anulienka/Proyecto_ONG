package com.example.proyecto_ong

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_ong.databinding.FragmentONGBinding
import androidx.fragment.app.FragmentManager


class ONGFragment : Fragment(){

    private var _binding: FragmentONGBinding? = null
    private var currentFragment: Fragment? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding: FragmentONGBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_n_g, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeFragment(ONGListaFragment())

        // Configurar el comportamiento del botom menú
        binding.bottomNvONG.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_lista -> {
                    // Acciones para el elemento "LISTA"
                    changeFragment(ONGListaFragment())
                    true

                }
                R.id.menu_grafico -> {
                    // Acciones para el elemento "GRAFICO"
                    changeFragment(ONGGraficosFragment())
                    true
                }
                else -> false
            }
        }

    }


    private fun changeFragment(fragment: Fragment) {
        if (fragment != currentFragment) {
            currentFragment = fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.main_content_container, fragment)
            if (transaction != null) transaction.commit()
        }
    }
}