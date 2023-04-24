package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentFirstBinding
import com.example.proyecto_ong.databinding.FragmentRegistrarUsuarioBinding

class RegistrarUsuarioFragment : Fragment() {

    //anado binding de fragmento
    private var _binding: FragmentRegistrarUsuarioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrarUsuarioBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bRegistrar.setOnClickListener {
            if(validarDatosUsuario()){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }


    }

    private fun validarDatosUsuario(): Boolean {
        if(binding.etNombre.text.toString().isEmpty() || binding.etNombre.text.toString().length < 3){
            showError(binding.etNombre, "El usuario debe que tener minimo 3 carácteres.")
            return false
        }else if (binding.etContrasena1.text.toString().isEmpty()){
            showError(binding.etContrasena1, "Campo contraseña es obligatorio.")
            return false
        }else if (binding.etContrasena2.text.toString().isEmpty()) {
            showError(binding.etContrasena2, "Campo contraseña es obligatorio.")
            return false
        }else if(binding.etContrasena1.text.toString() != binding.etContrasena2.text.toString()){
            showError(binding.etContrasena2, "Contreseñas no son iguales.")
            return false
        }
        return true

        //FALTA BUSCAR SI USUARIO YA EXISTE
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