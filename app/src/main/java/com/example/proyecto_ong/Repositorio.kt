package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*


//Repositorio va a ser una clase que se va a encargar de manejar las diferentes BBDD que use nuestra aplicación.
// Por ejemplo, podríamos tener una BBDD en local y otra en la nube.
class Repositorio(val miDAO: CondicionMeteorologicaDAO, id: Int) {
    val listaRegistros: Flow<List<CondicionMeteorologica>> =miDAO.mostrarTodo()
    val listaRegistrosClase: Flow<List<CondicionMeteorologicaClase>> =miDAO.mostrarTodosRegistros()
    val listaRegistrosClaseUsuario: Flow<List<CondicionMeteorologicaClase>> =miDAO.mostrarRegistrosUsuario(id)



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarUsuario(miUsuario: Usuario){
        miDAO.insertarUsuario(miUsuario)
    }

    fun mostrarTodosUsuarios(): Flow<List<Usuario>>{
        return miDAO.mostrarTodosUsuarios()
    }

    fun buscarUsuarioPorId(id:Int):Flow<Usuario>{
        return miDAO.buscarUsuarioPorId(id)
    }

    fun buscarUsuario(nombre: String, contrasena: String): Flow<Usuario> {
        return miDAO.buscarUsuario(nombre, contrasena)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarFranja(miFranja: Franja){
        miDAO.insertarFranja(miFranja)
    }

    fun mostrarTodasFranjas(): Flow<List<Franja>>{
        return miDAO.mostrarTodasFranjas()
    }

    fun buscarFranjaPorId(id:Int):Flow<Franja>{
        return miDAO.buscarFranjaPorId(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarRegistro(miRegistro: CondicionMeteorologica){
        miDAO.insertar(miRegistro)
    }

    fun buscarRegistroPorId(id:Int):Flow<CondicionMeteorologica>{
        return miDAO.buscarRegistroPorId(id)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrarRegistro(miRegistro: CondicionMeteorologica){
        miDAO.borrar(miRegistro)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun modificarRegistro(miRegistro: CondicionMeteorologica){
        miDAO.modificar(miRegistro)
    }
}