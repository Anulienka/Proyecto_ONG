package com.example.proyecto_ong


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

    //FUNCIONA
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

    fun buscarUsuario(nombre: String): Flow<Usuario> = callbackFlow {

        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        query.whereEqualTo("nombre", nombre)

        query.findInBackground { objetos, error ->
            if (error == null) {
                // Itera sobre los objetos obtenidos de Parse y los envía uno a uno al Flow
                for (objeto in objetos) {
                    // Parse los datos del objeto y crea un objeto Usuario
                    val id = objeto.objectId
                    val nombre = objeto.getString("nombre")
                    val contrasena = objeto.getString("contrasena")
                    val region = objeto.getString("region")

                    val usuario = Usuario(id, nombre.toString(), contrasena.toString(),
                        region.toString()
                    )

                    // Envía el objeto Usuario al Flow
                    trySend(usuario).isSuccess
                }

                // Cierra el canal de flujo después de enviar todos los objetos
                close()
            } else {
                throw Exception(error)
            }
        }
        awaitClose()
    }

        /* fun mostrarRegistrosUsuario(): LiveData<List<Registro>> {
        val misRegistros: MutableLiveData<List<Registro>> = MutableLiveData()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("objectId")
        query.findInBackground { objects, e ->
            if (e == null) {
                // Adding objects into the Array
                val peliculas = objects.map { i ->
                    Registro(i.objectId,
                        i.getString("titulo") ?: "",
                        i.getString("genero") ?: "",
                        i.getInt("estreno"))
                }
                misRegistros.postValue(peliculas)
            }
        }
        return misRegistros
    }*/

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
                    //Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show()
                    throw Exception(message)
                }
            }
        }
    }

    fun borrarRegistro(miRegistro: Registro){
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        query.whereEqualTo("objectId", miRegistro.id)
        query.getFirstInBackground{ parseObject, parseException ->
            if (parseException == null) {
                parseObject.deleteInBackground {
                    if (it != null) {
                        throw Exception(it.localizedMessage)
                    }
                }
            }
            else {
                throw Exception (parseException.localizedMessage)
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
