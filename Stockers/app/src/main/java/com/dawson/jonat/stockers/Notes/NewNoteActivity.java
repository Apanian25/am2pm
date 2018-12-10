package com.dawson.jonat.stockers.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dawson.jonat.stockers.R;

/**
 * @author Nicholas Apanian
 * Most of the code in this class is based off of:
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#12
 */
public class NewNoteActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditNoteView;
    private Button saveButton, deleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mEditNoteView = findViewById(R.id.edit_note);

        saveButton = findViewById(R.id.button_save);
        deleteButton = findViewById(R.id.button_delete);

        final Intent intent = getIntent();

        if(intent.getExtras() == null) {
            loadNewNote();
        } else {
            loadEditNote(intent);
        }
    }

    /**
     * Loads tha page displaying the text from the note that was clicked on from the previous
     * Activity
     * @param intent
     */
    private void loadEditNote(final Intent intent) {
        mEditNoteView.setText(intent.getExtras().getString("note"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = mEditNoteView.getText().toString();
                    replyIntent.putExtra("id", intent.getExtras().getInt("id"));
                    replyIntent.putExtra(EXTRA_REPLY, note);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("id", intent.getExtras().getInt("id"));
                setResult(RESULT_CANCELED, intent);

                finish();
            }
        });

    }

    /**
     * Loads the page displaying nothing in the Text field and is ready to create a new
     * note
     */
    private void loadNewNote() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = mEditNoteView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, note);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        /*delete button should not exist, the user can press the back button on their
          device to have the app go back to the previous page*/
        deleteButton.setEnabled(false);
    }
}