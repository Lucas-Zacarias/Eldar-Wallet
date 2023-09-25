package com.wallet.ui.signup

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.R
import com.wallet.domain.models.LoginResult
import com.wallet.domain.models.UserSignUp
import com.wallet.ui.reusablecomponents.HeightSpacer
import com.wallet.ui.reusablecomponents.ReusableButton
import com.wallet.ui.reusablecomponents.ReusableDialog
import com.wallet.ui.reusablecomponents.ReusableOutlineTextField
import com.wallet.ui.reusablecomponents.WidthSpacer

@Composable
fun SignUpScreen(
    goToLoginEvent: () -> Unit,
    goToHomeEvent: () -> Unit,
    viewModel: SignUpViewModel
) {
    val signUpState = viewModel.signUpState.observeAsState().value

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
            LogoApp()
            CreateAccountMessage()
            SignUpForm(goToLoginEvent, viewModel)
        }

        when (signUpState) {
            LoginResult.Success -> {
                goToHomeEvent()
            }

            LoginResult.EmptyFields -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.complete_all_fields),
                    show = true
                )
            }

            LoginResult.DistinctEmail -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.check_if_emails_match),
                    show = true
                )
            }

            LoginResult.DistinctPassword -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.check_if_passwords_match),
                    show = true
                )
            }

            LoginResult.EmailInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.email_format),
                    show = true
                )
            }

            LoginResult.PasswordInvalid -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.check_password_length),
                    show = true
                )
            }

            LoginResult.ExistingUser -> {
                ReusableDialog(
                    title =
                    stringResource(id = R.string.check),
                    text =
                    stringResource(id = R.string.try_with_other_email),
                    show = true
                )
            }

            else -> {}
        }
    }
}

@Composable
private fun LogoApp() {
    Image(
        painter =
        painterResource(id = R.drawable.wallet_logo),
        contentDescription =
        stringResource(R.string.app_logo),
        modifier =
        Modifier.size(250.dp, 250.dp)
    )
}

@Composable
private fun CreateAccountMessage() {
    HeightSpacer(height = 10)

    Text(
        text =
        stringResource(id = R.string.create_account),
        color =
        MaterialTheme.colorScheme.primary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SignUpForm(
    goToLoginEvent: () -> Unit,
    viewModel: SignUpViewModel
) {
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    HeightSpacer(height = 20)

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
        confirmEmail,
        onValueChange =
        { confirmEmail = it },
        label =
        stringResource(id = R.string.confirm_email),
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
            imeAction = ImeAction.Next
        ),
        keyboardActions =
        KeyboardActions(
            onNext =
            { focusManager.moveFocus(FocusDirection.Down) }
        ),
        maxLength = 6,
        isPasswordField = true,
        isPasswordVisible =
        passwordVisibility,
        onVisibilityChange =
        { passwordVisibility = it })

    HeightSpacer(height = 30)

    ReusableOutlineTextField(
        value =
        confirmPassword,
        onValueChange =
        { confirmPassword = it },
        label =
        stringResource(id = R.string.confirm_password),
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
        confirmPasswordVisibility,
        onVisibilityChange =
        { confirmPasswordVisibility = it })

    HeightSpacer(height = 30)

    ReusableButton(
        clickEvent = {
            viewModel.validateSignUpForm(
                UserSignUp(
                    name = name,
                    surname = surname,
                    email = email,
                    emailConfirm = confirmEmail,
                    password = password,
                    passwordConfirm = confirmPassword
                )
            )
        },
        text =
        stringResource(id = R.string.sign_up)
    )

    HeightSpacer(height = 20)

    LoginOption(goToLoginEvent)
}

@Composable
private fun LoginOption(
    goToLoginEvent: () -> Unit
) {
    Row(
        modifier =
        Modifier.fillMaxWidth(),
        horizontalArrangement =
        Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.have_account),
            color = MaterialTheme.colorScheme.primary
        )

        WidthSpacer(width = 15)

        Text(
            text = stringResource(id = R.string.log_in),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(
                onClick = {
                    goToLoginEvent()
                }
            )
        )
    }
}