package com.myapps.composelearning.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoStateViewModel : ViewModel() {
    private val _listState = MutableStateFlow(TodoListState())
    val listState = _listState.asStateFlow()

    private val _inputState = MutableStateFlow(TodoInputState())
    val inputState = _inputState.asStateFlow()

    init {
        loadInitialItems()
    }

    private fun loadInitialItems() {
        viewModelScope.launch {
            val sampleItems = List(20) { index ->
                TodoState(
                    id = index,
                    title = "Title $index",
                    description = "Description",
                    isChecked = false
                )
            }
            _listState.update { it.copy(items = sampleItems) }
        }
    }

    fun onAction(action: TodoAction) {
        when (action) {
            is TodoAction.OnCheckBoxClick -> {
                _listState.update { currentState ->
                    val updatedItems = currentState.items.map { item ->
                        if (item.id == action.itemId) {
                            item.copy(isChecked = action.isChecked)
                        } else {
                            item
                        }
                    }
                    currentState.copy(items = updatedItems)
                }
            }

            is TodoAction.OnDeleteButtonClick -> {
                _listState.update { currentState ->
                    val willBeDeletedItem = currentState.items.find { it.id == action.itemId }
                    currentState.copy(items = currentState.items.filter { it != willBeDeletedItem })
                }
            }

            is TodoAction.OnTitleTextChange -> {
                _inputState.update { currentState ->
                    currentState.copy(newItemTitle = action.title)
                }
            }

            is TodoAction.OnDescriptionTextChange -> {
                _inputState.update { currentState ->
                    currentState.copy(newItemDescription = action.description)
                }
            }

            is TodoAction.OnAddNewItemClick -> {
                if (_inputState.value.newItemTitle.isNotBlank()) {
                    val newItem = TodoState(
                        id = _listState.value.items.size + 1,
                        title = _inputState.value.newItemTitle,
                        description = _inputState.value.newItemDescription,
                        isChecked = false
                    )
                    _listState.update { currentState ->
                        currentState.copy(
                            items = currentState.items + newItem
                        )
                    }
                    _inputState.update { currentState ->
                        currentState.copy(
                            newItemTitle = "",
                            newItemDescription = ""
                        )
                    }
                }
            }
        }
    }
}