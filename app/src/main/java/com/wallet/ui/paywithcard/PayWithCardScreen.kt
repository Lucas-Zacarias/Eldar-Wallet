package com.wallet.ui.paywithcard

import android.nfc.NfcAdapter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.R
import com.wallet.domain.models.CardInput
import com.wallet.domain.models.CardType
import com.wallet.ui.reusablecomponents.FlipCard
import com.wallet.ui.reusablecomponents.HeightSpacer
import com.wallet.ui.reusablecomponents.ReusableButton
import com.wallet.ui.reusablecomponents.ReusableDialog
import com.wallet.ui.reusablecomponents.WidthSpacer
import com.wallet.domain.models.CardFace

@Composable
fun PayWithCardScreen(
    backEvent: () -> Unit,
    viewModel: PayWithCardViewModel
) {

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {

        Toolbar(backEvent)
        HeightSpacer(height = 80)

        Column(
            modifier =
            Modifier.padding(horizontal = 20.dp),
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {

            CardData(viewModel)
            HeightSpacer(height = 40)

            Box(
                modifier =
                Modifier.padding(horizontal = 20.dp)
            ) {
                PayButton()
            }

        }
    }

}

@Composable
private fun Toolbar(
    backEvent: () -> Unit
) {
    Row(
        modifier =
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Icon(
            imageVector =
            Icons.Outlined.ArrowBack,
            contentDescription =
            stringResource(id = R.string.icon_to_go_back),
            tint =
            MaterialTheme.colorScheme.primary,
            modifier =
            Modifier
                .size(32.dp)
                .clickable {
                    backEvent()
                })

        WidthSpacer(width = 10)

        Text(
            text = stringResource(id = R.string.card_data),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
private fun CardData(
    viewModel: PayWithCardViewModel
) {
    val cardData = viewModel.cardData.observeAsState().value
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    FlipCard(
        cardFace = cardFace,
        onClick = {
            cardFace = cardFace.next
        },
        front = {
            if (cardData != null) {
                FrontCard(cardData.type)
            }
        },
        back = {
            if (cardData != null) {
                BackCard(cardData)
            }
        }
    )
}

@Composable
fun FrontCard(cardData: CardType) {
    Card(
        shape =
        RoundedCornerShape(10.dp),
        modifier =
        Modifier
            .size(width = 300.dp, height = 200.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Box {
            Image(
                painterResource(
                    id =
                    when (cardData) {
                        CardType.VISA -> R.drawable.visa
                        CardType.AMERICAN_EXPRESS -> R.drawable.american_express
                        CardType.MASTERCARD -> R.drawable.mastercard
                        else -> if (!isSystemInDarkTheme()) {
                            R.drawable.blank_card_dark_mode
                        } else {
                            R.drawable.blank_card_light_mode
                        }
                    }
                ),
                contentDescription =
                stringResource(id = R.string.card_image),
                modifier = Modifier.fillMaxSize()
            )

            Icon(
                imageVector =
                Icons.Outlined.TouchApp,
                contentDescription =
                stringResource(id = R.string.button_to_flip_card),
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)
                    .size(32.dp)
            )
        }

    }
}

@Composable
private fun BackCard(cardData: CardInput) {
    Card(
        shape =
        RoundedCornerShape(10.dp),
        modifier =
        Modifier
            .size(width = 300.dp, height = 200.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                HeightSpacer(height = 20)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(
                            color = Color.Black
                        )
                )
                HeightSpacer(height = 20)
                Text(
                    text = stringResource(id = R.string.owner, cardData.name, cardData.surname),
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.surface
                )
                HeightSpacer(height = 20)
                Text(
                    text = cardData.number,
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.surface
                )
                HeightSpacer(height = 20)
                Row(
                    modifier =
                    Modifier.padding(start = 20.dp)
                ) {
                    Text(
                        text = cardData.month.plus("/").plus(cardData.year),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.surface
                    )

                    WidthSpacer(width = 20)

                    Text(
                        text = stringResource(id = R.string.cvc_mayus, cardData.cvc),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }

            Icon(
                imageVector =
                Icons.Outlined.TouchApp,
                contentDescription =
                stringResource(id = R.string.button_to_flip_card),
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)
                    .size(32.dp)
            )
        }
    }
}

@Composable
private fun PayButton() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    ReusableButton(
        clickEvent =
        {
            val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
            val isAvailable = nfcAdapter != null
            val isEnabled = nfcAdapter?.isEnabled == true

            dialogMessage = if (isAvailable) {
                if (isEnabled) {
                    context.getString(R.string.searching_devices_to_pay)
                } else {
                    context.getString(R.string.nfc_off)
                }
            } else {
                context.getString(R.string.device_without_nfc)
            }
            showDialog = !showDialog
        },
        text =
        stringResource(id = R.string.pay_with_nfc)
    )

    if (showDialog) {
        ReusableDialog(
            title =
            stringResource(id = R.string.pay_with_nfc),
            text = dialogMessage,
            show = true
        )
    }

}