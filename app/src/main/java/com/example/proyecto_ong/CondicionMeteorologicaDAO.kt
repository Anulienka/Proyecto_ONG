package com.example.proyecto_ong

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

//El DAO será la clase donde mapeemos todas nuestras querys a funciones.
//DAO ejecuta los query que tiene encima
@Dao
interface CondicionMeteorologicaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarUsuario(miUsuario: Usuario)

    @Query ("SELECT * FROM tabla_usuarios ORDER BY id ASC")
    fun mostrarTodosUsuarios() : Flow<List<Usuario>>

    @Query("SELECT * FROM tabla_usuarios where id like :id")
    fun buscarUsuarioPorId(id:Int):Flow<Usuario>

    @Query("SELECT * FROM tabla_usuarios where nombre like :nombre and contrasena like :contrasena")
    fun buscarUsuario(nombre: String, contrasena: String): Flow<Usuario>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarFranja(miFranja:Franja)

    @Query ("SELECT * FROM tabla_franjas ORDER BY id ASC")
    fun mostrarTodasFranjas() : Flow<List<Franja>>

    @Query("SELECT * FROM tabla_franjas where id like :id")
    fun buscarFranjaPorId(id:Int):Flow<Franja>

    //HAY QUE MIRAR
    @Query("SELECT r.id, r.fecha, r.niebla, r.agua, r.lluvia, r.densidad, idUsuario FROM tabla_registros as r")
    fun mostrarTodosRegistros():Flow<List<CondicionMeteorologicaClase>>


    @Query ("SELECT * FROM tabla_registros ORDER BY id ASC")
    fun mostrarTodo() : Flow<List<CondicionMeteorologica>>

    @Query("SELECT * FROM tabla_registros where idUsuario like :id")
    fun mostrarRegistrosUsuario(id:Int):Flow<List<CondicionMeteorologicaClase>>

    @Query("SELECT * FROM tabla_registros where id like :id")
    fun buscarRegistroPorId(id:Int):Flow<CondicionMeteorologica>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miRegistro:CondicionMeteorologica)

    @Delete
    suspend fun borrar(miRegistro: CondicionMeteorologica)

    @Update
    suspend fun modificar(miRegistro: CondicionMeteorologica)


    //CROSS TABLE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistroFranjaCrossRef(crossRef: RegistroFranjaCrossRef)
}