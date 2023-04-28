package com.example.proyecto_ong

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class Repositorio (val miDAO: CondicionMeteorologicaDAO) {
    val listaRegistros: Flow<List<CondicionMeteorologica>> =miDAO.mostrarTodo()
    val listaRegistrosClase: Flow<List<CondicionMeteorologicaClase>> =miDAO.mostrarTodosRegistros()

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