package com.myapps.composelearning.side_effects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SampleSideEffect(list: List<String>, modifier: Modifier) {
    val viewModel = viewModel<SampleSideEffectViewModel>()

    // LaunchedEffect, isScrolledToEnd değeri değiştiğinde tetiklenir.
//    val isScrolledToEnd by viewModel.isScrolledToEnd.collectAsState()
//    LaunchedEffect(isScrolledToEnd) {
//        if (isScrolledToEnd) {
//            // Eğer listenin sonuna gelindiyse, ViewModel'deki fonksiyonu çağır.
//            viewModel.showScrollToEndSnackbar()
//        }
//    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = viewModel.snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = viewModel.lazyListState,
                modifier = Modifier
                    .weight(1f)
            ) {
                items(list) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}