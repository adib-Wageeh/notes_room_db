package com.maadcoding.notesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maadcoding.notesapp.database.Note
import com.maadcoding.notesapp.database.RoomDBHelper
import kotlinx.coroutines.launch

class NoteViewModel(app: Application) : AndroidViewModel(app) {

    private val db = RoomDBHelper.getInstance(app)

    val notes = db.dao.getNotes()

    fun upsertNote(note: Note) {
        viewModelScope.launch {
            db.dao.upsertNote(note)
        }
    }

    fun delete(n: Note) {
        viewModelScope.launch {
            db.dao.deleteNote(n)
        }
    }

}