package com.example.proyecto_ong

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentFirstBinding
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bEntrar.setOnClickListener {
            if(validarDatos()) {
                //Las funciones suspendidas solo pueden ser llamadas desde otras funciones suspendidas
                // o dentro de un bloque de corutina
                lifecycleScope.launch {
                    buscarUsuario()
                }
            }
            }

        binding.bRegistrarUsuario.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_registrarUsuarioFragment)
        }
    }

    suspend fun buscarUsuario() {

        (activity as MainActivity).miViewModel.buscarUsuario(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        val miUsuario: Usuario? = (activity as MainActivity).miViewModel.miUsuario
        if(miUsuario == null){
            //si no existe usuario, se muestra el mensaje
            Toast.makeText(activity,"Usuario no existe", Toast.LENGTH_LONG).show()
        }
        else{
            //si existe, se asigna a idUsuario id de usuario que usa la app
            (activity as MainActivity).idUsuarioApp = miUsuario.id.toString()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    private fun validarDatos(): Boolean {
       if(binding.etUsername.text.toString().isEmpty() || binding.etUsername.text.toString().length < 3){
           showError(binding.etUsername, "El usuario debe que tener minimo 3 carácteres.")
           return false
       }
       if (binding.etPassword.text.toString().isEmpty()){
           showError(binding.etPassword, "Campo contraseña es obligatorio.")
           return false
       }
        return true
    }

    private fun showError(input: EditText, s: String) {
        input.setError(s)
        input.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}