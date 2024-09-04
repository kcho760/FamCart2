package com.example.famcart2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Item(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isChecked: Boolean = false
)
