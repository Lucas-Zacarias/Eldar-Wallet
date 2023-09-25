package com.wallet.domain.usecases

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.wallet.domain.services.ApiService
import java.io.InputStream
import javax.inject.Inject

class GenerateQRUseCase @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun getGeneratedQR(): InputStream? {
        return apiService.getGeneratedQR(getContentToGenerateQR())
    }

    fun decodeQRResponse(qrInputStream: InputStream): Bitmap {
        return BitmapFactory.decodeStream(qrInputStream)
    }

    private fun getUserName(): String? {
        return sharedPreferences.getString("name", null)
    }

    private fun getUserSurname(): String? {
        return sharedPreferences.getString("surname", null)
    }

    private fun getContentToGenerateQR(): String {
        return if (getUserName() != null && getUserSurname() != null) {
            getUserName().plus(" ").plus(getUserSurname())
        } else {
            "Eldar Wallet"
        }
    }

}