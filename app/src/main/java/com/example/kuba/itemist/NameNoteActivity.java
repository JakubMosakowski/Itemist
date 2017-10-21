package com.example.kuba.itemist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


public class NameNoteActivity extends AppCompatActivity {
    EditText editTextNameOfNote;
    Button buttonNameOfNote;
    Toolbar toolbar;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_note);
        editTextNameOfNote = (EditText) findViewById(R.id.editText_subpoint_of_the_list);
        buttonNameOfNote = (Button) findViewById(R.id.button_name_of_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = getApplicationContext();
        editTextNameOfNote.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    enterNote(v);
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void enterNote(View v) {
        String nameOfNote = editTextNameOfNote.getText().toString();

        if (!nameOfNote.equals("")) {
            nameOfNote= changeNameOfNote(nameOfNote);
            Intent intent = new Intent(NameNoteActivity.this, AddNoteActivity.class);
            intent.putExtra("location", nameOfNote);
            NameNoteActivity.this.startActivity(intent);

        } else {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.error_no_name), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected String changeNameOfNote(String title) {
        DataHandler data = new DataHandler(context);
        String[] notes = data.getArrayWithNotes();

            for (int i = 0; i < notes.length; i++) {
                if (notes[i]!=null) {
                    if (notes[i].equals(title)) {
                        title = title + "(2)";
                        i = 0;
                    }
                }
            }


        return title;
    }

}
