package com.example.kuba.applista;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity  {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        editText=(EditText)findViewById(R.id.editText_add_name_of_note);
    }

    protected void clearTextView(View v)
    {
        EditText editText=v.findViewById(R.id.editText_add_name_of_note);
        editText.setText("");

    }

    protected void setTitleOfToolbar(View v){
        if(editText==null){
            Toast toast= Toast.makeText(this,"Error",Toast.LENGTH_LONG);;
            toast.show();
        }else{
            String nameOfNote=editText.getText().toString();
            Toolbar toolbar=ToolbarMenagment.getToolbarView(this);
            String nameOfNoteFromResources=getResources().getString(R.string.enter_name_of_note);
            if(!((nameOfNote.equals(nameOfNoteFromResources))||(nameOfNote.equals(""))))
            {
                toolbar.setTitle(nameOfNote);
                Toast.makeText(getApplicationContext(), R.string.note_added, Toast.LENGTH_LONG).show();
            }else{
                Toast toast=Toast.makeText(this,getResources().getString(R.string.error_wrong_name),Toast.LENGTH_LONG);
                toast.show();
            }

        }

    }


}
