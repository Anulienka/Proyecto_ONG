package com.example.proyecto_ong

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

//Para conseguir que los datos se mantengan usamos un viewModel.
// Su principal ventaja es que almacena en caché el estado y lo conserva durante los cambios de configuración.
//definir los datos que queremos que persistan y la lógica de negocio de estas.
//hay irán los datos que vamos a usar en nuestra aplicación, en este caso todos los métodos de consultas a la BBDD.
class CondicionMeteorologicaViewModel(private val miRepositorio: Repositorio): ViewModel() {

    //LiveData, que es un dato observable
    lateinit var listaUsuarios: LiveData<List<Usuario>>
    lateinit var miUsuario: LiveData<Usuario>
    lateinit var listaFranjas: LiveData<List<Franja>>
    lateinit var miFranja: LiveData<Franja>
    val listaRegistros: LiveData<List<CondicionMeteorologica>> = miRepositorio.listaRegistros.asLiveData()
    lateinit var miRegistro: LiveData<CondicionMeteorologica>
    val listaRegistrosObjetos: LiveData<List<CondicionMeteorologicaClase>> = miRepositorio.listaRegistrosClase.asLiveData()
    val listaRegistrosObjetosUsuario: LiveData<List<CondicionMeteorologicaClase>> = miRepositorio.listaRegistrosClaseUsuario.asLiveData()

    fun insertarUsuario(miUsuario: Usuario) = viewModelScope.launch {
        miRegistro.insertarUsuario(miUsuario)
    }

    fun mostrarTodosUsuarios()= viewModelScope.launch {
        listaUsuarios = miRepositorio.mostrarTodosUsuarios().asLiveData()
    }

    fun buscarUsuarioPorId(id:Int) = viewModelScope.launch {
        miUsuario = miRepositorio.buscarUsuarioPorID(id).asLiveData()
    }

    fun buscarUsuario(nombre: String, contrasena: String) = viewModelScope.launch {
        miUsuario = miRepositorio.buscarUsuario(nombre, contrasena).asLiveData()
    }

    fun insertarFranja(miFranja: Franja) =viewModelScope.launch{
        miRepositorio.insertarFranja(miFranja)
    }

    fun mostrarTodasFranjas() =viewModelScope.launch {
        listaFranjas= miRepositorio.mostrarTodasFranjas().asLiveData()
    }

    fun buscarFranjaPorId(id:Int) =viewModelScope.launch{
        miFranja=miRepositorio.buscarFranjaPorId(id).asLiveData()
    }

    fun insertarRegistro(miRegistro: CondicionMeteorologica) =viewModelScope.launch{
        miRepositorio.insertarRegistro(miRegistro)
    }

    fun borrarRegistro(miRegistro: CondicionMeteorologica) =viewModelScope.launch{
        miRepositorio.borrarRegistro(miRegistro)
    }

    fun modificar(miRegistro: CondicionMeteorologica) =viewModelScope.launch{
        miRepositorio.modificarRegistro(miRegistro)
    }

    fun buscarRegistroPorId(id:Int) =viewModelScope.launch{
        miRegistro=miRepositorio.buscarRegistroPorId(id).asLiveData()
    }
}
class CondicionMeteorologicaViewModelFactory(private val miRepositorio: Repositorio):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CondicionMeteorologicaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return  CondicionMeteorologicaViewModel(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}

