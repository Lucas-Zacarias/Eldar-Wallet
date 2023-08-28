package com.eldarwallet.data.api.serviceImpl

import com.eldarwallet.data.api.QRGenerateGateway
import com.eldarwallet.domain.services.ApiService
import java.io.InputStream
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val qrGenerateGateway: QRGenerateGateway
): ApiService {
    override suspend fun getGeneratedQR(content: String): InputStream? {
        return qrGenerateGateway.getQrCode(content)
    }
}