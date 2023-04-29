package com.example.proyecto_ong

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class RegistrarDatosFragment : Fragment() {

    var hayLluvia: Int = 0
    var hayCortesAgua : Int = 0
    var hayNiebla : Int = 0
    var idRegistro:Int=-1
    var totalFranjas:Int=-1


    //acceso a BBDD
    private val db = FirebaseFirestore.getInstance()

    private var _binding: FragmentRegistrarDatosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrarDatosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //USUARIO
        var usuarioID = (activity as MainActivity).idUsuarioApp

        //SPINNER NIEBLA
        var densidadNiebla = arrayOf("Poca", "Media", "Mucha")
        val spinner = binding.sDensidad
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.simple_spinner_item, densidadNiebla)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        //SPINNER FRANJA
        // ********* HAY QUE CAMBIAR POR OTRO(MAS FRANJAS)
        (activity as MainActivity).miViewModel.mostrarTodasFranjas()
        (activity as MainActivity).miViewModel.listaFranjas.observe(activity as MainActivity){
            binding.sFranja.adapter= AdaptadorFranja(activity as MainActivity, it)
            totalFranjas=it.count()
        }

        idRegistro=arguments?.getInt("id") ?:-1
        //la de BD
        var miRegistro =CondicionMeteorologica()
        if(idRegistro == -1){
            binding.bRegistrarDatos.setText("Registrar Datos")
        }
        else{
            binding.bRegistrarDatos.setText("Modificar Datos")
        }


        //formato de la fecha de registro
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDateandTime: String = sdf.format(Date())
        binding.tvCalendario.setText(currentDateandTime)
        binding.sDensidad.isEnabled = false

        //*** BOTON QUE NO NECESITO, PORQUE TENGO EN MENU
        binding.bRegistrarDatos.setOnClickListener {

            var fechaRegistrada = binding.tvCalendario.text
            validarCheckbox()

            if(binding.bRegistrarDatos.text == "Insertar Datos"){
                if (validarContenido()) guardar(usuarioID)
                // Después de guardar los datos, muestra un mensaje de éxito al usuario
                Toast.makeText(context, "Datos se han registrado correctamente", Toast.LENGTH_SHORT).show()
            }

            if(binding.bRegistrarDatos.text == "Modificar Datos"){
                if (validarContenido()) modificar(idRegistro)
                // Después de modificar los datos, muestra un mensaje de éxito al usuario
                Toast.makeText(context, "Datos se han modificado correctamente", Toast.LENGTH_SHORT).show()

            }
            // Navega de vuelta al fragmento de recycled view
            findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)

        }

        // Agrega el listener para el text view de fecha de registracion de datos
        binding.tvCalendario.setOnClickListener {
            //DatePickerDialog para permitir al usuario seleccionar dia de registrtacion de datos
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.tvCalendario.setText(selectedDate)
                }, year, month, day)

            // Muestra el DatePickerDialog
            datePickerDialog.show()

        }

        binding.cbNiebla.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay niebla, se deshabilite spinner de densidad de niebla
            binding.sDensidad.isEnabled = binding.cbNiebla.isChecked
        }


        //MENU DE BORRAR
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                if (idRegistro != -1) menuInflater.inflate(R.menu.menu_modificar,menu)
                //else menuInflater.inflate(R.menu.menu_listadatos, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    /* R.id.miGuardar -> {
                         if(validarContenido()) guardar()
                         true
                     }
                     R.id.miModificar -> {
                         if(validarContenido()) modificar(idRegistro)
                         true
                     }*/
                    R.id.miBorrar -> {
                        borrar(miRegistro)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun borrar(miRegistro: CondicionMeteorologica) {
        (activity as MainActivity).miViewModel.borrarRegistro(miRegistro)
        Toast.makeText(activity,"Registro eliminado", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
    }

    private fun modificar(idDato: Int) {
        (activity as MainActivity).miViewModel.modificar(CondicionMeteorologica(
            idRegistro,
            fechaRegistro = binding.tvCalendario.text.toString(),
            hayNiebla= hayNiebla,
            hayAgua = hayCortesAgua,
            hayLluvia = hayLluvia,
            densidad =  binding.sDensidad.selectedItem.toString(),
        )
        )
        Toast.makeText(activity,"Registro modificado", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
    }

    private fun guardar(id: Int) {
        (activity as MainActivity).miViewModel.insertarRegistro(
            CondicionMeteorologica(
            fechaRegistro = binding.tvCalendario.text.toString(),
            hayNiebla= hayNiebla,
            hayAgua = hayCortesAgua,
            hayLluvia = hayLluvia,
            densidad =  binding.sDensidad.selectedItem.toString(),
                idUsuario = id)
        )
        Toast.makeText(activity,"Registro insertado", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
    }

    private fun validarContenido(): Boolean {
        if(binding.sFranja.selectedItem == null){
            Toast.makeText(activity,"No hay franja seleccionada",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun validarCheckbox() {
        if (binding.cbLluvia.isChecked) {
            //hay lluvia
            hayLluvia = 1
        }
        if (binding.cbAgua.isChecked) {
            //hay lluvia
            hayCortesAgua = 1
        }
        if (binding.cbNiebla.isChecked) {
            //hay lluvia
            hayNiebla = 1
        }
    }

    fun setearSpiner(idGenero:Int){
        if(totalFranjas!=-1){
            for (i in 0 until totalFranjas) {
                val option = binding.sFranja.adapter.getItemId(i).toInt()
                if (option == idGenero) {
                    binding.sFranja.setSelection(i)
                    break
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
