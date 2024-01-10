package test.s160419098.anmp.wu.order.detail

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.main.Application

class OrderDetailViewModel(
    private val app: android.app.Application
) : AndroidViewModel(app) {

    private val database
        get() = (app as Application).database

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    fun fetch(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = database.orderDao().find(orderId).entries.first().let { entry ->
                entry.key.apply {
                    items = entry.value.map { it.item.apply { menu = it.menu } }
                }
            }

            _order.postValue(order)
        }
    }

}
