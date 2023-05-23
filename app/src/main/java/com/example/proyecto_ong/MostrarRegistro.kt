package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentMostrarRegistroBinding
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding

class MostrarRegistro : Fragment() {

    private var _binding: FragmentMostrarRegistroBinding? = null
    private val binding get() = _binding!!
    var idRegistro: String = "-1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //no funciona el ID, siempre es -1
        idRegistro = arguments?.getString("id") ?: "-1"

        binding.menuBarMostrarDatos.setNavigationIcon(R.drawable.ic_back)
        binding.menuBarMostrarDatos.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_mostrarRegistro_to_SecondFragment)
        }


        try {
            var miRegistro = Registro()
            (activity as MainActivity).miViewModel.buscarRegistroPorId(idRegistro)
            (activity as MainActivity).miViewModel.miRegistro.observe(activity as MainActivity) {
                miRegistro = it
                binding.tvFecha.setText(it.fecha)
                if (it.niebla.equals("")) {
                    binding.tvNieblaIntensidadMostrar.setText("-")
                }

                //anadir franjas horarias


                if (it.lluvia.equals("")) {
                    binding.tvLluviaDuracionMostrar.setText("0:0")
                }
                else {
                    binding.tvLluviaDuracionMostrar.setText(it.lluvia)
                }


                if (it.agua.equals("")) {
                    binding.tvAguaDuracionMostrar.setText("0:0")
                }else {
                    binding.tvAguaDuracionMostrar.setText(it.agua)
                }


                if (it.incidencias.equals("")) {
                    binding.tvComentariosMostrar.setText("No hay comentarios.")
                }
                else {
                    binding.tvComentariosMostrar.setText(it.incidencias)
                }


            }
        } catch (e: Exception) {
            Toast.makeText(activity as MainActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
