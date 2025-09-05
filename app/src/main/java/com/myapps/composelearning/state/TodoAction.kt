package com.myapps.composelearning.state

sealed interface TodoAction {
    data class OnTitleTextChange(val title: String) : TodoAction

    data class OnDescriptionTextChange(val description: String) : TodoAction

    data object OnAddNewItemClick : TodoAction

    data class OnCheckBoxClick(val itemId: Int, val isChecked: Boolean) : TodoAction

    data class OnDeleteButtonClick(val itemId: Int) : TodoAction
}