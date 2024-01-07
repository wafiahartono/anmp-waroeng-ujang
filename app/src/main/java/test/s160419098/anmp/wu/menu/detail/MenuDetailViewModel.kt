package test.s160419098.anmp.wu.menu.detail

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.data.Menu
import test.s160419098.anmp.wu.data.OrderItem
import test.s160419098.anmp.wu.main.Application

class MenuDetailViewModel(
    private val app: android.app.Application,
) : AndroidViewModel(app) {
    private val db
        get() = (app as Application).database

    private val _menu = MutableLiveData<Menu>()
    val menu: LiveData<Menu> = _menu

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    fun getOrderItem() = OrderItem(
        menu = menu.value!!,
        quantity = _quantity.value!!,
    )

    fun fetchMenu(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _menu.postValue(db.menuDao().find(id)!!)
    }

    fun incrementQuantity() {
        _quantity.value = _quantity.value!!.inc()
    }

    fun decrementQuantity() {
        _quantity.value = _quantity.value!!.dec()
    }
}
