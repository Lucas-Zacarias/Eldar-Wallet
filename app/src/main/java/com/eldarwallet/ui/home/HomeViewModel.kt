package com.eldarwallet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.usecases.AddNewCardUseCase
import com.eldarwallet.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
): ViewModel() {

    private var _cardList = MutableLiveData<List<Card>>()
    val cardList: LiveData<List<Card>>
        get() = _cardList


    fun getCardList() {
        viewModelScope.launch {
            _cardList.value = homeUseCase.getCardList()
        }
    }

}