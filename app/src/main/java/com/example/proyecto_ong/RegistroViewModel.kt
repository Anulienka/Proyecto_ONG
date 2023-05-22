package com.example.proyecto_ong

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

//Para conseguir que los datos se mantengan usamos un viewModel.
//Su principal ventaja es que almacena en caché el estado y lo conserva durante los cambios de configuración.
//definir los datos que queremos que persistan y la lógica de negocio de estas.
//hay irán los datos que vamos a usar en nuestra aplicación, en este caso todos los métodos de consultas a la BBDD.
class RegistroViewModel(private val miRepositorio: Repositorio): ViewModel() {

    //LiveData, que es un dato observable
    lateinit var miRegistro: LiveData<Registro>
     var miUsuario: Usuario? = null
    var listaFranjas: List<Franja> = miRepositorio.listaFranjas
    lateinit var listaRegistrosUsuario: LiveData<List<Registro>>


    //**** USUARIO ****
    fun insertarUsuario(miUsuario:Usuario){
        miRepositorio.insertarUsuario(miUsuario)
    }

    //aqui da error en registrar usuario
    fun buscarUsuario(nombre: String){
        miUsuario = miRepositorio.buscarUsuario(nombre)
    }

//    fun buscarUsuario(nombre: String) =viewModelScope.launch{
//        miUsuario = miRepositorio.buscarUsuario(nombre)
//    }


    //**** REGISTROS ****

    fun mostrarRegistrosUsuario(id: String?) =viewModelScope.launch {
        listaRegistrosUsuario= miRepositorio.mostrarRegistrosUsuario(id)
    }

    fun insertarRegistro(miRegistro: Registro){
        miRepositorio.insertarRegistro(miRegistro)
    }

    fun borrarRegistro(miRegistro: Registro) =viewModelScope.launch{
        miRepositorio.borrarRegistro(miRegistro)
    }

    fun buscarRegistroPorId(id:String) =viewModelScope.launch{
        miRegistro=miRepositorio.buscarRegistroPorId(id)
    }

    //**** FRANJAS ****
   fun mostrarFranjas(){
        listaFranjas= miRepositorio.mostrarFranjas()
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


