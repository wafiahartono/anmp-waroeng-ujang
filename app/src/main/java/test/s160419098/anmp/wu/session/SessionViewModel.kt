package test.s160419098.anmp.wu.session

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.data.Waiter
import test.s160419098.anmp.wu.main.Application

class SessionViewModel(
    private val app: android.app.Application
) : AndroidViewModel(app) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(
                prefs.getLong("user", 0).let {
                    if (it == 0L) null
                    else database.waiterDao().find(it)?.copy(password = "")
                }
            )
        }
    }

    private val database
        get() = (app as Application).database

    private val prefs
        get() = app.getSharedPreferences("session", Context.MODE_PRIVATE)

    private val _user = MutableLiveData<Waiter?>()
    val user: LiveData<Waiter?> = _user

    private val _signInResult = MutableLiveData<Result<Unit>>()
    val signInResult: LiveData<Result<Unit>> = _signInResult

    fun signIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signInResult.postValue(Result.Loading)

            val user = database.waiterDao().find(username)

            if (user != null && user.password == password) {
                prefs.edit { putLong("user", user.id) }

                _user.postValue(user.copy(password = ""))

                _signInResult.postValue(Result.Success(Unit))
            } else {
                _signInResult.postValue(
                    Result.Error(
                        Exception(app.getString(R.string.invalid_username_or_password))
                    )
                )
            }
        }
    }

    fun signOut() {
        prefs.edit { remove("user") }
        _user.value = null
    }

}
