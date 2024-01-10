package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import androidx.room.Update
import java.util.Date

@Dao
interface CartDao {
    @Query(
        """
            SELECT id FROM orders
            WHERE `table` = :table AND status = 'CART' 
        """
    )
    fun getOrderId(table: Int): Long?

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
            SELECT * FROM orders
            JOIN order_items ON order_items.order_id = orders.id
            JOIN menus ON menus.id = order_items.menu_id
            WHERE `table` = :table AND status = 'CART'
        """
    )
    fun find(table: Int): Map<Order, List<OrderItemAndMenu>>

    @Query(
        """
            UPDATE orders
            SET datetime = :datetime, status = 'ORDER'
            WHERE `table` = :table AND status = 'CART'
        """
    )
    fun moveToOrder(table: Int, datetime: Date = Date())

    @Insert
    fun insertItem(item: OrderItem)

    @Update
    fun updateItem(item: OrderItem)

    @Delete
    fun deleteItem(item: OrderItem)
}
