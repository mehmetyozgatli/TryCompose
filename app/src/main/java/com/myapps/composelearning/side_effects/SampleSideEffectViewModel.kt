package com.myapps.composelearning.side_effects

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SampleSideEffectViewModel : ViewModel() {
    val lazyListState = LazyListState()
    val snackbarHostState = SnackbarHostState()
    val isScrolledToEnd = snapshotFlow {
        val layoutInfo = lazyListState.layoutInfo
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isEmpty()) {
            false // Eğer görünür öğe yoksa, sonda değiliz demektir.
        } else {
            // Son görünür öğenin indeksi, toplam öğe sayısının son indeksine eşit veya büyükse,
            // listenin sonundayız demektir.
            val lastVisibleItemIndex = visibleItemsInfo.last().index
            val totalItemsCount = layoutInfo.totalItemsCount
            lastVisibleItemIndex == totalItemsCount - 1
        }
    }
        .distinctUntilChanged() // Sadece sonuç değiştiğinde (true -> false veya false -> true) emit et
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    init {
        viewModelScope.launch {
            // isScrolledToEnd akışındaki her değişikliği dinle
            isScrolledToEnd.collect { scrolledToEnd ->
                // Eğer değer true ise (yani listenin sonuna gelindiyse)
                if (scrolledToEnd) {
                    // Snackbar'ı gösteren fonksiyonu çağır
                    showScrollToEndSnackbar()
                }
            }
        }
    }

    fun showScrollToEndSnackbar() {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = "Listenin sonuna ulaştınız!",
                actionLabel = "OK"
            )
        }
    }
}