package test.s160419098.anmp.wu.menu

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import test.s160419098.anmp.wu.data.Category
import test.s160419098.anmp.wu.main.Application

class MenuViewModel(
    private val app: android.app.Application
) : AndroidViewModel(app) {

    private val database
        get() = (app as Application).database

    val query = MutableLiveData("")

    val categories: LiveData<List<Category>> = query.switchMap { query ->
        liveData(Dispatchers.IO) {
            val categories = database.categoryDao().all(query).toList().map { pair ->
                pair.first.apply { items = pair.second }
            }

            emit(categories)
        }
    }

}
