package com.example.proyecto_ong


//niebla, lluvia, agua son INT -> en base de datos 1(checked), 0(non-checked)
class CondicionMeteorologicaClase(val id:Int,var fecha:String, var niebla:Int, var lluvia: Int, val agua: Int, val densidad: String, val idUsuario: Int) {
}

