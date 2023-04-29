package com.example.proyecto_ong

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_registros")
data class CondicionMeteorologica (
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @NonNull @ColumnInfo(name="fecha") val fechaRegistro:String="",
    @NonNull @ColumnInfo(name="niebla") val hayNiebla:Int=0,
    @NonNull @ColumnInfo(name="agua") val hayAgua:Int=0,
    @NonNull @ColumnInfo(name="lluvia") val hayLluvia:Int=0,
    @ColumnInfo(name="densidad") val densidad:String="",
    @NonNull @ColumnInfo(name="idUsuario") val idUsuario:Int= 0){}



