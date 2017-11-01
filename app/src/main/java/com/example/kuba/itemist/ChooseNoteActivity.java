package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseNoteActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private Context context;
    private ListView list;
    private Toolbar toolbar;
    private View v;
    private ImageButton imgButton;
    private String[] subpoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_note);
        imgButton = (ImageButton) findViewById(R.id.plus_button);
        context = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        list = (ListView) findViewById(R.id.listView);
        v = findViewById(R.id.activity_choose_note);
        setToolbar();
        try {
            enterNotesToListView();
        } catch (Exception e) {

        }


    }

    public void enterNotesToListView() {
        DataHandler data = new DataHandler(context);

        ArrayList<String> notes = new ArrayList<String>();
        String[] notesArray = data.getArrayWithNotes();
        int count = notesArray.length;

        for (int i = 0; i < count; i++) {
             /*====== this is where listView is populated =====*/
            if (notesArray[i] != null) {
                notes.add(notesArray[i]);
            }
        }
        setAdapter(notes);
    }

    public void setAdapter(ArrayList<String> notes) {
        adapter = new ArrayAdapter<String>(this, R.layout.row_for_notes_names, notes);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                dialogDeleteEdit(position);
                return true;
            }

        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToNote(position);
            }
        });
    }

    public void setToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void goToNote(int position) {
        Intent intent = new Intent(ChooseNoteActivity.this, NoteActivity.class);
        intent.putExtra("location", adapter.getItem(position));
        ChooseNoteActivity.this.startActivity(intent);
    }

    protected void deleteNote(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
                updateDataDelete(position);
                adapter.remove(adapter.getItem(position));
                list.setAdapter(adapter);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }

    protected void updateDataDelete(int position) {
        String noteName = adapter.getItem(position);
        DataHandler data = new DataHandler(noteName,context);
        data.deleteNote(noteName);
    }

    protected void updateDataEdit(int position) {
        String noteName = adapter.getItem(position);
        DataHandler data = new DataHandler(noteName, context);
        int len = adapter.getCount();

        String[] notes = new String[len];
        data.setStringWithSubpointsArray(subpoints);
        data.replaceFileWithSubpoints(subpoints);
        for (int i = 0; i < len; i++)
            notes[i] = adapter.getItem(i);
        data.setStringWithNotesArray(notes);
        data.replaceFileWithNotes(notes);

    }

    protected void editNote(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(ChooseNoteActivity.this);
        alert.setTitle(getResources().getString(R.string.edit));

        alert.setView(edittext);
        edittext.setText(adapter.getItem(position));
        edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edittext.setHint(adapter.getItem(position));
        alert.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                boolean sameNameNoteExists = false;
                for (int i = 0; i < adapter.getCount(); i++)
                    if (adapter.getItem(i).equals(edittext.getText().toString()))
                        sameNameNoteExists = true;

                if (!edittext.getText().toString().isEmpty() && sameNameNoteExists == false) {
                    updateDataDelete(position);
                    adapter.remove(adapter.getItem(position));
                    adapter.insert(edittext.getText().toString(), position);
                    updateDataEdit(position);
                    list.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), R.string.edited, Toast.LENGTH_SHORT).show();
                } else if (edittext.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.there_is_note_with_that_name, Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel), null);
        alert.show();
    }

    protected void dialogDeleteEdit(final int position) {
        CharSequence options[] = new CharSequence[]{
                getResources().getString(R.string.delete),
                getResources().getString(R.string.edit),
                getResources().getString(R.string.do_nothing)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.do_you_want_to_do_smth_with_this_note));
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.you_have_chosen) + adapter.getItem(position), Toast.LENGTH_LONG).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    deleteNote(position);
                } else if (which == 1) {
                    editNote(position);
                }

            }
        });
        builder.show();
    }
}
