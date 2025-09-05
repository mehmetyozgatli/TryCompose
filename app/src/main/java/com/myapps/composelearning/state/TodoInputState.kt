package com.myapps.composelearning.state

data class TodoInputState(
    val newItemTitle: String = "",
    val newItemDescription: String = ""
)