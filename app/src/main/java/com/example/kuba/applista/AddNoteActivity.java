package com.example.kuba.applista;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


public class AddNoteActivity extends AppCompatActivity {
    EditText editTextNameOfNote;
    Button buttonNameOfNote;
    EditText editTextSubpointOfTheList;
    Button buttonAccept;
    TextView list;
    int howManySubpoints=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        list = (TextView) findViewById(R.id.TextViewSubpoints);
        editTextNameOfNote = (EditText) findViewById(R.id.editText_add_name_of_note);
        buttonNameOfNote = (Button) findViewById(R.id.button_name_of_note);
        editTextSubpointOfTheList = (EditText) findViewById(R.id.editText_subpoint_of_the_list);
        buttonAccept = (Button) findViewById(R.id.button_accept_subpoint);
        list.setMovementMethod(new ScrollingMovementMethod());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        editTextNameOfNote.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    enterNote(v);
                }
                return false;
            }
        });
        editTextSubpointOfTheList.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    buttonAcceptOnClick(v);
                }
                return true;
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.you_sure_you_want_to_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }


    protected boolean setTitleOfToolbar(View v) {
        if (editTextNameOfNote == null) {
            Toast toast = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            ;
            toast.show();
            return false;
        } else {
            String nameOfNote = editTextNameOfNote.getText().toString();
            Toolbar toolbar = ToolbarMenagment.getToolbarView(this);
            if (!nameOfNote.equals("")) {
                toolbar.setTitle(nameOfNote);
                Toast.makeText(getApplicationContext(), R.string.note_added, Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast toast = Toast.makeText(this, getResources().getString(R.string.error_no_name), Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }
    }

    protected void enterNote(View v) {
        if (setTitleOfToolbar(v)) {
            hideEditTextNameOfNote();
            hideButtonNameOfNote();
            showSubpointOfTheList();
            showButtonAccept();
        }

    }

    protected void hideEditTextNameOfNote() {
        editTextNameOfNote.setVisibility(View.INVISIBLE);
    }

    protected void hideButtonNameOfNote() {
        buttonNameOfNote.setVisibility(View.INVISIBLE);
    }

    protected void showSubpointOfTheList() {
        editTextSubpointOfTheList.setVisibility(View.VISIBLE);
    }

    protected void showButtonAccept() {
        buttonAccept.setVisibility(View.VISIBLE);
    }

    protected void buttonAcceptOnClick(View v) {

        String subpoint = editTextSubpointOfTheList.getText().toString();
        howManySubpoints++;
        //TODO usun to jak będzie działać


        if (!subpoint.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.subpoint_added, Toast.LENGTH_LONG).show();
            addSubpoint(v, subpoint);
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_no_name, Toast.LENGTH_LONG).show();
        }
    }

    protected void addSubpoint(View v, String subpoint) {
        String contentOfList = list.getText().toString();
        editTextSubpointOfTheList.setText("");
        list.setVisibility(View.VISIBLE);
        list.setText(contentOfList + "\n" + subpoint);
    }

}
