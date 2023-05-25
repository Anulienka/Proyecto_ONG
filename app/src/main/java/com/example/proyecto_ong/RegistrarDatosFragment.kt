package com.example.proyecto_ong

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.navigation.fragment.findNavController
import java.util.*
import com.example.proyecto_ong.databinding.FragmentRegistrarDatosBinding

class RegistrarDatosFragment : Fragment() {

    private var _binding: FragmentRegistrarDatosBinding? = null
    private val binding get() = _binding!!

    //objetos seleccionados
    lateinit var selectedObjects: List<Franja>

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
        binding.etHoraLluvia.isEnabled = false
        binding.etHoraAqua.isEnabled = false
        binding.tvFranjas.isEnabled = false

        //de menu bar
        binding.menuBarDatos.setNavigationIcon(R.drawable.ic_back)
        binding.menuBarDatos.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)
        }

        //SPINNER NIEBLA
        var densidadNiebla = arrayOf("Intensa", "Normal", "Poco intensa")
        val spinner = binding.sDensidad
        val arrayAdapterNiebla = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, densidadNiebla
        )
        arrayAdapterNiebla.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapterNiebla

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


        // FRANJAS
        (activity as MainActivity).miViewModel.mostrarFranjas()
        var franjas: List<Franja> = (activity as MainActivity).miViewModel.listaFranjas

        binding.tvFranjas.setOnClickListener {
            val objectNames = franjas.map { it.hora }.toTypedArray()
            val checkedItems = BooleanArray(franjas.size) { false }
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Franjas horarias")

            builder.setMultiChoiceItems(objectNames, checkedItems) { _, which, isChecked ->
                // Update the checked state of the object
                checkedItems[which] = isChecked
            }
            builder.setPositiveButton("OK") { _, _ ->
                selectedObjects = franjas.filterIndexed { index, _ -> checkedItems[index] }
                // Create a string representation of the selected items
                val selectedItemsText = selectedObjects.joinToString(", ") { it.hora }
                binding.tvFranjas.setText(selectedItemsText)
            }

            builder.setNegativeButton("CANCELAR", null)
            builder.show()
        }


        //NIEBLA
        binding.cbNiebla.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay niebla, se deshabilite spinner de densidad de niebla
            binding.sDensidad.isEnabled = binding.cbNiebla.isChecked
            binding.tvFranjas.isEnabled = binding.cbNiebla.isChecked
        }

        //LLUVIA
        binding.cblluvia.setOnCheckedChangeListener { _, isChecked ->
            //si no esta marcado que hay lluvia, se deshabilite editText de hora
            binding.etHoraLluvia.isEnabled = binding.cblluvia.isChecked
        }

        //AGUA
        binding.cbAgua.setOnCheckedChangeListener { buttonView, isChecked ->
            //si no esta marcado que hay cortes de agua, se deshabilite editText de hora
            binding.etHoraAqua.isEnabled = binding.cbAgua.isChecked
        }

        //REGISTRAR DATOS
        binding.bRegistrarDatos.setOnClickListener {
            if (validarDatos()) {
                adjustarDatosGuardarDatos()
            }
        }
    }

    private fun adjustarDatosGuardarDatos() {
        var niebla: String = ""
        var lluvia: String = ""
        var agua: String = ""
        var comentarios: String = ""
        var metros3: Double = 0.0
        var litros: Double = 0.0
        var mililitros: Double = 0.0

        if (binding.cbNiebla.isChecked) {
            niebla = binding.sDensidad.selectedItem.toString()
        }

        if (binding.cblluvia.isChecked) {
            lluvia = binding.etHoraLluvia.text.toString()
        }

        if (binding.cbAgua.isChecked) {
            agua = binding.etHoraAqua.text.toString()
        }

        if (!binding.etComentario.text.toString().isEmpty()) {
            comentarios = binding.etComentario.text.toString()
        }

        if (!binding.etM3.text.toString().isEmpty()) {
            metros3 = binding.etM3.text.toString().toDouble()
        }

        if (!binding.etLitros.text.toString().isEmpty()) {
            litros = binding.etLitros.text.toString().toDouble()
        }

        if (!binding.etMl.text.toString().isEmpty()) {
            mililitros = binding.etMl.text.toString().toDouble()
        }

        guardarRegistro(niebla, lluvia, agua, comentarios, metros3, litros, mililitros)
    }

    private fun guardarRegistro(
        niebla: String,
        lluvia: String,
        agua: String,
        comentarios: String,
        metros3: Double,
        litros: Double,
        mililitros: Double
    ) {
        try {
            (activity as MainActivity).miViewModel.insertarRegistro(
                Registro(
                    fecha = binding.tvCalendario.text.toString(),
                    niebla = niebla,
                    lluvia = lluvia,
                    agua = agua,
                    incidencias = comentarios,
                    m3 = metros3,
                    litros = litros,
                    ml = mililitros,
                    idUsuario = usuarioid().toString()
                )
            )

            //si existe niebla, hay franjas horarias
            if (!binding.tvFranjas.text.toString().isEmpty()) {
                //busco ID de registro segun fecha y ID de usuario
                (activity as MainActivity).miViewModel.buscarRegistro(
                    binding.tvCalendario.text.toString(),
                    usuarioid().toString()
                )
                var miRegistro = (activity as MainActivity).miViewModel.miRegistroParaId
                if (miRegistro != null) {
                    //insetrar registro con franjas
                    for (elemento in selectedObjects) {
                        (activity as MainActivity).miViewModel.insertarRegistroFranja(miRegistro.id, elemento.id)
                    }
                }
            }

            Toast.makeText(activity, "Datos se han insertado correctamente", Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_registrarDatosFragment_to_SecondFragment)

        } catch (e: Exception) {
            Toast.makeText(activity as MainActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun usuarioid(): String? {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        // Recupera los datos de inicio de sesión
        val id = sharedPreferences.getString("id", null)
        return id
    }

    private fun validarDatos(): Boolean {
        if (binding.tvCalendario.text.toString() == "") {
            showErrorTextView(binding.tvCalendario, "Campo fecha es obligatorio.")
            return false
        }

        if (binding.cbNiebla.isChecked && binding.tvFranjas.text.toString().isEmpty()) {
            showErrorTextView(
                binding.tvFranjas,
                "Campo franja horaria es obligatorio si havía niebla."
            )
            return false
        }

        if (binding.cbNiebla.isChecked && binding.sDensidad.selectedItem == null) {
            showErrorSpinner(
                binding.sDensidad,
                "Campo densidad de niebla es obligatorio si havía niebla."
            )
            return false
        }

        if (binding.cblluvia.isChecked && binding.etHoraLluvia.text.toString().isEmpty()) {
            showError(binding.etHoraLluvia, "Duración de lluvia es obligatoria.")
            return false
        }

        if (binding.cbAgua.isChecked && binding.etHoraAqua.text.toString().isEmpty()) {
            showError(binding.etHoraAqua, "Duración de cortes de agua es obligatoria.")
            return false
        }

        return true
    }


    private fun showErrorTextView(input: TextView, e: String) {
        input.text = e
        input.setTextColor(Color.RED)
        input.requestFocus()
    }

    private fun showError(input: EditText, s: String) {
        input.setError(s)
        input.setTextColor(Color.RED)
        input.requestFocus()
    }

    private fun showErrorSpinner(sRegion: Spinner, s: String) {
        val errorText = sRegion.selectedView as TextView
        errorText.setError(s)
        errorText.requestFocus()
    }

}