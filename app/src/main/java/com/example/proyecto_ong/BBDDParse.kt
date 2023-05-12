package com.example.proyecto_ong


import com.parse.ParseObject
import com.parse.ParseQuery


//El DAO será la clase donde mapeamos todas nuestras querys a funciones.
class BBDDParse {

    fun insertarUsuario(nombre: String, contrasena: String, region: String) {
        val registroUsuario = ParseObject("Usuarios")
        registroUsuario.put("nombre", nombre)
        registroUsuario.put("contrasena", contrasena)
        registroUsuario.put("region", region)
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
        var miUsuario : Usuario? = null
        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        // Query Parameters
        query.whereEqualTo("nombre", nombre)
        // How we need retrive exactly one result we can use the getFirstInBackground method
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val usuario = Usuario(
                    i.objectId,
                    i.getString("nombre") ?: "",
                    i.getString("contrasena") ?: "",
                    i.getString("region") ?: "")
                miUsuario = usuario
            } else {
                miUsuario = null
                throw Exception(parseException)
            }
        }
        return miUsuario
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

    fun mostrarFranjas(): List<Franja> {
        val misFranjas: MutableList<Franja> = mutableListOf()
        // Configure Query
        val query = ParseQuery.getQuery<ParseObject>("Franjas")
        // Sorts the results in ascending order by the itemName field
        query.orderByAscending("objectId")
        query.findInBackground { objects, e ->
            if (e == null) {
                for(i in objects){
                    Franja(i.objectId, i.getString("hora") ?: "").let {
                        misFranjas.add(it)
                    }
                }
            }
        }
        return misFranjas
    }





}