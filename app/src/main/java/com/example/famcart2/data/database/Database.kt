package com.example.famcart2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.famcart2.data.dao.ItemDao
import com.example.famcart2.data.model.Item

@Database(entities = [Item::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
