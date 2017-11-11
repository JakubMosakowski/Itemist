package com.example.kuba.itemist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context ctx;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        if (intent.getStringExtra("TAG") != null) {
            if (intent.getStringExtra("TAG").equals("noteAdded")){
                Toast.makeText(getApplicationContext(), R.string.note_added, Toast.LENGTH_SHORT).show();
                intent.removeExtra("TAG");
            }
        }


         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();
        ctx = getApplicationContext();
    }
    public void setToolbar() {
        toolbar.setNavigationIcon(null);
        final ImageButton  imgBtn=findViewById(R.id.overflow_icon);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, imgBtn);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.toolbar_menu_about, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals(getResources().getString(R.string.settings)))
                            ;
                        else if(item.getTitle().toString().equals(getResources().getString(R.string.about_app)))
                            toAbout();

                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    private void toAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }



    public void toAddNoteActivity(View v) {
        if (canHaveMoreNotes()) {
            Intent intent = new Intent(this, NameNoteActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, v.getResources().getString(R.string.you_cant_have_more_notes), Toast.LENGTH_LONG).show();
    }

    public void toChooseNoteActivity(View v) {
        if (notesAreEmpty()) {
            Toast.makeText(this, v.getResources().getString(R.string.you_have_no_notes), Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, ChooseNoteActivity.class);
            startActivity(intent);
        }
    }

    public boolean notesAreEmpty() {
        DataHandler data = new DataHandler(ctx);
        return data.getArrayWithNotes().length == 0;
    }

    public boolean canHaveMoreNotes() {
        DataHandler data = new DataHandler(ctx);
        return data.getArrayWithNotes().length < 50;
    }

}
