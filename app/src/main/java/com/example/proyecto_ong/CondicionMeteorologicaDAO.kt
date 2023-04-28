package com.example.proyecto_ong

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CondicionMeteorologicaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarFranja(miFranja:Franja)

    @Query ("SELECT * FROM tabla_franjas ORDER BY id ASC")
    fun mostrarTodasFranjas() : Flow<List<Franja>>

    @Query("SELECT * FROM tabla_franjas where id like :id")
    fun buscarFranjaPorId(id:Int):Flow<Franja>

    //HAY QUE MIRAR
    @Query("SELECT r.id, r.fecha, r.niebla, r.agua, r.lluvia, r.densidad, f.nombre FROM tabla_registros as r, tabla_franjas as f where f.nombre like g.id")
    fun mostrarTodosRegistros():Flow<List<CondicionMeteorologicaClase>>

    @Query ("SELECT * FROM tabla_registros ORDER BY id ASC")
    fun mostrarTodo() : Flow<List<CondicionMeteorologica>>

    @Query("SELECT * FROM tabla_registros where id like :id")
    fun buscarRegistroPorId(id:Int):Flow<CondicionMeteorologica>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miRegistro:CondicionMeteorologica)

    @Delete
    suspend fun borrar(miRegistro: CondicionMeteorologica)

    @Update
    suspend fun modificar(miRegistro: CondicionMeteorologica)
}