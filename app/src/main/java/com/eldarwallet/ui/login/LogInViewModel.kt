package com.eldarwallet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserLogIn
import com.eldarwallet.domain.usecases.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
): ViewModel() {

    private var _logInState = MutableLiveData<LoginResult>()
    val logInState: LiveData<LoginResult>
        get() = _logInState

    fun validateLogIn(userLogIn: UserLogIn) {
        viewModelScope.launch {
            _logInState.value = logInUseCase.logIn(userLogIn)
        }
    }
}