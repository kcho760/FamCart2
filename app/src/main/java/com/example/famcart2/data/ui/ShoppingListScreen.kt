package com.example.famcart2.data.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.famcart2.data.ui.viewmodel.ShoppingViewModel
import com.example.famcart2.data.model.Item

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = viewModel()
) {
    val items by viewModel.allItems.collectAsState(initial = emptyList())

    Column(
        modifier = modifier.fillMaxSize(), // Use the passed-in modifier
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var newItemName by remember { mutableStateOf("") }

        // Input field to add a new item
        BasicTextField(
            value = newItemName,
            onValueChange = { newItemName = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true
        )

        // Button to add the item
        Button(
            onClick = {
                if (newItemName.isNotEmpty()) {
                    viewModel.addItem(newItemName)
                    newItemName = ""
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Item")
        }

        // LazyColumn to display the list of items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items) { item ->
                ShoppingListItem(
                    item = item,
                    onCheckedChange = { isChecked ->
                        viewModel.updateItem(item.copy(isChecked = isChecked))
                    },
                    onDelete = {
                        viewModel.deleteItem(item)
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    item: Item,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = item.name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }
    }
}
