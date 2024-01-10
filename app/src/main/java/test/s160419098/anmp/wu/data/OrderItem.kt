package test.s160419098.anmp.wu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity(
    tableName = "order_items",
    primaryKeys = ["order_id", "menu_id"],
)
data class OrderItem(
    @ColumnInfo(name = "order_id")
    val orderId: Long,

    @ColumnInfo(name = "menu_id")
    val menuId: Long,

    val quantity: Int,

    val subtotal: Float,
) {
    @Ignore
    lateinit var menu: Menu
}
