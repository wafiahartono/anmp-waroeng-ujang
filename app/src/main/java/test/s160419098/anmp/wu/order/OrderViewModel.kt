package test.s160419098.anmp.wu.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.s160419098.anmp.wu.data.Order

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>(listOf())
    val orders: LiveData<List<Order>> = _orders

    fun setOrder(order: Order) {
        val orders = _orders.value.orEmpty().toMutableList()

        val index = orders.indexOfFirst { it.table == order.table }

        if (index >= 0)
            orders[index] = orders[index].copy(items = order.items)
        else
            orders.add(order)

        _orders.value = orders
    }

    fun getTableOrder(table: Int) =
        _orders.value!!.firstOrNull { it.table == table }

    fun removeTableOrder(table: Int) {
        _orders.value = _orders.value.orEmpty().filter { it.table != table }
    }
}
