package com.eldarwallet.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserSignUp
import com.eldarwallet.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private var _signUpState = MutableLiveData<LoginResult>()
    val signUpState: LiveData<LoginResult>
        get() = _signUpState

    fun validateSignUpForm(userSignUp: UserSignUp){
        viewModelScope.launch {
            val result = signUpUseCase.signUp(userSignUp)

            if(result == LoginResult.Success) {
                signUpUseCase.saveUserSession(userSignUp)
            }

            _signUpState.value = result
        }
    }

}