package test.s160419098.anmp.wu.session

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.data.Waiter
import test.s160419098.anmp.wu.main.Application

class SessionViewModel(
    private val app: android.app.Application,
) : AndroidViewModel(app) {
    private val db
        get() = (app as Application).database

    private val prefs
        get() = app.getSharedPreferences("session", Context.MODE_PRIVATE)

    private val _user: MutableLiveData<Waiter?> = MutableLiveData(
        prefs.getString("user", null)?.let {
            return@let Json.decodeFromString<Waiter>(it)
        }
    )
    val user: LiveData<Waiter?> = _user

    val isAuthenticated get() = _user.value != null

    private val _signInResult = MutableLiveData<Result<Boolean>>()
    val signInResult: LiveData<Result<Boolean>> = _signInResult

    fun signIn(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _signInResult.postValue(Result.Loading)

        val user = db.waiterDao().find(username)

        if (user != null && user.password == password) {
            prefs.edit { putString("user", Json.encodeToString(user)) }

            _user.postValue(user)
            _signInResult.postValue(Result.Success(true))
        } else {
            _signInResult.postValue(Result.Success(false))
        }
    }

    fun signOut() {
        prefs.edit { remove("user") }
        _user.value = null
    }
}
