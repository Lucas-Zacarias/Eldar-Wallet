package com.wallet.data.api.serviceImpl

import com.wallet.data.api.QRGenerateGateway
import com.wallet.domain.services.ApiService
import java.io.InputStream
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val qrGenerateGateway: QRGenerateGateway
): ApiService {
    override suspend fun getGeneratedQR(content: String): InputStream? {
        return qrGenerateGateway.getQrCode(content)
    }
}