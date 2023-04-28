package com.example.proyecto_ong

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentFirstBinding
import com.google.android.material.textfield.TextInputEditText

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
            if(validarDatos()){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                //findNavController().navigate(R.id.action_FirstFragment_to_ONGFragment)
            }
        }

        binding.bRegistrarUsuario.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_registrarUsuarioFragment)
        }
    }

    private fun validarDatos(): Boolean {
       if(binding.etUsername.text.toString().isEmpty() || binding.etUsername.text.toString().length < 3){
           showError(binding.etUsername, "El usuario debe que tener minimo 3 carácteres.")
           return false
       }else if (binding.etPassword.text.toString().isEmpty()){
           showError(binding.etPassword, "Campo contraseña es obligatorio.")
           return false
       }
        else{
            //***** no funciona
           if(binding.etUsername.text.toString() != "Anna" && binding.etPassword.text.toString() != "123"){
               Toast.makeText(activity,"Usuario o contraseña invalido.", Toast.LENGTH_LONG).show()
               return false
           }
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