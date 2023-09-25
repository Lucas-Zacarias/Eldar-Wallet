package com.wallet.ui.paywithcard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wallet.ui.theme.WalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayWithCardActivity : AppCompatActivity() {
    private val payWithCardViewModel: PayWithCardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WalletTheme {
                PayWithCardScreen(
                    backEvent = {
                        goBack()
                    },
                    viewModel = payWithCardViewModel
                )
            }
        }

        setObservable()
    }

    private fun setObservable() {
        payWithCardViewModel.getCardData(getCardNumberExtra())
    }

    private fun getCardNumberExtra(): ByteArray {
        val extras = intent.extras!!
        return extras.getByteArray("card")!!
    }

    private fun goBack() {
        finish()
    }

}