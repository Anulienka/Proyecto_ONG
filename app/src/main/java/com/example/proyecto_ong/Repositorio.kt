package com.example.proyecto_ong

import kotlinx.coroutines.flow.Flow


//Repositorio va a ser una clase que se va a encargar de manejar las diferentes BBDD que use nuestra aplicación.
// Por ejemplo, podríamos tener una BBDD en local y otra en la nube.
class Repositorio(id: String) {

//    val listaRegistros: Flow<List<Registro>> =miDAO.mostrarTodo()
//    val listaRegistrosClase: Flow<List<Registro>> =miDAO.mostrarTodosRegistros()

    val miDAO = RegistroDAO()
   val listaRegistrosClaseUsuario: Flow<List<Registro>> =miDAO.mostrarRegistrosUsuario(id)


    //USUARIO

    fun insertarUsuario(nombre:String, contrasena: String, region:String){
        miDAO.insertarUsuario(nombre, contrasena, region)
    }

    suspend fun buscarUsuario(nombre: String, contrasena: String): Usuario? {
        return miDAO.buscarUsuario(nombre, contrasena)
    }

    fun mostrarTodasFranjas(): List<Franja>{
        return miDAO.mostrarFranjas()
    }


/*


    fun mostrarTodasFranjas(): Flow<List<Franja>>{
        return miDAO.mostrarTodasFranjas()
    }

    fun buscarFranjaPorId(id:Int):Flow<Franja>{
        return miDAO.buscarFranjaPorId(id)
    }


    //REGISTRO
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarRegistro(miRegistro: Registro){
        miDAO.insertar(miRegistro)
    }

    fun buscarRegistroPorId(id:Int):Flow<Registro>{
        return miDAO.buscarRegistroPorId(id)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrarRegistro(miRegistro: Registro){
        miDAO.borrar(miRegistro)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun modificarRegistro(miRegistro: Registro){
        miDAO.modificar(miRegistro)
    }*/
}