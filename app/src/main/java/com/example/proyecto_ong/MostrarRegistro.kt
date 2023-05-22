package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentMostrarRegistroBinding
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding

class MostrarRegistro : Fragment() {

    private var _binding: FragmentMostrarRegistroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuBarMostrarDatos.inflateMenu(R.menu.menu_guardar)


        binding.menuBarMostrarDatos.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miAtras -> {
                    findNavController().navigate(R.id.action_mostrarRegistro_to_SecondFragment)
                    true
                }
                else -> false
            }
        }

    //        var miRegistro = Registro()
//        if(idRegistro=="-1"){
//            binding.bRegistrarDatos.setText("Registrar datos")
//        }
//        else{
//            binding.bRegistrarDatos.setText("Borrar registro")
//
//            //deshabilito todos componentes en linear layout, porque usuario no puede modificar datos, solo borrar
//            for (i in 0 until binding.linlayDatos.childCount) {
//                val view: View = binding.linlayDatos.getChildAt(i)
//                view.isEnabled = false
//            }
//
//            try {
//                (activity as MainActivity).miViewModel.buscarRegistroPorId(idRegistro)
//                (activity as MainActivity).miViewModel.miRegistro.observe(activity as MainActivity){
//                    miRegistro=it
//                    binding.tvCalendario.setText(it.fecha)
//                    if(!it.niebla.equals("")){
//                      binding.cbNiebla.isChecked = true
//                      //seleccionar franja
//                    }
//                    if(!it.lluvia.equals("")){
//                        binding.cblluvia.isChecked = true
//                        binding.etHoraLluvia.setText(it.lluvia)
//                    }
//                    if(!it.agua.equals("")){
//                        binding.cbAgua.isChecked = true
//                        binding.etHoraAqua.setText(it.agua)
//                    }
//                    if(!binding.tvIncidencias.equals("")){
//                        binding.etComentario.setText(it.incidencias)
//                    }
//                    binding.etM3.setText(it.m3.toString())
//                    binding.etLitros.setText(it.litros.toString())
//                    binding.etMl.setText(it.ml.toString())
//
//                }
//            }
//            catch (e:Exception){
//                Toast.makeText(activity as MainActivity,e.message,Toast.LENGTH_LONG).show()
//            }
//        }
    }
}