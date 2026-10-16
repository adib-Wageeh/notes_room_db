package com.maadcoding.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class RoomDBHelper : RoomDatabase() {

    abstract val dao: NoteDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDBHelper? = null

        fun getInstance(c: Context): RoomDBHelper {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = c,
                    RoomDBHelper::class.java,
                    "note_db"
                ).build()
                INSTANCE = instance
                instance
            }
            // create a new object


        }
    }
}