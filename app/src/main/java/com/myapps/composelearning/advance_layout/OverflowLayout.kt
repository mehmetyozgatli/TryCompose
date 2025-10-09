package com.myapps.composelearning.advance_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach

@Composable
fun OverflowLayout(
    isOverflowing: Boolean,
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    overflowContent: @Composable () -> Unit
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val overflowPlaceables = if (isOverflowing) {
            val overflowMeasurables = subcompose("overflow_content", overflowContent)
            overflowMeasurables.map { it.measure(constraints) }
        } else emptyList()

        val mainMeasurables = subcompose("main_content", mainContent)
        val mainPlaceables = mainMeasurables.map {
            it.measure(constraints)
        }

        val maxMainPlaceableWidth = mainPlaceables.maxOfOrNull { it.width } ?: 0
        val maxMainPlaceableHeight = mainPlaceables.maxOfOrNull { it.height } ?: 0
        val maxOverflowPlaceableWidth = overflowPlaceables.maxOfOrNull { it.width } ?: 0
        val maxOverflowPlaceableHeight = overflowPlaceables.maxOfOrNull { it.height } ?: 0

        val width = maxOf(maxMainPlaceableWidth, maxOverflowPlaceableWidth)
        val height = maxMainPlaceableHeight + maxOverflowPlaceableHeight

        layout(width, height) {
            mainPlaceables.fastForEach { placeable ->
                placeable.place(0, 0)
            }
            overflowPlaceables.forEach { placeable ->
                placeable.place(0, maxMainPlaceableHeight)
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun OverflowLayoutPreview() {
    var isOverflowing by remember {
        mutableStateOf(false)
    }
    OverflowLayout(
        isOverflowing = isOverflowing,
        mainContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "This is a toggle section"
                )
                IconButton(
                    onClick = {
                        isOverflowing = !isOverflowing
                    }
                ) {
                    Icon(
                        imageVector = if (isOverflowing) {
                            Icons.Default.KeyboardArrowUp
                        } else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        },
        overflowContent = {
            Text(
                text = "Secret section",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
                    .padding(16.dp)
            )
        }
    )
}