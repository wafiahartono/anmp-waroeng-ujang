package test.s160419098.anmp.wu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menus")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val name: String,
    val price: Float,
    val description: String,
)
