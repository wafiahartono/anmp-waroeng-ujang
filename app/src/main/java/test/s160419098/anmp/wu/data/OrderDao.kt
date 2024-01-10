package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import androidx.room.Update
import java.util.Date

@Dao
interface OrderDao {
    @Insert
    fun insert(order: Order): Long

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
            SELECT * FROM orders
            JOIN order_items ON order_items.order_id = orders.id
            JOIN menus ON menus.id = order_items.menu_id
            WHERE orders.id = :id
        """
    )
    fun find(id: Long): Map<Order, List<OrderItemAndMenu>>

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
            SELECT * FROM orders
            JOIN order_items ON order_items.order_id = orders.id
            JOIN menus ON menus.id = order_items.menu_id
            WHERE status = 'ORDER'
            ORDER BY datetime
        """
    )
    fun all(): Map<Order, List<OrderItemAndMenu>>

    @Update
    fun update(order: Order)

    @Query(
        """
            UPDATE orders
            SET status = 'CART'
            WHERE id = :orderId
        """
    )
    fun moveToCart(orderId: Long)

    @Query(
        """
            UPDATE orders
            SET closed_at = :date, status = 'CLOSED'
            WHERE id = :orderId
        """
    )
    fun close(orderId: Long, date: Date = Date())
}
