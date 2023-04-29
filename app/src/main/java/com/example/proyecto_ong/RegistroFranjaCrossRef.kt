package com.example.proyecto_ong

@Entity(primaryKeys = ["id_registro", "id_franja"])
data class RegistroFranjaCrossRef(
    val id_registro: String,
    val id_franja: String
)