package com.example.kuba.applista;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


        defaultAdapter();
       /* enterNotesToListView();
        */toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void enterNotesToListView(){
        DataHandler data=new DataHandler(context);
        adapter=data.returnAdapterWithNotes();
        Toast.makeText(getApplicationContext(), String.valueOf(adapter.getCount()), Toast.LENGTH_SHORT).show();
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
    public void defaultAdapter(){


        String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};

        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(cars) );

        adapter = new ArrayAdapter<String>(this, R.layout.row_for_notes_names, carL);

        list.setAdapter(adapter);
    }
}
