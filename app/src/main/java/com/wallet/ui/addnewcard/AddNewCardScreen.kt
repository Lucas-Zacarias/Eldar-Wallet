package com.wallet.ui.addnewcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wallet.R
import com.wallet.domain.models.CardInput
import com.wallet.domain.models.CardType
import com.wallet.domain.models.NewCardResult
import com.wallet.ui.reusablecomponents.HeightSpacer
import com.wallet.ui.reusablecomponents.ReusableButton
import com.wallet.ui.reusablecomponents.ReusableDialog
import com.wallet.ui.reusablecomponents.ReusableOutlineTextField
import kotlinx.coroutines.delay

@Composable
fun AddNewCardScreen(viewModel: AddNewCardViewModel) {
    val addNewCardState = viewModel.addNewCardState.observeAsState().value
    val cardTypeState = viewModel.cardType.observeAsState().value

    Box(
        modifier =
        Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AddNewCardTitle()
            HeightSpacer(height = 20)
            CardImage(
                if(addNewCardState == NewCardResult.Success)
                    CardType.NOT_DEFINED
                else cardTypeState
            )
            HeightSpacer(height = 30)
            AddNewCardForm(
                viewModel,
                addNewCardState,
                cardTypeState
            )

        }
        
        when(addNewCardState) {
            NewCardResult.Success -> {
                NewCardAdded(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            NewCardResult.EmptyFields -> {
                ReusableDialog(
                    title = 
                    stringResource(id = R.string.check),
                    text = 
                    stringResource(id = R.string.complete_all_fields)
                )
            }

            NewCardResult.OwnerNameInvalid, NewCardResult.OwnerInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.add_card_in_your_name)
                )
            }

            NewCardResult.NumberInvalid, NewCardResult.CardTypeInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.use_a_valid_card_number)
                )
            }

            NewCardResult.MonthInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.not_use_an_expiration_month_before_the_current_date)
                )
            }

            NewCardResult.YearInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.not_use_an_expiration_year_before_the_current_date)
                )
            }

            NewCardResult.CVCLengthInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.use_a_valid_cvc)
                )
            }

            NewCardResult.CardAlreadyAdded -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.card_already_added)
                )
            }

            NewCardResult.Error  -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.problem_not_detected)
                )
            }

            else -> {}
        }

    }
}

@Composable
private fun AddNewCardTitle() {
    Text(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        text =
        stringResource(id = R.string.complete_to_add_new_card),
        color =
        MaterialTheme.colorScheme.primary,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CardImage(cardTypeState: CardType?) {
    Image(
        painter =
        painterResource(
            id =
            when (cardTypeState) {
                CardType.VISA -> R.drawable.visa

                CardType.MASTERCARD -> R.drawable.mastercard

                CardType.AMERICAN_EXPRESS -> R.drawable.american_express

                else -> if (isSystemInDarkTheme()) {
                    R.drawable.blank_card_dark_mode
                } else {
                    R.drawable.blank_card_light_mode
                }
            }
        ),
        contentDescription =
        stringResource(id = R.string.card_image),
        modifier =
        Modifier.size(width = 200.dp, height = 150.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNewCardForm(
    viewModel: AddNewCardViewModel,
    addNewCardState: NewCardResult?,
    cardTypeState: CardType?
) {
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var expirationMonth by remember { mutableStateOf("") }
    var expirationYear by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cvcVisibility by remember { mutableStateOf(false) }
    var cardType by remember { mutableStateOf(CardType.NOT_DEFINED) }
    cardType = cardTypeState ?: CardType.NOT_DEFINED

    ReusableOutlineTextField(
        value =
        name,
        onValueChange =
        { name = it },
        label =
        stringResource(id = R.string.name),
        keyboardOptions =
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions =
        KeyboardActions(
            onNext =
            { focusManager.moveFocus(FocusDirection.Down) }
        ))

    HeightSpacer(height = 30)

    ReusableOutlineTextField(
        value =
        surname,
        onValueChange =
        { surname = it },
        label =
        stringResource(id = R.string.surname),
        keyboardOptions =
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions =
        KeyboardActions(
            onNext =
            { focusManager.moveFocus(FocusDirection.Down) }
        ))

    HeightSpacer(height = 30)

    ReusableOutlineTextField(
        value =
        number,
        onValueChange =
        {
            number = it
            viewModel.setCardType(it)
        },
        label =
        stringResource(id = R.string.number),
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions =
        KeyboardActions(
            onNext =
            { focusManager.moveFocus(FocusDirection.Down) }
        ),
        maxLength = 16)

    HeightSpacer(height = 30)

    Row(
        modifier =
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = expirationMonth,
            onValueChange = { newValue: String ->
                if (newValue.length <= 2) {
                    expirationMonth = newValue
                }
            },
            label = {
                Text(
                    text = stringResource(id = R.string.expiration_month),
                    fontWeight = FontWeight.Bold
                )
            },
            shape =
            RoundedCornerShape(10.dp),
            colors =
            TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions =
            KeyboardActions(
                onNext =
                { focusManager.moveFocus(FocusDirection.Next) }
            ),
            singleLine = true,
            modifier = Modifier.width(150.dp)
        )

        OutlinedTextField(
            value = expirationYear,
            onValueChange = { newValue: String ->
                if (newValue.length <= 4) {
                    expirationYear = newValue
                }
            },
            label = {
                Text(
                    text = stringResource(id = R.string.expiration_year),
                    fontWeight = FontWeight.Bold
                )
            },
            shape =
            RoundedCornerShape(10.dp),
            colors =
            TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions =
            KeyboardActions(
                onNext =
                { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
            modifier = Modifier.width(150.dp)
        )
    }
    
    HeightSpacer(height = 30)

    ReusableOutlineTextField(
        value =
        cvc,
        onValueChange =
        { cvc = it },
        label =
        stringResource(id = R.string.cvc),
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions =
        KeyboardActions(
            onDone =
            { focusManager.clearFocus() }
        ),
        maxLength = 4,
        isPasswordField = true,
        isPasswordVisible =
        cvcVisibility,
        onVisibilityChange =
        { cvcVisibility = it })

    HeightSpacer(height = 30)

    ReusableButton(
        clickEvent = {
            viewModel.validateAddNewCard(
                CardInput(
                    name = name,
                    surname = surname,
                    number = number,
                    month = expirationMonth,
                    year = expirationYear,
                    cvc = cvc,
                    type = cardType
                )
            )
        },
        text =
        stringResource(id = R.string.add)
    )
    
    HeightSpacer(height = 20)

    if(addNewCardState == NewCardResult.Success) {
        name = ""
        surname = ""
        number = ""
        expirationMonth = ""
        expirationYear = ""
        cvc = ""
        cvcVisibility = false
        cardType = CardType.NOT_DEFINED
    }
}

@Composable
private fun NewCardAdded(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.new_card_added))
    var showNewCardAdded by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit){
        delay(2000)
        showNewCardAdded = false
    }

    if(showNewCardAdded) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
        )
    }
}