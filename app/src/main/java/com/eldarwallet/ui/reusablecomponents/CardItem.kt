package com.eldarwallet.ui.reusablecomponents

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eldarwallet.R
import com.eldarwallet.ui.theme.EldarWalletTheme

@Composable
fun ReusableCardItem(
    cardImage: Int,
    cardHidedNumber: String,
    onClick: () -> Unit
) {
    Card(
        modifier =
        Modifier
            .wrapContentSize()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation =
        CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter =
                painterResource(cardImage),
                contentDescription =
                stringResource(R.string.card_image),
                modifier =
                Modifier.size(width = 200.dp, height = 150.dp)
            )

            Text(
                text = cardHidedNumber,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }


    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light mode"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Night mode"
)
@Composable
fun PreviewCardItem() {
    EldarWalletTheme {
        Surface {
            ReusableCardItem(
                R.drawable.visa,
                stringResource(id = R.string.example_hided_card_number),
                onClick = {})
        }
    }
}