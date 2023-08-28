package com.eldarwallet.data.api

import com.eldarwallet.domain.models.Secrets
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface QRGenerateClient {

    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "X-RapidAPI-Key: ${Secrets.RAPID_API_KEY}",
        "X-RapidAPI-Host: neutrinoapi-qr-code.p.rapidapi.com"
    )
    @FormUrlEncoded
    @POST("qr-code")
    suspend fun generateQRCode(
        @Field("content") content: String
    ): Response<ResponseBody>

}