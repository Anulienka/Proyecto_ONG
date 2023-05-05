/*
import com.example.proyecto_ong.CondicionMeteorologica
import com.example.proyecto_ong.Franja
import com.example.proyecto_ong.RegistroFranjaCrossRef
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RegistroConFranja(
    //@Embedded para incluir todos los campos de la tabla Registro directamente en esta entidad
    @Embedded val registro: CondicionMeteorologica,
    //@Relation para indicar que esta entidad tiene una relación con la tabla franja
    @Relation(
        parentColumn = "id_registro",
        entityColumn = "id_franja",

        //Registro, y la propiedad associateBy indica la tabla de unión que se utiliza para relacionar ambas tablas.
        associateBy = Junction(RegistroFranjaCrossRef::class)
    )
    val franjas: List<Franja>
)*/
