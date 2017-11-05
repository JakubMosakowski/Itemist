package com.example.kuba.itemist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by Kuba on 05.11.2017.
 */


    public class CustomAdapterWithButton extends ArrayAdapter<String> {

        public ArrayList<String> array;
        Context context;


        public CustomAdapterWithButton(ArrayList<String> data, Context context) {
            super(context, R.layout.row_with_button, data);
            this.array = data;
            this.context = context;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_with_button, parent, false);
            }
            TextView name = convertView.findViewById(R.id.Row_with_button);

            name.setText(getItem(position));
            ImageButton imgButton=convertView.findViewById(R.id.edit_btn);

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return convertView;

        }


    }

