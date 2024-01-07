package test.s160419098.anmp.wu.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "waiters",
    indices = [Index(value = ["username"], unique = true)]
)
data class Waiter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val username: String,
    val password: String,
    val sex: Sex,
)

enum class Sex {
    MALE, FEMALE
}
