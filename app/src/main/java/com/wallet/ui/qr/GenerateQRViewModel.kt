package com.wallet.ui.qr

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.domain.usecases.GenerateQRUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateQRViewModel @Inject constructor(
    private val generateQRUseCase: GenerateQRUseCase
): ViewModel() {
    private var _qrBitmap = MutableLiveData<Bitmap>()
    val qrBitmap: LiveData<Bitmap>
        get() = _qrBitmap

    fun generateQR() {
        viewModelScope.launch {
            val qrBitmapResult = generateQRUseCase.getGeneratedQR()

            if(qrBitmapResult != null) {
                _qrBitmap.value = generateQRUseCase.decodeQRResponse(qrBitmapResult)
            }
        }
    }

}
