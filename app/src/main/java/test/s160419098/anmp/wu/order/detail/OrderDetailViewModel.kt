package test.s160419098.anmp.wu.order.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import test.s160419098.anmp.wu.order.Order

class OrderDetailViewModel : ViewModel() {
    lateinit var getOrderFn: (table: Int) -> Order

    val table = MutableLiveData<Int>()

    val order: LiveData<Order> = table.map { getOrderFn(it) }
}
