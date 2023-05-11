package com.example.proyecto_ong


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

//El DAO será la clase donde mapeamos todas nuestras querys a funciones.
class RegistroDAO {

    //acceso a BBDD
    // Inicializa Firebase
    private val db = FirebaseFirestore.getInstance()

    //USUARIO

    //***  FUNCIONA INSERTAR ***
    fun insertarUsuario(nombre:String, contrasena: String, region:String){

        val usuario = hashMapOf(
            "name" to nombre,
            "contrasena" to contrasena,
            "region" to region
        )

        // Agrega el objeto a la base de datos y obtiene su ID
        val usuariosRef = db.collection("usuarios")
        //anade nuevo document a collection usuarios
        val nuevoDoc = usuariosRef.document()
        //recoge su id
        val nuevoId = nuevoDoc.id
        //anade propiedades a documento creado
        nuevoDoc.set(usuario)

        // Actualiza el ID del objeto en la base de datos con el ID generado automáticamente
        nuevoDoc.update("id", nuevoId)
    }


    suspend fun buscarUsuario(nombre: String, contrasena: String): Usuario? {

        val useruarioRef = db.collection("usuarios")
        val query = useruarioRef.whereEqualTo("nombre", nombre).whereEqualTo("contrasena", contrasena).limit(1)

        val querySnapshot =  query.get().await()

        return if (!querySnapshot.isEmpty) {
            val documentSnapshot = querySnapshot.documents[0]
            val id = documentSnapshot.getString("id")
            val nombre = documentSnapshot.getString("nombre")
            val contrasena = documentSnapshot.getString("contrasena")
            val region = documentSnapshot.getString("region")
            val usuario = Usuario(id, nombre, contrasena, region)
            usuario
        } else {
            null
        }
    }


//FRANJAS
    fun mostrarFranjas(): List<Franja>{

        val franjas = mutableListOf<Franja>()
        val franjaRef = db.collection("franjas")

        franjaRef.get().addOnSuccessListener { documents ->

            // Recorremos los documentos obtenidos y creamos un objeto por cada uno
            for (document in documents) {
                val objeto = document.toObject(Franja::class.java)
                franjas.add(objeto)
            }

        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
        return franjas
    }


    //REGISTRO
    //-----------------------> esto hay que poner diferente, no hay todos los datos
    fun mostrarRegistrosUsuario(id: String):Flow<List<Registro>>{

        // Creamos el MutableStateFlow que contendrá la lista actualizada
        val registrosUsuario: MutableStateFlow<List<Registro>> = MutableStateFlow(emptyList())

    // Hacemos la consulta a Firestore para obtener los documentos que deseamos agregar a la lista
        val query = db.collection("registros")
        query.get().addOnSuccessListener { documents ->
            // Creamos una lista mutable para agregar los objetos extraídos
            val objetos = mutableListOf<Registro>()

            // Recorremos los documentos obtenidos y creamos un objeto por cada uno
            for (document in documents) {
                val objeto = document.toObject(Registro::class.java)
                objetos.add(objeto)
            }
            // Agregamos los objetos a la lista actualizada utilizando emit()
            registrosUsuario.value = objetos

        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
        return registrosUsuario
    }


}