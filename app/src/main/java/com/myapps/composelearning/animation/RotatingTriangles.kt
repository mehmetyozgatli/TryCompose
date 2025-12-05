package com.myapps.composelearning.animation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

@Composable
fun RotatingTriangles(width: Dp) {
    val transition = rememberInfiniteTransition(label = "infinite transition")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = EaseInOut
            ),
        ),
        label = "progress animation"
    )
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
    )

    colors.forEachIndexed { index, color ->
        Box(
            modifier = Modifier
                .graphicsLayer {
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 0.5f,
                        pivotFractionY = 2f / 3f
                    )
                    val progressOffset = index * 360f
                    rotationZ = (progress) * (360f + progressOffset)
                }
                .clip(TriangleShape)
                .width(width)
                .height(width * (sqrt(3f) / 2f))
                .background(color)
        )
    }
}

data object TriangleShape: Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                moveTo(size.width / 2f, 0f)
                lineTo(0f, size.height)
                lineTo(size.width, size.height)
                close()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RotatingTrianglesPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RotatingTriangles(width = 100.dp)
    }
}