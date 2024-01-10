package test.s160419098.anmp.wu.cart

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.data.Menu
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.data.OrderItem
import test.s160419098.anmp.wu.main.Application

class CartViewModel(
    private val app: android.app.Application
) : AndroidViewModel(app) {

    private val database
        get() = (app as Application).database

    private val _table = MutableLiveData<Int?>(null)
    val table: LiveData<Int?> = _table

    private val _items = MutableLiveData<List<OrderItem>>(emptyList())
    val items: LiveData<List<OrderItem>> = _items

    val subtotal: LiveData<Float> = _items.map { items ->
        items.fold(0F) { acc, item -> acc + item.subtotal }
    }

    val tax: LiveData<Float> = subtotal.map { it * 0.1F }

    val total: LiveData<Float> = subtotal.map { it * 1.1F }

    fun updateTable(table: Int?) {
        if (table == null) {
            _table.postValue(null)
            _items.postValue(emptyList())
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val items = database.cartDao().find(table).entries.firstOrNull()?.let { entry ->
                entry.value.map { it.item.apply { menu = it.menu } }
            }

            _table.postValue(table)
            _items.postValue(items ?: emptyList())
        }
    }

    fun setCartFromOrder(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = database.orderDao().find(orderId).entries.first().let { entry ->
                entry.key.apply {
                    items = entry.value.map { it.item.apply { menu = it.menu } }
                }
            }

            _table.postValue(order.table)
            _items.postValue(order.items)
        }
    }

    fun setItemQuantity(menu: Menu, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var orderId = database.cartDao().getOrderId(table.value!!)

            if (orderId == null) {
                orderId = database.orderDao().insert(Order(id = 0, table = table.value!!))
            }

            val items = _items.value!!.toMutableList()
            val index = items.indexOfFirst { it.menu.id == menu.id }

            val item = OrderItem(orderId, menu.id, quantity, menu.price * quantity).apply {
                this.menu = menu
            }

            if (index < 0) {
                database.cartDao().insertItem(item)
                items.add(item)
            } else if (quantity == 0) {
                database.cartDao().deleteItem(item)
                items.removeAt(index)
            } else {
                database.cartDao().updateItem(item)
                items[index] = item
            }

            _items.postValue(items)
        }
    }

    fun processToOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            database.cartDao().moveToOrder(table.value!!)

            _table.postValue(null)
            _items.postValue(emptyList())
        }
    }

}
