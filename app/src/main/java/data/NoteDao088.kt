package com.example.catatanharian088.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao088 {

    @Insert
    suspend fun insertNote(note: Note088)

    @Update
    suspend fun updateNote(note: Note088)

    @Delete
    suspend fun deleteNote(note: Note088)

    @Query("SELECT * FROM notes WHERE username = :username ORDER BY id DESC")
    fun getNotesByUser(username: String): Flow<List<Note088>>

    @Query("SELECT * FROM notes WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: Int): Note088?
}

