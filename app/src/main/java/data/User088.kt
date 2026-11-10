package com.example.catatanharian088.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User088(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val username: String,

    val password: String

)

