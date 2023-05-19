package com.example.proyecto_ong


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SupportSQLiteCompat.Api16Impl.cancel
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


//El DAO será la clase donde mapeamos todas nuestras querys a funciones.
class BBDDParse {

    fun insertarUsuario(miUsuario: Usuario) {
        val registroUsuario = ParseObject("Usuarios")
        registroUsuario.put("nombre", miUsuario.nombre)
        registroUsuario.put("contrasena", miUsuario.contrasena)
        registroUsuario.put("region", miUsuario.region)
        registroUsuario.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    //Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show()
                    throw Exception(message)
                }
            }
        }
    }

    fun buscarUsuario(nombre: String): LiveData<Usuario> {
        val miUSuario = MutableLiveData<Usuario>()
        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        query.whereEqualTo("nombre", nombre)

        query.getFirstInBackground { objeto, error ->
            if (error == null) {
                // Itera sobre los objetos obtenidos de Parse y los envía uno a uno al Flow
                val usuario = Usuario(
                    // Parse los datos del objeto y crea un objeto Usuario
                    objeto.objectId,
                    objeto.getString("nombre") ?: "",
                    objeto.getString("contrasena") ?: "",
                    objeto.getString("region") ?: ""
                )

                miUSuario.postValue(usuario)
            } else {
               //throw Exception(error)
            }
        }
        return miUSuario
    }

    fun mostrarRegistrosUsuario(): LiveData<List<Registro>> {
        val misRegistros: MutableLiveData<List<Registro>> = MutableLiveData()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("objectId")
        query.findInBackground { objects, e ->
            if (e == null) {
                // Adding objects into the Array
                val registro = objects.map { i ->
                    Registro(
                        i.objectId,
                        i.getString("fecha") ?: "",
                        i.getString("niebla") ?: "",
                        i.getString("lluvia") ?: "",
                        i.getString("agua") ?: "",
                        i.getString("incidencias") ?: "",
                        i.getDouble("m3"),
                        i.getDouble("litros"),
                        i.getDouble("ml"),
                        i.getString("idUsuario") ?: "")
                }
                misRegistros.postValue(registro)
            }
        }
        return misRegistros
    }

    fun insertarRegistro(miRegistro: Registro) {
        val registroUsuario = ParseObject("Registros")
        registroUsuario.put("fecha", miRegistro.fecha)
        registroUsuario.put("niebla", miRegistro.niebla)
        registroUsuario.put("lluvia", miRegistro.lluvia)
        registroUsuario.put("agua", miRegistro.agua)
        registroUsuario.put("incidencias", miRegistro.incidencias)
        registroUsuario.put("m3", miRegistro.m3)
        registroUsuario.put("litros", miRegistro.litros)
        registroUsuario.put("ml", miRegistro.ml)
        registroUsuario.put("idUsuario", miRegistro.idUsuario)
        registroUsuario.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    throw Exception(message)
                }
            }
        }
    }

    fun borrarRegistro(miRegistro: Registro) {
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        query.whereEqualTo("objectId", miRegistro.id)
        query.getFirstInBackground { parseObject, parseException ->
            if (parseException == null) {
                parseObject.deleteInBackground {
                    if (it != null) {
                        throw Exception(it.localizedMessage)
                    }
                }
            } else {
                throw Exception(parseException.localizedMessage)
            }

        }
    }

    fun mostrarFranjas(): LiveData<List<Franja>> {
        val misFranjas: MutableLiveData<List<Franja>> = MutableLiveData()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("Franjas")
        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("objectId")
        query.findInBackground { objects, e ->
            if (e == null) {
                val franja = objects.map { i ->
                    Franja(
                        i.objectId,
                        i.getString("hora") ?: ""
                    )
                }
                misFranjas.postValue(franja)
            }
        }
        return misFranjas
    }

}
