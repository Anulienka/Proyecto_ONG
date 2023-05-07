package com.example.proyecto_ong

//@Entity(tableName = "tabla_usuarios")
//data class Usuario (
//    @PrimaryKey(autoGenerate = true) var id:Int=0,
//    @NonNull @ColumnInfo(name="nombre") val nombre:String="",
//    @NonNull @ColumnInfo(name="contrasena") val contrasena:String = "",
//    @NonNull @ColumnInfo(name="region") val region:String = "",
//    @NonNull @ColumnInfo(name="rol") val rol:String = "") {}

class Usuario(var id: String?, val nombre: String?, val contrasena: String?, val region: String?){


}