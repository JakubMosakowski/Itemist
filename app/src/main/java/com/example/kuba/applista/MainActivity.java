package com.example.kuba.applista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toAddNoteActivity(View view)
    {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
