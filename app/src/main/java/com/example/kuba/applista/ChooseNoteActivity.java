package com.example.kuba.applista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseNoteActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private Context context;
    private ListView list;
    private Toolbar toolbar;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_note);

        context = getApplicationContext();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        list = (ListView) findViewById(R.id.listView);
        v=findViewById(R.id.activity_choose_note);
        try{
            enterNotesToListView();
        }
        catch(Exception e){
            Log.e("TAG","CRASHw onCreate StackTrace: "+ Arrays.toString(e.getStackTrace()));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void enterNotesToListView(){
        DataHandler data=new DataHandler(context);

        ArrayList<String> notes = new ArrayList<String>();
        String [] notesArray=data.getArrayWithNotes();
        int count = notesArray.length;
        Log.d("TAG", String.valueOf(count));

        for (int i = 0; i < count; i++) {
             /*====== this is where listView is populated =====*/
            if(notesArray[i] != null) {
                notes.add(notesArray[i]);
            }
        }
        Toast.makeText(context, Arrays.toString(data.getArrayWithNotes()), Toast.LENGTH_SHORT).show();
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
    public void goToNote(int position){
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
                updateData(position);
                adapter.remove(adapter.getItem(position));
                list.setAdapter(adapter);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }
    protected void updateData(int position){
        String noteName=adapter.getItem(position);
        DataHandler data=new DataHandler(noteName,context);
        Toast.makeText(context, noteName, Toast.LENGTH_SHORT).show();
        data.deleteNote(noteName);
    }
    protected void editSubpoint(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(ChooseNoteActivity.this);
        alert.setTitle(getResources().getString(R.string.edit));

        alert.setView(edittext);
        edittext.setText("");
        edittext.setHint(adapter.getItem(position));
        alert.setPositiveButton(getResources().getString(R.string.confirme_edit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!edittext.getText().toString().isEmpty()) {
                    adapter.remove(adapter.getItem(position));
                    adapter.insert(edittext.getText().toString(),position);
                    updateData(position);

                    list.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), R.string.edited, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                }
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

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    deleteNote(position);
                } else if (which == 1) {
                    editSubpoint(position);
                }

            }
        });
        builder.show();
    }

}
