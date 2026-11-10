package com.example.catatanharian088.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note088(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val username: String,

    val title: String,

    val content: String,

    val date: String
)