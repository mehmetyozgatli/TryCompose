package com.myapps.composelearning.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapps.composelearning.ui.theme.ComposeLearningTheme

@Composable
fun TodoStateScreenRoot(modifier: Modifier = Modifier) {
    val viewModel = viewModel<TodoStateViewModel>()
    val listState by viewModel.listState.collectAsStateWithLifecycle()
    val inputState by viewModel.inputState.collectAsStateWithLifecycle()

    TodoStateScreen(
        listState = listState,
        inputState = inputState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun TodoStateScreen(
    listState: TodoListState,
    inputState: TodoInputState,
    onAction: (TodoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .padding(vertical = 2.dp)
        ) {
            items(
                items = listState.items,
                key = { item -> item.id }
            ) { todoItem ->
                TodoRowItem(state = todoItem, onAction = onAction)
            }
        }
        TodoInputSection(
            title = inputState.newItemTitle,
            onTitleChange = { onAction(TodoAction.OnTitleTextChange(it)) },
            description = inputState.newItemDescription,
            onDescriptionChange = { onAction(TodoAction.OnDescriptionTextChange(it)) },
            onAddItemClick = { onAction(TodoAction.OnAddNewItemClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun TodoRowItem(
    state: TodoState,
    onAction: (TodoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                state.title,
                textDecoration = if (state.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                state.description,
                textDecoration = if (state.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        IconButton(onClick = { onAction(TodoAction.OnDeleteButtonClick(itemId = state.id)) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete task",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
        Checkbox(checked = state.isChecked, onCheckedChange = { isChecked ->
            onAction(TodoAction.OnCheckBoxClick(itemId = state.id, isChecked = isChecked))
        })
    }
}

@Composable
fun TodoInputSection(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Yeni görev başlığı") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Açıklama (isteğe bağlı)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onAddItemClick,
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Yeni görev ekle")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NumberGuessScreenPreview() {
    ComposeLearningTheme {
        TodoStateScreen(
            listState = TodoListState(),
            inputState = TodoInputState(),
            onAction = {}
        )
    }
}