package com.wallet.ui.reusablecomponents

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wallet.R
import com.wallet.ui.theme.WalletTheme

@Composable
fun ReusableDialog(
    title: String,
    text: String,
    show: Boolean,
    onConfirm: () -> Unit = {},
    ifConfirmOnlyToCloseDialog: Boolean = true,
    hasCancelOption: Boolean = false
) {
    var showAlertDialog by remember {
        mutableStateOf(show)
    }

    if (showAlertDialog) {
        AlertDialog(
            dismissButton = {

                if (hasCancelOption) {
                    TextButton(
                        onClick = {
                            showAlertDialog = false
                        }
                    ) {
                        Text(
                            text =
                            stringResource(id = R.string.cancel),
                            color =
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }

            },
            confirmButton =
            {
                TextButton(
                    onClick = {
                        if (ifConfirmOnlyToCloseDialog) {
                            showAlertDialog = false
                        } else {
                            onConfirm()
                        }
                    }
                ) {
                    Text(
                        text =
                        stringResource(id = R.string.ok),
                        color =
                        MaterialTheme.colorScheme.primaryContainer
                    )
                }
            },
            containerColor =
            MaterialTheme.colorScheme.inversePrimary,
            titleContentColor =
            MaterialTheme.colorScheme.primary,
            textContentColor =
            MaterialTheme.colorScheme.primary,
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = text
                )
            },
            shape = RoundedCornerShape(20.dp),
            onDismissRequest = {
                showAlertDialog = false
            }
        )
    }


}

@Preview(
    showBackground = true,
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun Preview() {
    WalletTheme {
        ReusableDialog("Hola Dialog", "Cuerpo del Dialog", true, {})
    }
}