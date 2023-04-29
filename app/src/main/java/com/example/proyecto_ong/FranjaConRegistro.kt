package com.plcoding.multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.proyecto_ong.CondicionMeteorologica
import com.example.proyecto_ong.Franja
import com.example.proyecto_ong.RegistroFranjaCrossRef


data class FranjaConRegistro(
    @Embedded val franja: Franja,
    @Relation(
        parentColumn = "id_franja",
        entityColumn = "id_registro",
        associateBy = Junction(RegistroFranjaCrossRef::class)
    )
    val registros: List<CondicionMeteorologica>
)