package com.myapps.composelearning.locals

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

val LocalSnackBarState = staticCompositionLocalOf {
    SnackbarHostState()
}

@Composable
fun CustomLocal() {
    val state = LocalSnackBarState.current
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = {
        SnackbarHost(state)
    }) { innerPadding ->
        Button(modifier = Modifier.padding(innerPadding), onClick = {
            scope.launch { state.showSnackbar("Hello") }
        }) {
            Text(text = "Click!")
        }
    }
}