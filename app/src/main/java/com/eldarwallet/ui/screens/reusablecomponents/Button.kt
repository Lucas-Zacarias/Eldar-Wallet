package com.eldarwallet.ui.screens.reusablecomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReusableButton(
    clickEvent: () -> Unit,
    text: String,
    height: Int = 50
) {

    Button(
        onClick = { clickEvent() },
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.inversePrimary,
        ),
        modifier =
        Modifier
            .fillMaxWidth()
            .height(height.dp),
        shape =
        RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text
        )
    }

}