package com.example.homework3.Database

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun alterNoteNotebook(id: Int, notebook: String) {
        noteDao.alterNoteNotebook(id, notebook)
    }

    suspend fun alterNoteTitle(id: Int, title: String) {
        noteDao.alterNoteTitle(id, title)
    }

    suspend fun alterNoteContent(id: Int, content: String) {
        noteDao.alterNoteContent(id, content)
    }

    suspend fun alterNoteDate(id: Int, date: String) {
        noteDao.alterNoteDate(id, date)
    }

    suspend fun flushTable() {
        noteDao.flushTable()
    }

}