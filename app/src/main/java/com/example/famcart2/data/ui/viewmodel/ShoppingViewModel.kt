package com.example.famcart2.data.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.famcart2.data.model.Item
import com.example.famcart2.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _allItems = MutableStateFlow<List<Item>>(emptyList())
    val allItems: StateFlow<List<Item>> = _allItems

    init {
        // Fetch items from the repository when ViewModel is created
        viewModelScope.launch {
            _allItems.value = repository.getAllItems() // Assume repository provides this function
        }
    }

    fun addItem(name: String) {
        val newItem = Item(name = name)
        _allItems.value = _allItems.value + newItem

        // Add item to the repository (optional)
        viewModelScope.launch {
            repository.insertItem(newItem)
        }
    }

    fun updateItem(updatedItem: Item) {
        _allItems.value = _allItems.value.map { if (it.id == updatedItem.id) updatedItem else it }

        // Update item in the repository (optional)
        viewModelScope.launch {
            repository.updateItem(updatedItem)
        }
    }

    fun deleteItem(item: Item) {
        _allItems.value = _allItems.value.filter { it.id != item.id }

        // Delete item from the repository (optional)
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    private fun areAllItemsSelected(): Boolean {
        return _allItems.value.all { it.isChecked }
    }

    // Modify the function to toggle between select and deselect all
    fun toggleSelectAllItems() {
        val shouldSelectAll = !areAllItemsSelected()
        _allItems.value = _allItems.value.map { it.copy(isChecked = shouldSelectAll) }
    }


    fun deleteSelectedItems() {
        val selectedItems = _allItems.value.filter { it.isChecked }

        // Update the in-memory list by removing selected items
        _allItems.value = _allItems.value.filterNot { it.isChecked }

        // Remove the selected items from the repository
        viewModelScope.launch {
            selectedItems.forEach { item ->
                repository.deleteItem(item)
            }
        }
    }

}
