package com.example.kuba.applista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;
    private Context context;
    private ListView list;
    //private ArrayAdapter<String> adapter;
    private Model[] modelItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        intent = getIntent();
        context=getApplicationContext();
        list=(ListView)findViewById(R.id.listView);
try{
    setToolbar();
}catch(Exception e){
    Log.e("TAG","setAdapter się wywalił, stack:"+ Arrays.toString(e.getStackTrace()));
}

        setAdapter();
    }
    public void setToolbar(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(intent.getStringExtra("location"));
    }

    public void setAdapter(){
        try {
            DataHandler data = new DataHandler(context, toolbar.getTitle().toString());


            ArrayList<String> notes = new ArrayList<String>();
            String[] notesArray = data.getArrayWithSubpoints();
            Toast.makeText(context, String.valueOf(notesArray.length), Toast.LENGTH_SHORT).show();
            int count = notesArray.length;
            int howManyModels=0;


            for (int i = 0; i < count; i++) {

                if (notesArray[i] != null) {
                howManyModels++;
                }
            }
            modelItems = new Model[howManyModels];
            for(int i=0;i<howManyModels;i++)
                modelItems[i]=new Model(notesArray[i],false);

            //adapter = new ArrayAdapter<String>(this, R.layout.row_for_subpoints, notes);

            CustomAdapter adapter = new CustomAdapter(NoteActivity.this, modelItems);
            list.setAdapter(adapter);
        }catch(Exception e){
            Log.e("TAG","setAdapter się wywalił, stack:"+ Arrays.toString(e.getStackTrace()));
        }
    }
}
