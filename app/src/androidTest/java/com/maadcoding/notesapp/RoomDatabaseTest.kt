package com.maadcoding.notesapp

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maadcoding.notesapp.database.Note
import com.maadcoding.notesapp.database.NoteDao
import com.maadcoding.notesapp.database.RoomDBHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {

    private lateinit var dao: NoteDao
    private lateinit var db: RoomDBHelper

    @Before
    fun setup() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RoomDBHelper::class.java,
        )
            .setDriver(AndroidSQLiteDriver())
            .build()
        dao = db.dao
    }

    @Test
    fun insert_SingleNote() {
        runTest {
            // arrange
            val n = Note(
                id = 1,
                content = "test content"
            )

            // act
            dao.upsertNote(n)

            // assert
            val note: Note = dao.getNotes().first().first()
            Assert.assertNotNull(note)
            assertEquals(n.id, note.id)
            assertEquals(n.content, note.content)
        }
    }

    @Test
    fun getNotes_ContainNotes() {
        runTest {
            // arrange
            val arrangedNotes = listOf(
                Note(
                    id = 1,
                    content = "test content"
                ), Note(
                    id = 2,
                    content = "test content 2"
                )
            )

            arrangedNotes.forEach {
                dao.upsertNote(it)
            }

            // act
            val notes = dao.getNotes().first()

            // assert
            assertEquals(2, notes.size)
            assertEquals(arrangedNotes, notes)
        }
    }

    @Test
    fun selectTest_NoNotes(){

        runTest {
            //act
            val notes = dao.getNotes().first()

            //assert
            assertTrue(notes.isEmpty())
        }
    }

    @After
    fun closeDb() {
        db.close()
    }

}