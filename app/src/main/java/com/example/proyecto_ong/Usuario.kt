package com.example.proyecto_ong

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuarios")
data class Usuario (
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @NonNull @ColumnInfo(name="nombre") val nombre:String="",
    @NonNull @ColumnInfo(name="contrasena") val contrasena:String = "",
    @NonNull @ColumnInfo(name="region") val region:String = "",
    @NonNull @ColumnInfo(name="rol") val rol:String = "") {}

