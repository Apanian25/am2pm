package com.dawson.jonat.stockers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNote extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditNoteView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mEditNoteView = findViewById(R.id.edit_note);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditNoteView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}