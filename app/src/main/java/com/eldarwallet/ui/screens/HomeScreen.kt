package com.eldarwallet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.eldarwallet.R
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.models.CardType
import com.eldarwallet.domain.usecases.EncryptionUseCase
import com.eldarwallet.ui.home.HomeViewModel
import com.eldarwallet.ui.screens.reusablecomponents.ReusableCardItem
import com.eldarwallet.ui.screens.reusablecomponents.ReusableDialog

@Composable
fun HomeScreen(
    userInitials: String,
    userName: String?,
    viewModel: HomeViewModel,
    signOffEvent: () -> Unit,
    goToPayEvent: (ByteArray) -> Unit
) {

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UserGreetings(userInitials, userName, signOffEvent)
        UserBalance()
        UserCards(viewModel, goToPayEvent)

    }

}

@Composable
private fun UserGreetings(
    userInitials: String,
    userName: String?,
    signOffEvent: () -> Unit
) {
    var isDialogShowing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Box(
            modifier =
            Modifier
                .size(50.dp)
                .background(
                    color =
                    MaterialTheme.colorScheme.tertiary,
                    shape =
                    CircleShape
                )
                .clickable {
                    isDialogShowing = !isDialogShowing
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userInitials,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text =
            stringResource(id = R.string.hello)
                .plus(" ")
                .plus(userName ?: stringResource(id = R.string.wallet))
                .plus("!"),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 15.dp)
        )

    }

    if (isDialogShowing) {
        SignOffDialog(signOffEvent)
    }
}

@Composable
private fun SignOffDialog(
    signOffEvent: () -> Unit
) {
    ReusableDialog(
        title =
        stringResource(id = R.string.sign_off),
        text =
        stringResource(id = R.string.sign_off_confirm),
        show = true,
        hasCancelOption = true,
        ifConfirmOnlyToCloseDialog = false,
        onConfirm = {
            signOffEvent()
        }
    )
}

@Composable
private fun UserBalance() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier.width(150.dp),
            shape = RoundedCornerShape(10.dp),
            colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.balance),
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = stringResource(id = R.string.user_default_balance),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            }

        }
    }


}

@Composable
private fun UserCards(
    viewModel: HomeViewModel,
    goToPayEvent: (ByteArray) -> Unit
) {
    val cards = viewModel.cardList.observeAsState().value

    Column {

        Text(
            text = stringResource(id = R.string.your_cards),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Start
        )

        if (cards?.isNotEmpty() == true) {
            CardList(cards, goToPayEvent)
        } else {
            NoCardsAdded()
        }

    }
}

@Composable
private fun CardList(
    cards: List<Card>,
    goToPayEvent: (ByteArray) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = cards
        ) { card ->
            CardAdapter(card, goToPayEvent)
        }
    }
}

@Composable
private fun NoCardsAdded() {
    val constraintSet = ConstraintSet {
        val notCardsAdded = createRefFor("notCardsAdded")

        constrain(notCardsAdded) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    BoxWithConstraints {
        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.layoutId("notCardsAdded")
            ) {
                Image(
                    painterResource(
                        id = if (isSystemInDarkTheme()) {
                            R.drawable.blank_card_dark_mode
                        } else {
                            R.drawable.blank_card_light_mode
                        }
                    ),
                    contentDescription =
                    stringResource(id = R.string.card_image),
                    modifier =
                    Modifier.size(width = 200.dp, height = 150.dp)
                )

                Text(
                    text = stringResource(id = R.string.no_cards_added),
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }


    }
}

@Composable
private fun CardAdapter(
    card: Card,
    goToPayEvent: (ByteArray) -> Unit
) {
    val cardType = EncryptionUseCase.decrypt(card.type)
    val cardNumber = EncryptionUseCase.decrypt(card.number)

    ReusableCardItem(
        cardImage = when (cardType) {
            CardType.VISA.toString() -> R.drawable.visa
            CardType.AMERICAN_EXPRESS.toString() -> R.drawable.american_express
            CardType.MASTERCARD.toString() -> R.drawable.mastercard
            else -> if (isSystemInDarkTheme()) {
                R.drawable.blank_card_dark_mode
            } else {
                R.drawable.blank_card_light_mode
            }
        },
        cardHidedNumber = if (cardNumber.length % 4 == 0)
            "**** **** **** ${cardNumber.takeLast(4)}"
        else "**** ****** ${cardNumber.takeLast(5)}",
        onClick = {
            goToPayEvent(card.number)
        }
    )

}
