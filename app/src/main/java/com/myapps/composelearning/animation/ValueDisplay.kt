package com.myapps.composelearning.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

@Composable
fun ValueDisplay(
    modifier: Modifier = Modifier,
    value: Float,
    maxValue: Float,
    unit: String,
    color: Color = Color.Red,
    strokeWidth: Dp = 10.dp
) {
    val animatedValue by animateFloatAsState(
        targetValue = value,
        label = "value animation",
        animationSpec = tween(durationMillis = 3000)
    )

    Box(
        modifier = modifier
            .drawBehind {
                drawArc(
                    color = color,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    ),
                    startAngle = -90f,
                    sweepAngle = (animatedValue / maxValue) * 360f,
                    useCenter = false
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (round(animatedValue * 10f) / 10f).toString() + unit,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ValueDisplayPreview() {
    var value by remember {
        mutableFloatStateOf(0f)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueDisplay(
            value = value,
            maxValue = 100f,
            unit = "%",
            modifier = Modifier
                .size(150.dp)
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { value = 10f }
            ) {
                Text("10%")
            }
            Button(
                onClick = { value = 50f }
            ) {
                Text("50%")
            }
            Button(
                onClick = { value = 90f }
            ) {
                Text("90%")
            }
        }
    }
}