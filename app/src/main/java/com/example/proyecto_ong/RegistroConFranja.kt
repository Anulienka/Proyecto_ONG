import com.example.proyecto_ong.CondicionMeteorologica
import com.example.proyecto_ong.Franja
import com.example.proyecto_ong.RegistroFranjaCrossRef
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RegistroConFranja(
    @Embedded val registro: CondicionMeteorologica,
    @Relation(
        parentColumn = "id_registro",
        entityColumn = "id_region",
        associateBy = Junction(RegistroFranjaCrossRef::class)
    )
    val franjas: List<Franja>
)