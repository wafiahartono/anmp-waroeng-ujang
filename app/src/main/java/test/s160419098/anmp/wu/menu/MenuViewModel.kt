package test.s160419098.anmp.wu.menu

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import test.s160419098.anmp.wu.main.Application

class MenuViewModel(
    private val app: android.app.Application,
) : AndroidViewModel(app) {
    private val service get() = (app as Application).service

    val menuCategoriesQuery = MutableLiveData("")

    val menuCategories: LiveData<List<MenuCategory>> = menuCategoriesQuery.switchMap { query ->
        liveData(Dispatchers.IO) {
            val menuCategories =
                if (query.isEmpty())
                    service.getMenuCategories()
                else
                    service.getMenuCategories()
                        .map { category ->
                            category.copy(
                                items = category.items.filter { item ->
                                    item.name.contains(query, true)
                                }
                            )
                        }
                        .filter { category -> category.items.isNotEmpty() }
            emit(menuCategories)
        }
    }
}
