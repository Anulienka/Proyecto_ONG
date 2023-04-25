package com.example.proyecto_ong

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding
import java.text.SimpleDateFormat
import java.util.*

class RegistrarDatosFragment : Fragment() {

    var hayLluvia: Int = 0
    var hayCortesAgua : Int = 0
    var hayNiebla : Int = 0
    var idDato:Int=-1


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
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //la de BD
        val miCondicionMeteorologica = CondicionMeteorologica()

        //cuando se crea la vista
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDateandTime: String = sdf.format(Date())
        binding.tvCalendario.setText(currentDateandTime)

        //si no esta marcado que hay niebla, se deshabilite spinner de densidad de niebla
        if(!binding.cbNiebla.isChecked){
            binding.sDensidad.isEnabled = false
        }

        //*** BOTON QUE NO NECESITO, PORQUE TENGO EN MENU
        /*binding.bRegistrarDatos.setOnClickListener {

            var fechaRegistrada = binding.tvCalendario.text
            validarCheckbox()

            if(binding.bRegistrarDatos.text == "Insertar Datos"){
                // Después de guardar los datos, muestra un mensaje de éxito al usuario
                Toast.makeText(context, "Datos se han registrado correctamente", Toast.LENGTH_SHORT).show()

                // Navega de vuelta al fragmento de inicio de sesión
                findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
            }

            if(binding.bRegistrarDatos.text == "Modificar Datos"){
                // Después de modificar los datos, muestra un mensaje de éxito al usuario
                Toast.makeText(context, "Datos se han modificado correctamente", Toast.LENGTH_SHORT).show()

                // Navega de vuelta al fragmento de inicio de sesión
                findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
            }

        }*/

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                if (idDato==-1) menuInflater.inflate(R.menu.menu_guardar,menu)
                else menuInflater.inflate(R.menu.menu_modificar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miGuardar -> {
                        if(validarContenido()) guardar()
                        true
                    }
                    R.id.miModificar -> {
                        if(validarContenido()) modificar(idDato)
                        true
                    }
                    R.id.miBorrar -> {
                        borrar(miCondicionMeteorologica)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun borrar(miCondicionMeteorologica: CondicionMeteorologica) {

    }

    private fun modificar(idDato: Int) {

    }

    private fun guardar() {
        TODO("Not yet implemented")
    }

    private fun validarContenido(): Boolean {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}