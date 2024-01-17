package com.grp9.multinotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class EditNote extends AppCompatActivity {

    private Button noteBack;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteBack =  (Button) findViewById(R.id.button3);
        noteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        EditText editNotesText = (EditText) findViewById(R.id.editNotesText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editNotesText.setText(notes.notes.get(noteId));
        } else {
            notes.notes.add("");
            noteId = notes.notes.size() - 1;
            notes.arrayAdapter.notifyDataSetChanged();
        }

        editNotesText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notes.notes.set(noteId, String.valueOf(charSequence));
                notes.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.grp9.multinotes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(notes.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void openActivity(){
        Intent intent = new Intent(this, notes.class);
        startActivity(intent);
    }

}