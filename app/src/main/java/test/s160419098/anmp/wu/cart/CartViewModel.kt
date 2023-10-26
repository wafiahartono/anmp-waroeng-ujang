package test.s160419098.anmp.wu.cart

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

class CartViewModel(
    app: android.app.Application,
) : AndroidViewModel(app) {
    val table = MutableLiveData<Int?>(null)

    private val _items = MutableLiveData<List<CartItem>>(emptyList())
    val items: LiveData<List<CartItem>> = _items

    val subtotal: LiveData<Int> = _items.map { items ->
        items.fold(0) { acc, item -> acc + item.menu.price * item.quantity }
    }
    val tax: LiveData<Float> = subtotal.map { subtotal -> subtotal * 0.1F }
    val total: LiveData<Float> = subtotal.map { subtotal -> subtotal * 1.1F }

    fun setItems(items: List<CartItem>) {
        _items.value = items
    }

    fun setItem(item: CartItem) {
        val items = _items.value.orEmpty().toMutableList()
        val index = items.indexOfFirst { it.menu.id == item.menu.id }
        if (index >= 0)
            items[index] = item
        else
            items.add(item)
        _items.value = items.filter { it.quantity > 0 }
    }

    fun emptyCart() {
        _items.value = emptyList()
    }
}
