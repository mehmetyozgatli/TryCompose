package com.myapps.composelearning.state

data class TodoListState(
    val items: List<TodoState> = emptyList()
)