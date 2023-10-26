package test.s160419098.anmp.wu.menu.detail

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import test.s160419098.anmp.wu.main.Application
import test.s160419098.anmp.wu.menu.Menu

class MenuDetailViewModel(
    private val app: android.app.Application,
) : AndroidViewModel(app) {
    private val service get() = (app as Application).service

    val menuId = MutableLiveData<Int>()

    val menu: LiveData<Menu> = menuId.switchMap { id ->
        liveData(Dispatchers.IO) { emit(service.getMenu(id)) }
    }

    private val _menuQuantity = MutableLiveData(1)
    val menuQuantity: LiveData<Int> = _menuQuantity

    fun incrementQuantity() {
        _menuQuantity.value = _menuQuantity.value!!.inc()
    }

    fun decrementQuantity() {
        _menuQuantity.value = _menuQuantity.value!!.dec()
    }
}
