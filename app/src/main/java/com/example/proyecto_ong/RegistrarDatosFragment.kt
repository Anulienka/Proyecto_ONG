package com.example.proyecto_ong

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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

    //USUARIO
    var usuarioID = (activity as MainActivity).idUsuarioApp

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

        //SPINNER NIEBLA
        var densidadNiebla = arrayOf("Intensa", "Normal", "Poco intensa")
        val spinner = binding.sDensidad
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, densidadNiebla)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        //formato de la FECHA de registro
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDateandTime: String = sdf.format(Date())

        //default HORA y FECHA
        binding.tvCalendario.setText(currentDateandTime)
        val defaultHora = String.format("%02d:%02d", "00", "00")
        binding.tvHora.setText(defaultHora)

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


        // Agrega el listener para el text view de fecha de registracion de datos
        binding.tvHora.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.tvHora.text = selectedTime
            }, hour, minute, false)
            timePickerDialog.show()
        }


        findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)

    }
}