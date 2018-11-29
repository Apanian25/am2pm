package com.dawson.jonat.stockers;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.dawson.jonat.stockers.Entity.Note;

import java.util.List;

/**
 *
 * @author Nicholas Apanian
 *
 * Most of the code in this class is based off of:
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#8
 */
public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    /**
     * Set the private variables for the class
     *
     * @param application
     */
    public NoteViewModel(Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    /**
     * Gets all the Notes
     *
     * @return LiveData<List<Note>>
     */
    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * Wrapper insert method to hide the
     * original implementation from the UI
     *
     * @param note
     */
    public void insert(Note note) {
        noteRepository.insert(note);
    }


    public void updateNote(String id, String note){
        noteRepository.updateNote(id, note);
    }
}


