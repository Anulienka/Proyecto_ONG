package com.example.proyecto_ong

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.type.DateTime
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegistrarDatosFragment : Fragment() {

    private var _binding: FragmentRegistrarDatosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrarDatosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sDensidad.isEnabled = false
        binding.tvLluvia.isEnabled = false
        binding.tvAgua.isEnabled = true


        //SPINNER NIEBLA
        var densidadNiebla = arrayOf("Intensa", "Normal", "Poco intensa")
        val spinner = binding.sDensidad
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, densidadNiebla)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

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


        // Agrega el listener para el text view de hora de registracion de datos
        //AGUA
        binding.tvHoraAqua.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d AM", hourOfDay, minute)
                binding.tvHoraAqua.text = selectedTime
            }, hour, minute, true)

            timePickerDialog.show()
        }

        // Agrega el listener para el text view de hora de registracion de datos
        //LLUVIA
        binding.tvHoraLluvia.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d AM", hourOfDay, minute)
                binding.tvHoraLluvia.text = selectedTime
            }, hour, minute, false)
            timePickerDialog.show()
        }


        //SPINER FRANJAS
        (activity as MainActivity).miViewModel.mostrarFranjas()
        (activity as MainActivity).miViewModel.listaFranjas.observe(activity as MainActivity){
            binding.sFranjas.adapter= AdaptadorFranja(activity as MainActivity, it)
        }

        binding.cbNiebla.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay niebla, se deshabilite spinner de densidad de niebla
            binding.sDensidad.isEnabled = binding.cbNiebla.isChecked
        }

        binding.cblluvia.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay lluvia, se deshabilite textView de hora
            binding.tvLluvia.isEnabled = binding.cblluvia.isChecked
        }

        binding.cbAgua.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay cortes de agua, se deshabilite textView de hora
            binding.tvAgua.isEnabled = binding.cbAgua.isChecked
        }

        binding.bRegistrarDatos.setOnClickListener{
            if(validarDatos()){
                adjustarDatosGuardarDatos()

            }
        }

    }

    private fun adjustarDatosGuardarDatos() {
        var niebla: String
        var lluvia: String
        var agua: String

        if(!binding.cbNiebla.isChecked){
            niebla = ""
        }
        else{
            niebla = binding.sDensidad.selectedItem.toString()
        }

        if(!binding.cblluvia.isChecked){
            lluvia = ""
        }
        else{
            lluvia = binding.tvLluvia.text.toString()
        }

        if(!binding.cbAgua.isChecked){
            agua = ""
        }
        else{
            agua = binding.tvAgua.text.toString()
        }

        guardarRegistro(niebla, lluvia, agua)
    }

    private fun guardarRegistro(niebla: String, lluvia: String, agua: String ) {
        try {
            (activity as MainActivity).miViewModel.insertarRegistro(Registro(
                fecha = binding.tvCalendario.text.toString(),
                niebla = niebla,
                lluvia = lluvia,
                agua = agua,
                incidencias = binding.etComentario.text.toString(),
                m3 = binding.etM3.text.toString().toDouble(),
                litros = binding.etLitros.text.toString().toDouble(),
                ml = binding.etMl.text.toString().toDouble(),
                idUsuario = usuarioid().toString()
            ))
            Toast.makeText(activity,"Datos se han insertado correctamente", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
        }
        catch (e:Exception){
            Toast.makeText(activity as MainActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun usuarioid(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("credensiales", Context.MODE_PRIVATE)
        // Recupera los datos de inicio de sesión
        val id = sharedPreferences.getString("id", "")
        return id
    }


    fun borrar(miRegistro:Registro){
        try {
            (activity as MainActivity).miViewModel.borrarRegistro(miRegistro)
            Toast.makeText(activity,"Registro se ha eliminado correctamente", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
        }
        catch (e:Exception){
            Toast.makeText(activity as MainActivity,e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun validarDatos(): Boolean {
        if(binding.tvCalendario.text.toString() == "" ) {
            showErrorTextView(binding.tvCalendario, "Campo fecha es obligatorio.")
            return false
        }

        if(binding.cbNiebla.isChecked && binding.sFranjas.selectedItem == null){
            showErrorSpinner(binding.sFranjas, "Campo fecha horaria es obligatorio si havia niebla.")
            return false
        }

        if(binding.cblluvia.isChecked && binding.tvLluvia.text.toString() == ""){
            showErrorTextView(binding.tvCalendario, "Duración de lluvia es obligatoria.")
            return false
        }

        if(binding.cbAgua.isChecked && binding.tvAgua.text.toString() == ""){
            showErrorTextView(binding.tvCalendario, "Duración de cortes de agua es obligatoria.")
            return false
        }

        return true
    }


    private fun showErrorTextView(tv: TextView, e: String) {
        tv.text = e
        tv.setTextColor(Color.RED)
        tv.requestFocus()
    }

    private fun showErrorSpinner(sRegion: Spinner, s: String) {
        val errorText = sRegion.selectedView as TextView
        errorText.setError(s)
        errorText.requestFocus()
    }

}