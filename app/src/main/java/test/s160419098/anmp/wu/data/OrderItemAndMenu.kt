package test.s160419098.anmp.wu.data

import androidx.room.Embedded
import androidx.room.Relation

data class OrderItemAndMenu(
    @Embedded
    val item: OrderItem,

    @Relation(
        parentColumn = "menu_id",
        entityColumn = "id",
    )
    val menu: Menu,
)
