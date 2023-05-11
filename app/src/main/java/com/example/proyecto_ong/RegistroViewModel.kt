package com.example.proyecto_ong

import androidx.lifecycle.*
import java.util.*

//Para conseguir que los datos se mantengan usamos un viewModel.
//Su principal ventaja es que almacena en caché el estado y lo conserva durante los cambios de configuración.
//definir los datos que queremos que persistan y la lógica de negocio de estas.
//hay irán los datos que vamos a usar en nuestra aplicación, en este caso todos los métodos de consultas a la BBDD.
class RegistroViewModel(private val miRepositorio: Repositorio): ViewModel() {

    //LiveData, que es un dato observable
    lateinit var miRegistro: LiveData<Registro>
    lateinit var miUsuario: Usuario
    lateinit var listaFranjas: List<Franja>

    //val listaRegistros: LiveData<List<Registro>> = miRepositorio.listaRegistros.asLiveData()
    //val listaRegistrosObjetos: LiveData<List<Registro>> = miRepositorio.listaRegistrosClase.asLiveData()
    val listaRegistrosObjetosUsuario: LiveData<List<Registro>> = miRepositorio.listaRegistrosClaseUsuario.asLiveData()


    //---------->FIREBASE
    //**** USUARIO ****
    suspend fun insertarUsuario(nombre: String, contrasena: String, region: String) {
        miRepositorio.insertarUsuario(nombre, contrasena, region)
    }

    suspend fun buscarUsuario(nombre: String, contrasena: String) {
        miUsuario = miRepositorio.buscarUsuario(nombre, contrasena)!!
    }

    fun mostrarTodasFranjas() {
        listaFranjas = miRepositorio.mostrarTodasFranjas()
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
