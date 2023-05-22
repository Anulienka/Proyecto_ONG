package com.example.proyecto_ong


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parse.ParseException
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

    fun buscarUsuario(nombre: String): Usuario? {
        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        query.whereEqualTo("nombre", nombre)

        try {
            val objeto = query.getFirst()
            return Usuario(
                objeto.objectId,
                objeto.getString("nombre") ?: "",
                objeto.getString("contrasena") ?: "",
                objeto.getString("region") ?: ""
            )
        } catch (error: ParseException) {
            // Manejar el error de Parse según sea necesario
            return null
        }
    }

//    fun buscarUsuario(nombre: String): LiveData<Usuario> {
//        val miUSuario = MutableLiveData<Usuario>()
//        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
//        query.whereEqualTo("nombre", nombre)
//
//        query.getFirstInBackground { objeto, error ->
//            if (error == null) {
//                // Itera sobre los objetos obtenidos de Parse y los envía uno a uno al Flow
//                val usuario = Usuario(
//                    // Parse los datos del objeto y crea un objeto Usuario
//                    objeto.objectId,
//                    objeto.getString("nombre") ?: "",
//                    objeto.getString("contrasena") ?: "",
//                    objeto.getString("region") ?: ""
//                )
//
//                miUSuario.postValue(usuario)
//            } else {
//               //throw Exception(error)
//            }
//        }
//        return miUSuario
//    }

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

    fun insertarRegistroFranja(idRegistro: String, idFranja: String) {
        val registroUsuario = ParseObject("RegistroFranja")
        registroUsuario.put("idRegistro", idRegistro)
        registroUsuario.put("idFranja", idFranja)
        registroUsuario.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    //Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show()
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

    fun buscarRegistro(fecha: String, id: String): Registro? {
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        query.whereEqualTo("fecha", fecha).whereEqualTo("idUsuario", id)

        try {
            val objeto = query.getFirst()
            return Registro(
                objeto.objectId,
                objeto.getString("fecha") ?: "",
                objeto.getString("niebla") ?: "",
                objeto.getString("lluvia") ?: "",
                objeto.getString("agua") ?: "",
                objeto.getString("incidencias") ?: "",
                objeto.getDouble("m3"),
                objeto.getDouble("litros"),
                objeto.getDouble("ml"),
                objeto.getString("idUsuario") ?: "")

        } catch (error: ParseException) {
            // Manejar el error de Parse según sea necesario
            return null
        }
    }

    fun buscarRegistroPorId(id: String): LiveData<Registro> {
        val miRegistro = MutableLiveData<Registro>()
        val query = ParseQuery.getQuery<ParseObject>("Registros")
        // Query Parameters
        query.whereEqualTo("objectId", id)
        // How we need retrive exactly one result we can use the getFirstInBackground method
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val registro = Registro(
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

                miRegistro.postValue(registro)
            } else {
                throw Exception(parseException)

            }
        }
        return miRegistro
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
