package com.wallet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.domain.models.LoginResult
import com.wallet.domain.models.UserLogIn
import com.wallet.domain.usecases.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
): ViewModel() {

    private var _logInState = MutableLiveData<LoginResult?>()
    val logInState: LiveData<LoginResult?>
        get() = _logInState

    fun validateLogIn(userLogIn: UserLogIn) {
        viewModelScope.launch {
            _logInState.value = null
            val result = logInUseCase.logIn(userLogIn)

            if(result == LoginResult.Success) {
                logInUseCase.saveUserSession(userLogIn.email)
            }

            _logInState.value = result
        }
    }
}