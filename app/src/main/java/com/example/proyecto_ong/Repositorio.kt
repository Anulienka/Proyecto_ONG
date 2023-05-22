package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData


//Repositorio va a ser una clase que se va a encargar de manejar las diferentes BBDD que use nuestra aplicación.
// Por ejemplo, podríamos tener una BBDD en local y otra en la nube.
class Repositorio(val miBBDD: BBDDParse) {

    //val listaRegistrosUsuario = miBBDD.mostrarRegistrosUsuario(id)
    val listaFranjas =miBBDD.mostrarFranjas()

    //USUARIO
    fun insertarUsuario(miUsuario:Usuario){
        miBBDD.insertarUsuario(miUsuario)
    }


    fun buscarUsuario(nombre: String): Usuario? {
        return miBBDD.buscarUsuario(nombre)
    }

//    fun buscarUsuario(nombre: String): LiveData<Usuario>{
//        return miBBDD.buscarUsuario(nombre)
//    }

    fun insertarRegistro(miRegistro: Registro){
        miBBDD.insertarRegistro(miRegistro)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun mostrarRegistrosUsuario(id: String?):LiveData<List<Registro>>{
        return miBBDD.mostrarRegistrosUsuario(id)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrarRegistro(miRegistro: Registro){
        miBBDD.borrarRegistro(miRegistro)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun buscarRegistroPorId(id:String):LiveData<Registro>{
        return miBBDD.buscarRegistroPorId(id)
    }


    fun mostrarFranjas(): List<Franja> {
        return miBBDD.mostrarFranjas()
    }


}