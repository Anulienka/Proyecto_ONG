package com.example.proyecto_ong

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CondicionMeteorologicaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarGenero(miGenero:Genero)

    @Query ("SELECT * FROM tabla_generos ORDER BY id ASC")
    fun mostrarTodosGeneros() : Flow<List<Genero>>

    @Query("SELECT * FROM tabla_generos where id like :id")
    fun buscarGeneroPorId(id:Int):Flow<Genero>

    @Query("SELECT p.id, p.titulo, g.genero, p.estreno FROM tabla_generos as g, tabla_peliculas as p where p.genero like g.id")
    fun mostrarTodasPeliculas():Flow<List<PeliculaClase>>

    @Query ("SELECT * FROM tabla_peliculas ORDER BY id ASC")
    fun mostrarTodo() : Flow<List<Pelicula>>

    @Query("SELECT * FROM tabla_peliculas where id like :id")
    fun buscarPorId(id:Int):Flow<Pelicula>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miPelicula:Pelicula)

    @Delete
    suspend fun borrar(miPelicula: Pelicula)

    @Update
    suspend fun modificar(miPelicula: Pelicula)
}