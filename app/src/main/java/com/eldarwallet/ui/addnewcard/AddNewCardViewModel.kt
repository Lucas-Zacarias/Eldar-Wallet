package com.eldarwallet.ui.addnewcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldarwallet.domain.models.CardInput
import com.eldarwallet.domain.models.CardType
import com.eldarwallet.domain.models.NewCardResult
import com.eldarwallet.domain.usecases.AddNewCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewCardViewModel @Inject constructor(
    private val addNewCardUseCase: AddNewCardUseCase
): ViewModel() {

    private var _addNewCardState = MutableLiveData<NewCardResult>()
    val addNewCardState: LiveData<NewCardResult>
        get() = _addNewCardState

    private var _cardType = MutableLiveData<CardType>()
    val cardType: LiveData<CardType>
        get() = _cardType

    fun validateAddNewCard(cardInput: CardInput) {
        viewModelScope.launch {
            val result = addNewCardUseCase.addNewCard(cardInput)

            _addNewCardState.value = result
        }
    }

    fun setCardType(cardNumberLength: String) {
        val cardValue = addNewCardUseCase.setCardType(cardNumberLength)

        _cardType.value = cardValue
    }
}