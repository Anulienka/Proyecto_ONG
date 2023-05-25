package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class Repositorio(val miBBDD: BBDDParse) {

    val listaFranjas =miBBDD.mostrarFranjas()

    //USUARIO
    fun insertarUsuario(miUsuario:Usuario){
        miBBDD.insertarUsuario(miUsuario)
    }

    fun buscarUsuario(nombre: String): Usuario? {
        return miBBDD.buscarUsuario(nombre)
    }


    //REGISTRO
    fun insertarRegistro(miRegistro: Registro){
        miBBDD.insertarRegistro(miRegistro)
    }

    fun insertarRegistroFranja(idRegistro: String, idFranja:String){
        miBBDD.insertarRegistroFranja(idRegistro, idFranja)
    }

    fun buscarRegistro(fecha: String, id:String): Registro? {
        return miBBDD.buscarRegistro(fecha, id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun mostrarRegistrosUsuario(id: String?):LiveData<List<Registro>>{
        return miBBDD.mostrarRegistrosUsuario(id)
    }


    fun buscarRegistroPorId(id:String): LiveData<Registro>{
        return miBBDD.buscarRegistroPorId(id)
    }

    fun borrarRegistro(miRegistro: Registro){
        miBBDD.borrarRegistro(miRegistro)
    }


//FRANJAS HORARIAS
    fun buscarFranjaPorId(id: String): Franja? {
        return miBBDD.buscarFranjaPorId(id)
    }

    fun mostrarFranjas(): List<Franja> {
        return miBBDD.mostrarFranjas()
    }

    fun mostrarFranjasRegistro(id: String): List<RegistroFranja> {
        return miBBDD.mostrarFranjasRegistro(id)
    }


}