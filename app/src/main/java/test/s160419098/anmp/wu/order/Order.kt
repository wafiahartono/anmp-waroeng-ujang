package test.s160419098.anmp.wu.order

import test.s160419098.anmp.wu.cart.CartItem

data class Order(
    val table: Int,
    val items: List<CartItem>,
    val processedAt: Long,
)
