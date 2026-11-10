package com.example.catatanharian088.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Note088::class,
        User088::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase088 : RoomDatabase() {

    abstract fun noteDao(): NoteDao088

    abstract fun userDao(): UserDao088

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase088? = null

        fun getDatabase(context: Context): AppDatabase088 {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase088::class.java,
                    "catatan_harian_088"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}

