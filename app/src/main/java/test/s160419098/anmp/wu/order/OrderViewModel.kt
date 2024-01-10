package test.s160419098.anmp.wu.order

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.main.Application

class OrderViewModel(
    private val app: android.app.Application
) : AndroidViewModel(app) {

    private val database
        get() = (app as Application).database

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val orders = database.orderDao().all().toList().map { pair ->
                pair.first.apply {
                    items = pair.second.map { it.item.apply { menu = it.menu } }
                }
            }

            _orders.postValue(orders)
        }
    }

    fun moveOrderToCart(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.orderDao().moveToCart(orderId)

            _orders.postValue(
                _orders.value!!.filter { it.id != orderId }
            )
        }
    }

    fun closeOrder(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.orderDao().close(orderId)

            _orders.postValue(
                _orders.value!!.filter { it.id != orderId }
            )
        }
    }

}
