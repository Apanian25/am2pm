package com.dawson.jonat.stockers.Interface;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dawson.jonat.stockers.Entity.Note;

import java.util.List;

/**
 * @author Nicholas Apanian
 * Most of the code in this class is based off of:
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4
 */
@Dao
public interface NoteDAO {

    @Insert
    void insertNote(Note note);

    @Query("DELETE FROM note_table WHERE id = :id")
    void deleteNote(int id);

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("UPDATE note_table set note = :note WHERE id = :id")
    void updateNote(int id, String note);
}
