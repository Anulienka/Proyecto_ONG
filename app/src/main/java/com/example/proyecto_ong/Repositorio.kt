package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


//Repositorio va a ser una clase que se va a encargar de manejar las diferentes BBDD que use nuestra aplicación.
// Por ejemplo, podríamos tener una BBDD en local y otra en la nube.
class Repositorio(val miBBDD: BBDDParse) {

    val listaRegistros = miBBDD.mostrarRegistrosUsuario()
    val listaFranjas =miBBDD.mostrarFranjas()

    //USUARIO
    fun insertarUsuario(miUsuario:Usuario){
        miBBDD.insertarUsuario(miUsuario)
    }

    fun buscarUsuario(nombre: String): LiveData<Usuario>{
        return miBBDD.buscarUsuario(nombre)
    }

    fun insertarRegistro(miRegistro: Registro){
        miBBDD.insertarRegistro(miRegistro)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun mostrarRegistrosUsuario():LiveData<List<Registro>>{
        return miBBDD.mostrarRegistrosUsuario()
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrarRegistro(miRegistro: Registro){
        miBBDD.borrarRegistro(miRegistro)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun mostrarFranjas(): LiveData<List<Franja>> {
        return miBBDD.mostrarFranjas()
    }

}