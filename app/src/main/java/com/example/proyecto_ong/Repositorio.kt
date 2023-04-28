package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class Repositorio (val miDAO: CondicionMeteorologicaDAO) {
    val listaPeliculas: Flow<List<CondicionMeteorologica>> =miDAO.mostrarTodo()
    val listaPeliculas2: Flow<List<CondicionMeteorologicaClase>> =miDAO.mostrarTodasPeliculas()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarGenero(miGenero: Genero){
        miDAO.insertarGenero(miGenero)
    }

    fun mostrarTodosGeneros(): Flow<List<Genero>>{
        return miDAO.mostrarTodosGeneros()
    }

    fun buscarGeneroPorId(id:Int):Flow<Genero>{
        return miDAO.buscarGeneroPorId(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertar(miPelicula: Pelicula){
        miDAO.insertar(miPelicula)
    }

    fun buscarPorId(id:Int):Flow<Pelicula>{
        return miDAO.buscarPorId(id)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrar(miPelicula: Pelicula){
        miDAO.borrar(miPelicula)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun modificar(miPelicula: Pelicula){
        miDAO.modificar(miPelicula)
    }
}