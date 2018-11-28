package com.dawson.jonat.stockers;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.dawson.jonat.stockers.Abstract.NoteRoomDatabase;
import com.dawson.jonat.stockers.Entity.Note;
import com.dawson.jonat.stockers.Interface.NoteDAO;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    private LiveData<List<Note>> allNotes;

    NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDAO = db.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert (Note note) {
        new insertAsyncTask(noteDAO).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO mAsyncTaskDao;

        insertAsyncTask(NoteDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskDao.insertNote(params[0]);
            return null;
        }
    }
}
