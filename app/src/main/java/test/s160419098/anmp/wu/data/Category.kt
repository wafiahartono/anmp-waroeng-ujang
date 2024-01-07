package test.s160419098.anmp.wu.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
) {
    @Ignore
    lateinit var items: List<Menu>
}
