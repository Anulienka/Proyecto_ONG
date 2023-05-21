package com.example.proyecto_ong


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parse.ParseObject
import com.parse.ParseQuery


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

    fun mostrarRegistrosUsuario(idUsuario: String?): LiveData<List<Registro>> {
        val misRegistros: MutableLiveData<List<Registro>> = MutableLiveData()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        query.whereEqualTo("idUsuario", idUsuario)
        query.orderByAscending("objectId")
        query.findInBackground { objects, e ->
            if (e == null) {
                // Adding objects into the Array
                val registro = objects.map { i ->
                    Registro(
                        i.objectId,
                        i.getString("region") ?: "",
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
        registroUsuario.put("region", miRegistro.region)
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

    fun mostrarFranjas(): List<Franja> {
        val query = ParseQuery.getQuery<ParseObject>("Franjas")
        query.orderByAscending("objectId")
        val objects = query.find()
        val misFranjas = objects.map { i ->
            Franja(
                i.objectId,
                i.getString("hora") ?: ""
            )
        }
        return misFranjas
    }

}
