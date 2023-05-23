package com.example.proyecto_ong

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

class RegistroViewModel(private val miRepositorio: Repositorio): ViewModel() {

    //LiveData, que es un dato observable
    lateinit var miRegistro: LiveData<Registro>
     var miUsuario: Usuario? = null
    var miRegistroParaId: Registro? = null
    var miFranjaRegistro: Franja? = null
    var listaFranjas: List<Franja> = miRepositorio.listaFranjas
    lateinit var listaFranjasRegistro: List<RegistroFranja>
    lateinit var listaRegistrosUsuario: LiveData<List<Registro>>


    //**** USUARIO ****
    fun insertarUsuario(miUsuario:Usuario){
        miRepositorio.insertarUsuario(miUsuario)
    }

    //aqui da error en registrar usuario
    fun buscarUsuario(nombre: String){
        miUsuario = miRepositorio.buscarUsuario(nombre)
    }

    //**** REGISTROS ****

    fun mostrarRegistrosUsuario(id: String?) =viewModelScope.launch {
        listaRegistrosUsuario= miRepositorio.mostrarRegistrosUsuario(id)
    }

    fun insertarRegistro(miRegistro: Registro){
        miRepositorio.insertarRegistro(miRegistro)
    }

    fun insertarRegistroFranja(idRegistro: String, idFranja:String){
        miRepositorio.insertarRegistroFranja(idRegistro, idFranja)
    }

    fun borrarRegistro(miRegistro: Registro) =viewModelScope.launch{
        miRepositorio.borrarRegistro(miRegistro)
    }

    fun buscarRegistroPorId(id:String) =viewModelScope.launch{
        miRegistro=miRepositorio.buscarRegistroPorId(id)
    }

    fun buscarRegistro(fecha: String, id:String){
        miRegistroParaId = miRepositorio.buscarRegistro(fecha, id)
    }


    //**** FRANJAS ****
   fun mostrarFranjas(){
        listaFranjas= miRepositorio.mostrarFranjas()
    }

    fun mostrarFranjasRegistro(id: String){
        listaFranjasRegistro= miRepositorio.mostrarFranjasRegistro(id)
    }

    fun buscarFranjaPorId(id: String){
        miFranjaRegistro =  miRepositorio.buscarFranjaPorId(id)
    }

    class RegistroViewModelFactory(private val miRepositorio: Repositorio) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegistroViewModel(miRepositorio) as T
            }
            throw IllegalArgumentException("ViewModel class desconocida")
        }
    }
}


