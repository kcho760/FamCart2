package com.example.famcart2.data.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.famcart2.data.model.Item
import com.example.famcart2.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _allItems = MutableStateFlow<List<Item>>(emptyList())
    val allItems: StateFlow<List<Item>> = _allItems

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _allItems.value = repository.getAllItems()
            }
        }
    }

    fun addItem(name: String) {
        val newItem = Item(name = name)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insert(newItem)
                _allItems.value = repository.getAllItems() // Reload items after adding
            }
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.update(item)
                _allItems.value = repository.getAllItems() // Reload items after updating
            }
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.delete(item)
                _allItems.value = repository.getAllItems() // Reload items after deleting
            }
        }
    }
}
