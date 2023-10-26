package test.s160419098.anmp.wu.order

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.s160419098.anmp.wu.cart.CartItem

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>(listOf())
    val orders: LiveData<List<Order>> = _orders

    fun setOrder(table: Int, items: List<CartItem>) {
        val orders = _orders.value.orEmpty().toMutableList()
        val index = orders.indexOfFirst { it.table == table }
        if (index >= 0)
            orders[index] = orders[index].copy(items = items)
        else
            orders.add(Order(table, items, SystemClock.elapsedRealtime()))
        _orders.value = orders
    }

    fun removeOrder(order: Order) {
        _orders.value = _orders.value.orEmpty().filter { it.table != order.table }
    }
}
