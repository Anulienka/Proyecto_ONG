package com.example.proyecto_ong

import androidx.annotation.WorkerThread


//Repositorio va a ser una clase que se va a encargar de manejar las diferentes BBDD que use nuestra aplicación.
// Por ejemplo, podríamos tener una BBDD en local y otra en la nube.
class Repositorio(val miDAO: BBDDParse) {

    //val listaRegistros = miDAO.mostrarRegistrosUsuario()

    //USUARIO
    fun insertarUsuario(nombre:String, contrasena: String, region:String){
        miDAO.insertarUsuario(nombre, contrasena, region)
    }

    fun buscarUsuario(nombre: String): Usuario? {
        return miDAO.buscarUsuario(nombre)
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun mostrarRegistrosUsuario():LiveData<List<Registro>>{
//        return miDAO.mostrarRegistrosUsuario()
//    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun mostrarFranjas():List<Franja>{
        return miDAO.mostrarFranjas()
    }

}