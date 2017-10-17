package com.example.kuba.applista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ctx=getApplicationContext();
        toolbar.setNavigationIcon(null);

    }

    public void toAddNoteActivity(View v) {
        if(canHaveMoreNotes()) {
            Intent intent = new Intent(this, NameNoteActivity.class);
            startActivity(intent);
        }else
            Toast.makeText(this, v.getResources().getString(R.string.you_cant_have_more_notes), Toast.LENGTH_LONG).show();
    }

    public void toChooseNoteActivity(View v) {
        if(notesAreEmpty()){
            Toast.makeText(this, v.getResources().getString(R.string.you_have_no_notes), Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, ChooseNoteActivity.class);
            startActivity(intent);
        }
    }
    public boolean notesAreEmpty(){

        DataHandler data=new DataHandler(ctx);
       //data.deleteAllFiles();
        Toast.makeText(ctx, Arrays.toString(data.getArrayWithNotes()), Toast.LENGTH_SHORT).show();
        if(data.getArrayWithNotes().length==0)
            return true;
        else
            return false;
    }
    public boolean canHaveMoreNotes(){
        DataHandler data=new DataHandler(ctx);
        if(data.getArrayWithNotes().length>=30)
            return false;
        else
            return true;
    }

}
