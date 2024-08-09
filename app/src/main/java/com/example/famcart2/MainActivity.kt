package com.example.famcart2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.famcart2.data.database.ShoppingDatabase
import com.example.famcart2.data.repository.ItemRepository
import com.example.famcart2.data.ui.ShoppingListScreen
import com.example.famcart2.data.ui.viewmodel.ShoppingViewModel
import com.example.famcart2.data.ui.viewmodel.ShoppingViewModelFactory
import com.example.famcart2.ui.theme.FamCart2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the repository
        val database = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java,
            "shopping_db"
        ).build()
        val repository = ItemRepository(database.itemDao())

        // Create an instance of the ViewModelFactory
        val factory = ShoppingViewModelFactory(repository)

        // Get the ViewModel using the factory
        val shoppingViewModel: ShoppingViewModel by viewModels { factory }

        setContent {
            FamCart2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingListScreen(
                        viewModel = shoppingViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    FamCart2Theme {
        ShoppingListScreen(
            viewModel = viewModel()
        )
    }
}
