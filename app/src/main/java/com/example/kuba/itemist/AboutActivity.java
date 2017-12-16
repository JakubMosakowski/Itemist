package com.example.kuba.itemist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Kuba on 11.11.2017.
 */

public class AboutActivity extends AppCompatActivity {
    static final String email="mosakowskijakubsm@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setToolbar();
        setTextViews();
        setOverflowIcon();
        setBottomNavigation();
    }

    private void setOverflowIcon() {
        final ImageButton imgBtn=findViewById(R.id.overflow_icon);
        imgBtn.setVisibility(View.INVISIBLE);
    }

    private void setTextViews() {
        TextView emailTextView=findViewById(R.id.textView_email);
        String text=emailTextView.getText().toString()+"\n"+email;
        emailTextView.setText(text);
        TextView authorTextView=findViewById(R.id.textView_author);
        String authorGithub=authorTextView.getText()+"\n"+getResources().getString(R.string.github);
        authorTextView.setText(authorGithub);
        TextView verTextView=findViewById(R.id.textView_version);
        String version= BuildConfig.VERSION_NAME;
        text=verTextView.getText().toString()+"\n"+version;
        verTextView.setText(text);
    }

    private void setToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar_top);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setBottomNavigation() {
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bnve.setTextVisibility(false);
    }
}
