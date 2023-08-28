package com.eldarwallet.data.api

import java.io.InputStream
import javax.inject.Inject

class QRGenerateGateway @Inject constructor(
    private val qrGenerateClient: QRGenerateClient
) {

    suspend fun getQrCode(content: String): InputStream? {
        val response = qrGenerateClient.generateQRCode(content)

        return if (response.isSuccessful && response.body() != null) {
            response.body()!!.byteStream()
        } else {
            null
        }
    }

}