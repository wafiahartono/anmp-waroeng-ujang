package test.s160419098.anmp.wu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val table: Int,

    val datetime: Date? = null,

    @ColumnInfo(name = "closed_at")
    val closedAt: Date? = null,

    val status: Status = Status.CART,
) {
    @Ignore
    lateinit var items: List<OrderItem>

    val subtotal
        get() = items.fold(0F) { acc, item -> acc + item.menu.price * item.quantity }

    val tax
        get() = subtotal * 0.1F

    val total
        get() = subtotal * 1.1F

    val detail
        get() = items.joinToString { "${it.quantity}x ${it.menu.name}" }

    enum class Status { CART, ORDER, CLOSED }
}
