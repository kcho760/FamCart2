package com.example.famcart2.data.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.famcart2.data.ui.viewmodel.ShoppingViewModel
import com.example.famcart2.data.model.Item
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = viewModel()
) {
    val items by viewModel.allItems.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Launcher to get the speech recognition result
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val recognizedSpeech = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recognizedSpeech?.let {
                if (it.isNotEmpty()) {
                    newItemName = it[0] // Set the recognized speech to the text field
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // TextField for entering new items
            TextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),  // Material height for text field
                singleLine = true,
                placeholder = { Text("Enter item") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle done action
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

//            // Button to add the item
//            Button(
//                onClick = {
//                    if (newItemName.isNotEmpty()) {
//                        viewModel.addItem(newItemName)
//                        newItemName = ""
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.tertiary,
//                    contentColor = MaterialTheme.colorScheme.onTertiary
//                )
//            ) {
//                Text("Add Item")
//            }
        }

        // Displaying the shopping list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)  // Ensures the LazyColumn takes up the remaining space
                .padding(horizontal = 16.dp)
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
                Spacer(modifier = Modifier.height(8.dp)) // Space between items
            }
        }

        // Button to add the item
        Button(
            onClick = {
                if (newItemName.isNotEmpty()) {
                    viewModel.addItem(newItemName)
                    newItemName = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text("Add Item")
        }
        // Button to activate speech-to-text input at the bottom of the screen
        IconButton(
            onClick = {
                // Request audio permission if not granted
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    0
                )

                // Start the speech recognizer
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                }

                speechLauncher.launch(intent)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)  // Centering the mic button horizontally
                .padding(16.dp)
                .size(56.dp)  // Size to match Material Design button size
                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Speak to add item"
            )
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
            .shadow(20.dp, shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .border(3.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            .padding(6.dp),
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
