package com.example.proyecto_ong

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentFirstBinding

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

        if (usuarioEstaLogeado()) {
            //si esta logeado, directamente va a 2 fragmento
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.bEntrar.setOnClickListener {
            if (validarDatos()) {
                buscarUsuario()
            }
        }

        binding.bRegistrarUsuario.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_registrarUsuarioFragment)
        }
    }

    fun buscarUsuario() {
        //var miUsuario = Usuario()
        try {
            (activity as MainActivity).miViewModel.buscarUsuario(binding.etUsername.text.toString())
            var miUsuario = (activity as MainActivity).miViewModel.miUsuario
            if(miUsuario != null){
                if (binding.etUsername.text.toString() == miUsuario.nombre && binding.etPassword.text.toString() == miUsuario.contrasena) {
                    //guarda nombre y contrasena de usuario en fichcero credenciales
                    guardarPreferencias(miUsuario)
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                }else {
                        Toast.makeText(activity, "Contraseña no esta correcta", Toast.LENGTH_LONG).show()
                        //selecciona la contrasena
                        binding.etPassword.selectAll()
                    }
            }
            else{
                Toast.makeText(activity, "No existe el usuario", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            //usuaro no existe
            Toast.makeText(activity as MainActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun guardarPreferencias(miUsuario: Usuario) {

        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Guarda los datos de inicio de sesión
        editor.putString("nombre_usuario", miUsuario.nombre)
        editor.putString("contrasena", miUsuario.contrasena)
        editor.putString("id", miUsuario.id)
        editor.apply()
    }


    private fun usuarioEstaLogeado(): Boolean {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)

        // Recupera los datos de inicio de sesión
        val nombreUsuario = sharedPreferences.getString("nombre_usuario", null)
        val contrasena = sharedPreferences.getString("contrasena", null)

        //si no devuelve nada, no esta logeado
        if (nombreUsuario == null && contrasena == null) {
            return false
        }
        return true
    }


    private fun validarDatos(): Boolean {
        if (binding.etUsername.text.toString()
                .isEmpty() || binding.etUsername.text.toString().length < 3
        ) {
            showError(binding.etUsername, "El usuario debe que tener minimo 3 carácteres.")
            return false
        }
        if (binding.etPassword.text.toString().isEmpty()) {
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