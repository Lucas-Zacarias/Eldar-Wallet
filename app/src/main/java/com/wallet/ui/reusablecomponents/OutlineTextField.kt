package com.wallet.ui.reusablecomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wallet.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLength: Int = 50,
    focusRequester: FocusRequester = FocusRequester(),
    onFocusChanged: () -> Unit = {}
) {
    var isNotFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue: String ->
            if (newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
        label = {
            Text(
                text = label,
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
        modifier =
        Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isNotFocused = !isNotFocused
                if(it.isFocused) {
                    onFocusChanged()
                }
            },
        trailingIcon = {
            if (isPasswordField) {
                IconButton(onClick =
                { onVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector =
                        if (isPasswordVisible) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        },
                        contentDescription =
                        stringResource(id = R.string.ic_visibility),
                        tint =
                        if (!isNotFocused)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        },
        visualTransformation =
        when {
            isPasswordField && isPasswordVisible -> VisualTransformation.None
            isPasswordField -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions =
        keyboardOptions,
        keyboardActions =
        keyboardActions,
        singleLine = true
    )

}