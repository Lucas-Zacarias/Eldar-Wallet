package com.wallet.ui.qr

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.R

@Composable
fun QRScreen(
    generateQRViewModel: GenerateQRViewModel
) {
    val qrState = generateQRViewModel.qrBitmap.observeAsState().value

    Box(
        modifier =
        Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {

            if(qrState != null) {
                GeneratedQR(qrState)
            }else {
                LoadingQR()
            }

        }

        InstructionalMessage(
            Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun LoadingQR() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 5.dp
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(id = R.string.charging_your_qr),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun GeneratedQR(qrImage: Bitmap) {
    Box(
        modifier =
        Modifier
            .size(300.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            ),
       contentAlignment = Alignment.Center
    ) {

        Image(
            bitmap = qrImage.asImageBitmap(),
            contentDescription =
            stringResource(id = R.string.qr_code),
            modifier =
            Modifier
                .size(250.dp)
                .fillMaxSize()
        )

    }
}

@Composable
private fun InstructionalMessage(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        text =
        stringResource(id = R.string.show_qr_code_to_receive_money),
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}
