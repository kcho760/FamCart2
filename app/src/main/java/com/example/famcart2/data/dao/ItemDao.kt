package com.example.famcart2.data.dao

import androidx.room.*
import com.example.famcart2.data.model.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item")
    suspend fun getAllItems(): List<Item>

    @Insert
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}
