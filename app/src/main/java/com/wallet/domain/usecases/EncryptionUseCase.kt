package com.wallet.domain.usecases

import android.annotation.SuppressLint
import com.wallet.domain.models.Secrets
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object EncryptionUseCase {

    @SuppressLint("GetInstance")
    fun encrypt(string: String): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(string.toByteArray())
    }

    @SuppressLint("GetInstance")
    fun decrypt(encryptedString: ByteArray): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return String(cipher.doFinal(encryptedString))
    }

    private val secretKey: SecretKey = SecretKeySpec(
        Secrets.KEY_TO_ENCRYPT.toByteArray(),
        "AES"
    )
}