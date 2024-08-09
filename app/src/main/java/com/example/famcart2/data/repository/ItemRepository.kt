package com.example.famcart2.data.repository

import com.example.famcart2.data.dao.ItemDao
import com.example.famcart2.data.model.Item

class ItemRepository(private val itemDao: ItemDao) {

    fun getAllItems(): List<Item> {
        return itemDao.getAllItems()
    }

    suspend fun insert(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun update(item: Item) {
        itemDao.updateItem(item)
    }

    suspend fun delete(item: Item) {
        itemDao.deleteItem(item)
    }
}
