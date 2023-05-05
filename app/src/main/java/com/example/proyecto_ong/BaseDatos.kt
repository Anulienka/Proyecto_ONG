package com.example.proyecto_ong

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(CondicionMeteorologica::class,Franja::class, Usuario::class, RegistroFranjaCrossRef::class), version = 1, exportSchema = false)
abstract class BaseDatos:RoomDatabase() {
    abstract fun miDAO():CondicionMeteorologicaDAO

    companion object{
        @Volatile
        private var INSTANCE:BaseDatos?=null

        fun getDatabase(context:Context):BaseDatos{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "my_app_database"
                )
                    .addCallback(roomCallback)
                    .build().also { INSTANCE = it }
            }
        }

        // Callback que se ejecuta cuando se crea la base de datos
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                    // Insertar los datos iniciales
                    val franjasIniciales = listOf(
                        Franja(nombreFranja = "primera"),
                        Franja(nombreFranja = "segunada"),
                        Franja(nombreFranja = "tercera"),
                        Franja(nombreFranja = "cuarta"),
                        Franja(nombreFranja = "quinta")
                    )
                    // Inserta las franjas iniciales en la base de datos
                    val viewModelScope = CoroutineScope(Dispatchers.IO)
                    viewModelScope.launch {
                        // Realizar la operación en segundo plano (p. ej., poblar la base de datos)
                        franjasIniciales.forEach { franja -> INSTANCE?.miDAO()?.insertarFranja(franja) }
                    }
            }
        }
    }
}