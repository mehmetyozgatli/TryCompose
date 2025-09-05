package com.myapps.composelearning.state

data class TodoState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val isChecked: Boolean = false
)
