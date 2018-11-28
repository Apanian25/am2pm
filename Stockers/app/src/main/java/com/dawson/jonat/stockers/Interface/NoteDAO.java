package com.dawson.jonat.stockers.Interface;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dawson.jonat.stockers.Entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNote(Note note);

    @Query("DELETE FROM note_Table WHERE id = :id")
    void deleteNote(int id);

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();
}
