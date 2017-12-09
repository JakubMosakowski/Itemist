package com.example.kuba.itemist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Kuba on 11.11.2017.
 */

public class AboutActivity extends AppCompatActivity {
    static final String email="mosakowskijakubsm@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar=findViewById(R.id.toolbar_top);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView emailTextView=findViewById(R.id.textView_email);
        String text=emailTextView.getText().toString()+"\n"+email;
        emailTextView.setText(text);

        TextView verTextView=findViewById(R.id.textView_version);
        String version=BuildConfig.VERSION_NAME;
        text=verTextView.getText().toString()+"\n"+version;
        verTextView.setText(text);
        final ImageButton imgBtn=findViewById(R.id.overflow_icon);
        imgBtn.setVisibility(View.INVISIBLE);
    }
}
