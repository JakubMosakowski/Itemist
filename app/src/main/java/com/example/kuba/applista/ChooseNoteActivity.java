package com.example.kuba.applista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseNoteActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private Context context;
    private ListView list;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_note);

        context = getApplicationContext();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        list = (ListView) findViewById(R.id.listView);
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

        try{
            Toast.makeText(getApplicationContext(), Arrays.toString(data.getArrayWithNotes()), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("TAG", "MOJE ERRORY:"+Arrays.toString(e.getStackTrace()));
        }
        for (int i = 0; i < count; i++) {
             /*====== this is where listView is populated =====*/
            if(notesArray[i] != null) {
                notes.add(notesArray[i]);
            }
        }
        //Toast.makeText(getApplicationContext(),String.valueOf(notesArray.length) , Toast.LENGTH_LONG).show();//TODO usuń
      // notes.addAll( Arrays.asList(notesArray) );
        adapter = new ArrayAdapter<String>(this, R.layout.row_for_notes_names, notes);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
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

}
