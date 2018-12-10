package com.dawson.jonat.stockers.Notes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.dawson.jonat.stockers.Abstract.NoteRoomDatabase;
import com.dawson.jonat.stockers.Entity.Note;
import com.dawson.jonat.stockers.Interface.NoteDAO;

import java.util.List;

/**
 * @author Nicholas Apanian
 * Most of the code in this class is based off of:
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
 */
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

    public void updateNote(String id, String note){
        new updateAsyncTask(noteDAO).execute(id, note);
    }

    public void deleteNote(Integer id) {
        new deleteAsyncTask(noteDAO).execute(id);
    }

    /**
     * This class is used to insert a note in the database in an asynchronous matter.
     */
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

    /**
     * This class is used to update the note in an asynchronous matter.
     */
    private static class updateAsyncTask extends AsyncTask<String, Void, Void> {

        private NoteDAO mAsyncTaskDao;

        updateAsyncTask(NoteDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... values) {
            mAsyncTaskDao.updateNote(Integer.valueOf(values[0]), values[1]);
            return null;
        }
    }


    /**
     * This class is used to delete the note in an asynchronous matter.
     */
    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private NoteDAO mAsyncTaskDao;

        deleteAsyncTask(NoteDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... values) {
            mAsyncTaskDao.deleteNote(values[0]);
            return null;
        }
    }
}
