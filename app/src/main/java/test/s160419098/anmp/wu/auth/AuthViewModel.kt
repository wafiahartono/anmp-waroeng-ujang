package test.s160419098.anmp.wu.auth

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.main.Application

class AuthViewModel(
    private val app: android.app.Application,
) : AndroidViewModel(app) {
    private val service get() = (app as Application).service

    private val _user = MutableLiveData(service.getSignedInUser())
    val user: LiveData<User?> = _user

    private val _signInResult = MutableLiveData<Result<Boolean>>()
    val signInResult: LiveData<Result<Boolean>> = _signInResult

    fun signIn(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _signInResult.postValue(Result.Loading)

        try {
            val user = service.signIn(username, password)

            if (user != null) _user.postValue(user)

            _signInResult.postValue(Result.Success(user != null))
        } catch (e: Exception) {
            _signInResult.postValue(Result.Error(e))
        }
    }

    fun signOut() {
        service.signOut()
        _user.postValue(null)
    }

    override fun onCleared() {
        super.onCleared()
        service.dispose()
    }
}
