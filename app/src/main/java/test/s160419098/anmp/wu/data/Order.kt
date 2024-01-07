package test.s160419098.anmp.wu.data

data class Order(
    val table: Int,
    val datetime: Long,
    val items: List<OrderItem>,
) {
    val subtotal = items
        .fold(0F) { acc, item -> acc + item.menu.price * item.quantity }

    val tax = subtotal * 0.1F

    val total = subtotal * 1.1F

    val detail = items.joinToString {
        "${it.quantity}x ${it.menu.name}"
    }
}
