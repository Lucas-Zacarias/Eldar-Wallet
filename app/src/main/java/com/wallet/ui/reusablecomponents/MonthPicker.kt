package com.wallet.ui.reusablecomponents

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.wallet.R
import java.util.Calendar

@Composable
fun MonthPicker(
    visible: MutableState<Boolean>,
    confirmButtonCLicked: (String, String) -> Unit,
    moveFocusEvent: () -> Unit
) {

    val months = listOf(
        Pair(stringResource(id = R.string.january_short), "01"),
        Pair(stringResource(id = R.string.february_short), "02"),
        Pair(stringResource(id = R.string.march_short), "03"),
        Pair(stringResource(id = R.string.april_short), "04"),
        Pair(stringResource(id = R.string.may_short), "05"),
        Pair(stringResource(id = R.string.june_short), "06"),
        Pair(stringResource(id = R.string.july_short), "07"),
        Pair(stringResource(id = R.string.august_short), "08"),
        Pair(stringResource(id = R.string.september_short), "09"),
        Pair(stringResource(id = R.string.october_short), "10"),
        Pair(stringResource(id = R.string.november_short), "11"),
        Pair(stringResource(id = R.string.december_short), "12")
    )

    var month by remember {
        mutableStateOf(months[Calendar.getInstance().get(Calendar.MONTH) + 1].first)
    }

    var year by remember {
        mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR))
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (visible.value) {

        AlertDialog(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10)
                ),
            title = {

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmButtonCLicked(
                            months.find { it.first == month }!!.second,
                            year.toString()
                        )
                        moveFocusEvent()
                        visible.value = false
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
            dismissButton = {
                TextButton(
                    onClick = {
                        visible.value = false
                    }
                ) {
                    Text(
                        text =
                        stringResource(id = R.string.cancel),
                        color =
                        MaterialTheme.colorScheme.primaryContainer
                    )
                }
            },
            text = {

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = year.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }


                    Card(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                    ) {

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surface),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = {
                                                month = it.first
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    val animatedSize by animateDpAsState(
                                        targetValue = if (month == it.first) 60.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing
                                        ), label = ""
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(animatedSize)
                                            .background(
                                                color =
                                                if (month == it.first)
                                                    MaterialTheme.colorScheme.primaryContainer
                                                else
                                                    Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )

                                    Text(
                                        text = it.first,
                                        color =
                                        if (month == it.first)
                                            MaterialTheme.colorScheme.surface
                                        else
                                            MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )

                                }
                            }

                        }

                    }

                }

            },
            onDismissRequest = {
                visible.value = false
            }
        )

    }

}