package com.example.proyecto_ong

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Transformation
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentRegistrarUsuarioBinding

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

        binding.menuBarUsuario.setNavigationIcon(R.drawable.ic_back)
        binding.menuBarUsuario.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_registrarUsuarioFragment_to_FirstFragment)
        }

        //SPINNER REGIONES
        var regiones = arrayOf("Veladero", "Sivingalito", "Pucuta", "Chaquemarca")
        val spinner = binding.sRegion
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, regiones)
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
        try {
            //primero inserta usuario
            (activity as MainActivity).miViewModel.insertarUsuario(Usuario(
                nombre = binding.etNombre.text.toString(),
                contrasena = binding.etContrasena1.text.toString(),
                region = binding.sRegion.selectedItem.toString()))

            //var miUsuario = Usuario()
            (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
            var miUsuario = (activity as MainActivity).miViewModel.miUsuario
            if (miUsuario != null){
                guardarPreferencias(miUsuario)
                findNavController().navigate(R.id.action_registrarUsuarioFragment_to_SecondFragment)
            }

            //luego busco usuario en BD para recoger su ID
//            (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
//            (activity as MainActivity).miViewModel.miUsuario.observe(activity as MainActivity){ usuario->
//                miUsuario = usuario
//                //asigno id de usuario registrado
//                guardarPreferencias(miUsuario)
//                Toast.makeText(activity,"Usuario se ha registrado correctamente", Toast.LENGTH_LONG).show()
//                findNavController().navigate(R.id.action_registrarUsuarioFragment_to_SecondFragment)
//            }
        }
        catch (e:Exception){
            Toast.makeText(activity as MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun guardarPreferencias(miUsuario: Usuario) {

        val sharedPreferences = requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
            editor.putString("nombre_usuario", miUsuario.nombre)
            editor.putString("contrasena", miUsuario.contrasena)
            editor.putString("id", miUsuario.id)
            editor.apply()
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
            binding.etContrasena2.selectAll()
            return false
        }else if(binding.sRegion.selectedItem == null) {
            showErrorSpinner(binding.sRegion, "Campo region es obligatorio.")
            return false
        }

        //BUSCA SI USUARIO YA EXISTE
        if (existeUsuario()){
            //usuario existe y retorna false
            Toast.makeText(activity,"Usuario con mismo nombre ya existe.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun existeUsuario(): Boolean {
        //busco en BD si existe usuario
        var usuarioExiste = false
        //buaco si existe usuario con mismo nombre
       // var miUsuario = Usuario()
        (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
        var miUsuario = (activity as MainActivity).miViewModel.miUsuario
        if (miUsuario != null){
            usuarioExiste = true
        }
//        try {
//            //******* no funciona
//            (activity as MainActivity).miViewModel.buscarUsuario(binding.etNombre.text.toString())
//            (activity as MainActivity).miViewModel.miUsuario.observe(activity as MainActivity) { usuario ->
//                miUsuario = usuario
//                usuarioExiste = true
//            }
//        } catch (e: Exception) {
//            Toast.makeText(activity as MainActivity, e.message, Toast.LENGTH_LONG).show()
//        }
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