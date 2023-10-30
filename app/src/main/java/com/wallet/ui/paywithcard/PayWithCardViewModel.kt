package com.wallet.ui.paywithcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.domain.models.CardInput
import com.wallet.domain.usecases.PayWithCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayWithCardViewModel @Inject constructor(
    private val payWithCardUseCase: PayWithCardUseCase
): ViewModel() {
    private var _cardData = MutableLiveData<CardInput>()
    val cardData: LiveData<CardInput>
        get() = _cardData

    fun getCardData(cardNumber: ByteArray) {
        viewModelScope.launch {
            _cardData.value = payWithCardUseCase.getCardData(cardNumber)
        }
    }

}