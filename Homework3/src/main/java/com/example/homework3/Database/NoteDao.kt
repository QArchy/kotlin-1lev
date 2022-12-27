package com.example.homework3.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY date DESC")
    fun readAllData(): LiveData<List<Note>>

    @Query("UPDATE note_table SET notebook=:notebook WHERE id=:id")
    suspend fun alterNoteNotebook(id: Int, notebook: String)

    @Query("UPDATE note_table SET title=:title WHERE id=:id")
    suspend fun alterNoteTitle(id: Int, title: String)

    @Query("UPDATE note_table SET content=:content WHERE id=:id")
    suspend fun alterNoteContent(id: Int, content: String)

    @Query("UPDATE note_table SET date=:date WHERE id=:id")
    suspend fun alterNoteDate(id: Int, date: String)

    @Query("DELETE FROM note_table")
    suspend fun flushTable()
}