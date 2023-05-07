package com.example.proyecto_ong


//niebla, lluvia, agua son INT -> en base de datos 1(checked), 0(non-checked)
data class Registro(val id:Int, var fecha:String, var niebla:Int, val agua: Int, var lluvia: Int, val densidad:String, val idUsuario: Int, val idFranja: Int, val nombre: String) {

    //constructor(id:Int, fecha:String, niebla:Int, agua: Int, lluvia: Int, densidad:String, idFranja: Int, nombre: String): this(id, fecha, niebla, agua, lluvia,  densidad, 0, idFranja, nombre)

}

