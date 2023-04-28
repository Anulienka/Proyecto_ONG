package com.example.proyecto_ong

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

class RegistroViewModel(private val miRepositorio: Repositorio): ViewModel() {
    val listaRegistros: LiveData<List<CondicionMeteorologica>> = miRepositorio.listaRegistros.asLiveData()
    lateinit var miRegistro: LiveData<CondicionMeteorologica>
    lateinit var listaFranjas: LiveData<List<Franja>>
    lateinit var miFranja: LiveData<Franja>
    val listaRegistrosObjetos: LiveData<List<CondicionMeteorologicaClase>> = miRepositorio.listaPeliculas2.asLiveData()


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
class RegistroViewModelFactory(private val miRepositorio: Repositorio):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return  RegistroViewModel(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}

