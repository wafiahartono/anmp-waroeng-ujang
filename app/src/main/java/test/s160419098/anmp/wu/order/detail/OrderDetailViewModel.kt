package test.s160419098.anmp.wu.order.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.s160419098.anmp.wu.data.Order

class OrderDetailViewModel : ViewModel() {
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    fun getOrderSkipValueAccessor() = _order.value!!

    fun setOrder(order: Order) {
        _order.value = order
    }

    fun getTable() = _order.value!!.table
}
