package com.eldarwallet.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eldarwallet.R
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserLogIn
import com.eldarwallet.ui.reusablecomponents.HeightSpacer
import com.eldarwallet.ui.reusablecomponents.ReusableButton
import com.eldarwallet.ui.reusablecomponents.ReusableDialog
import com.eldarwallet.ui.reusablecomponents.ReusableOutlineTextField
import com.eldarwallet.ui.reusablecomponents.WidthSpacer

@Composable
fun LoginScreen(
    signUpEvent: () -> Unit,
    goToHomeEvent: () -> Unit,
    viewModel: LogInViewModel
) {
    val loginState = viewModel.logInState.observeAsState().value

    Box(
        modifier =
        Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoApp()
            WelcomeGreeting()
            LoginForm(signUpEvent, viewModel)
        }

        when(loginState) {
            LoginResult.Success -> {
                goToHomeEvent()
            }

            LoginResult.EmptyFields -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.complete_all_fields),
                    show = true)
            }
            LoginResult.EmailInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.email_format),
                    show = true)
            }
            LoginResult.UserNotFound -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.credentials),
                    show = true)
            }
            else -> {}
        }
    }
}

@Composable
private fun LogoApp() {
    Image(
        painter =
        painterResource(id = R.drawable.eldar_logo_app),
        contentDescription =
        stringResource(R.string.app_logo),
        modifier =
        Modifier.size(250.dp, 250.dp)
    )
}

@Composable
private fun WelcomeGreeting() {
    HeightSpacer(height = 10)

    Text(
        text =
        stringResource(id = R.string.welcome_greeting),
        color =
        MaterialTheme.colorScheme.primary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun LoginForm(
    signUpEvent: () -> Unit,
    logInViewModel: LogInViewModel
) {
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    HeightSpacer(height = 20)

    ReusableOutlineTextField(
        value =
        email,
        onValueChange =
        { email = it },
        label =
        stringResource(id = R.string.email),
        keyboardOptions =
        KeyboardOptions(
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
        password,
        onValueChange =
        { password = it },
        label =
        stringResource(id = R.string.password),
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions =
        KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        maxLength = 6,
        isPasswordField = true,
        isPasswordVisible =
        passwordVisibility,
        onVisibilityChange =
        { passwordVisibility = it })

    HeightSpacer(height = 30)

    ReusableButton(
        clickEvent = {
            logInViewModel.validateLogIn(
                UserLogIn(
                    email = email,
                    password = password
                )
            )
        },
        text =
        stringResource(id = R.string.log_in)
    )

    HeightSpacer(height = 20)

    SignUpOption(signUpEvent)
}

@Composable
private fun SignUpOption(signUpEvent: () -> Unit) {
    Row(
        modifier =
        Modifier.fillMaxWidth(),
        horizontalArrangement =
        Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.not_have_account),
            color = MaterialTheme.colorScheme.primary
        )

        WidthSpacer(width = 15)

        Text(
            text = stringResource(id = R.string.sign_up),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(
                onClick =
                { signUpEvent() }
            )
        )

    }
}