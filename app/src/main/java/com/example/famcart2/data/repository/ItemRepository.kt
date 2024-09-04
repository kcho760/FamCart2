package com.example.famcart2.data.repository

import com.example.famcart2.data.dao.ItemDao
import com.example.famcart2.data.model.Item

class ItemRepository(private val itemDao: ItemDao) {

    suspend fun getAllItems() = itemDao.getAllItems()

    suspend fun insertItem(item: Item) = itemDao.insertItem(item)

    suspend fun updateItem(item: Item) = itemDao.updateItem(item)

    suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)
}
