package com.dawson.jonat.stockers.Notes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import com.dawson.jonat.stockers.Entity.Note;
import com.dawson.jonat.stockers.R;

public class NoteActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        DividerItemDecoration horizontalLine = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        horizontalLine.setDrawable(getDrawable(R.drawable.horizontal_line));
        recyclerView.addItemDecoration(horizontalLine);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                // Update the cached copy of the words in the adapter.
                adapter.setNotes(notes);
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NewNoteActivity.EXTRA_REPLY));
            noteViewModel.insert(note);
        } else if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        } else if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            noteViewModel.updateNote(String.valueOf(data.getExtras().getInt("id")), data.getStringExtra(NewNoteActivity.EXTRA_REPLY));
        } else if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED && data != null){
            //The user has pressed the delete button when editing the note

            noteViewModel.deleteNote(data.getExtras().getInt("id"));
            Toast.makeText(
                    getApplicationContext(),
                    R.string.item_deleted,
                    Toast.LENGTH_LONG).show();
        }
    }


    public void createNote(View view) {
        Intent intent = new Intent(NoteActivity.this, NewNoteActivity.class);
        startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
    }
}
