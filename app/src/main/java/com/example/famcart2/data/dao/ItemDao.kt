package com.example.famcart2.data.dao

import androidx.room.*
import com.example.famcart2.data.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM shopping_items")
    fun getAllItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)
}
