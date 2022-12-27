package com.example.homework3.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun alterNoteNotebook(id: Int, notebook: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.alterNoteNotebook(id, notebook)
        }
    }

    fun alterNoteTitle(id: Int, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.alterNoteTitle(id, title)
        }
    }

    fun alterNoteContent(id: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.alterNoteContent(id, content)
        }
    }

    fun alterNoteDate(id: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.alterNoteDate(id, date)
        }
    }

    fun flushTable() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.flushTable()
        }
    }
}