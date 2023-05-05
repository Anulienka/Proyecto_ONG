package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        //SPINNER REGIONES
        var densidadNiebla = arrayOf("Veladero", "Sivingalito", "Pucuta", "Chaquemarca")
        val spinner = binding.sRegion
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, densidadNiebla)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        //REGISTRA USUARIO
        binding.bRegistrar.setOnClickListener {
            if(validarDatosUsuario()) registrarUsuario()
        }
    }

    private fun registrarUsuario() {

        (activity as MainActivity).miViewModel.insertarUsuario(
            Usuario(
                nombre = binding.etNombre.text.toString(),
                contrasena = binding.etContrasena1.text.toString(),
                region = binding.sRegion.selectedItem.toString(),
                //todos usuarios que se registran tienen rol USUARIO
                rol = "usuario")
        )
        Toast.makeText(activity,"Usuario se ha registrado correctamente", Toast.LENGTH_LONG).show()

        //busco usuario en BD para recoger su ID
        var miUsuario = Usuario()
        (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString(), binding.etContrasena1.text.toString())
        (activity as MainActivity).miViewModel.miUsuario.observe(activity as MainActivity) { usuario ->
            miUsuario = usuario
            //asigno id a idUsuarioApp
            (activity as MainActivity).idUsuarioApp = miUsuario.id
        }

        //voy a second fragment, donde aparece lista de lod registros de usuario
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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
        }else if(binding.sRegion.selectedItem == null) {
            showErrorSpinner(binding.sRegion, "Campo region es obligatorio.")
            return false
        }


        //BUSCA SI USUARIO YA EXISTE
        if(existeUsuario()){
            //usuario existe y retorna false
            Toast.makeText(activity,"Usuario ya existe.", Toast.LENGTH_LONG).show()
            return false
        }

        //si se cumplen requisitos, retorna true
        return true
    }


    private fun existeUsuario(): Boolean {
        var usuarioExiste = true
        var miUsuario = Usuario()
        (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString(), binding.etContrasena1.text.toString())
        (activity as MainActivity).miViewModel.miUsuario.observe(activity as MainActivity) { usuario ->
            miUsuario = usuario
            if (miUsuario == null){
                usuarioExiste = false
            }
        }
        return usuarioExiste
    }

    private fun showError(input: EditText, s: String) {
        input.setError(s)
        input.requestFocus()
    }


    private fun showErrorSpinner(sRegion: Spinner, s: String) {
        val errorText = sRegion.selectedView as TextView
        errorText.setError(s)
        errorText.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}