package test.s160419098.anmp.wu.cart

import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.data.OrderItem

class CartViewModel(
    app: android.app.Application,
) : AndroidViewModel(app) {
    private val _table = MutableLiveData<Int?>(null)
    val table: LiveData<Int?> = _table

    private val _items = MutableLiveData<List<OrderItem>>(emptyList())
    val items: LiveData<List<OrderItem>> = _items

    val subtotal: LiveData<Float> = _items.map { items ->
        items.fold(0F) { acc, item -> acc + item.menu.price * item.quantity }
    }

    val tax: LiveData<Float> = subtotal.map { subtotal -> subtotal * 0.1F }

    val total: LiveData<Float> = subtotal.map { subtotal -> subtotal * 1.1F }

    fun getOrder() = Order(
        table = _table.value!!,
        datetime = SystemClock.elapsedRealtime(),
        items = _items.value!!,
    )

    fun set(order: Order) {
        _table.value = order.table
        _items.value = order.items
    }

    fun set(table: Int, items: List<OrderItem>?) {
        _table.value = table
        _items.value = items.orEmpty()
    }

    fun setItem(item: OrderItem) {
        val items = _items.value.orEmpty().toMutableList()

        val index = items.indexOfFirst { it.menu.id == item.menu.id }

        if (index >= 0)
            items[index] = item
        else
            items.add(item)

        _items.value = items.filter { it.quantity > 0 }
    }

    fun empty() {
        _table.value = null
        _items.value = emptyList()
    }
}
