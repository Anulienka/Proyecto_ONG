package com.example.proyecto_ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentFirstBinding
import com.example.proyecto_ong.databinding.FragmentRegistrarUsuarioBinding
import kotlinx.coroutines.launch

class RegistrarUsuarioFragment : Fragment() {

    //anado binding de fragmento
    //para acceder a las variables
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
            if(validarDatosUsuario()) {
                    registrarUsuario()
            }
        }
    }

    private fun registrarUsuario() {

        (activity as MainActivity).miViewModel.insertarUsuario(binding.etNombre.text.toString(),binding.etContrasena1.text.toString(),binding.sRegion.selectedItem.toString())
        Toast.makeText(activity,"Usuario se ha registrado correctamente", Toast.LENGTH_LONG).show()

        //busco usuario en BD para recoger su ID
        (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
        val miUsuario: Usuario? = (activity as MainActivity).miViewModel.miUsuario
        if(miUsuario == null){
            //si no existe usuario, se muestra el mensaje
            Toast.makeText(activity,"Usuario no existe", Toast.LENGTH_LONG).show()
        }else{
            (activity as MainActivity).idUsuarioApp = miUsuario.id
            findNavController().navigate(R.id.action_registrarUsuarioFragment_to_SecondFragment)
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
        }else if(binding.sRegion.selectedItem == null) {
            showErrorSpinner(binding.sRegion, "Campo region es obligatorio.")
            return false
        }

        var existeUsuario = false
        //BUSCA SI USUARIO YA EXISTE
        if (existeUsuario){
            //usuario existe y retorna false
            Toast.makeText(activity,"Usuario ya existe.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun existeUsuario(): Boolean {
        //busco en BD si existe usuario
        var usuarioExiste = true
        (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
        val miUsuario: Usuario? = (activity as MainActivity).miViewModel.miUsuario
        if (miUsuario == null){
            usuarioExiste = false
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