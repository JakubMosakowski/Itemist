package com.example.kuba.applista;

/**
 * Created by Kuba on 15.10.2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Model>  {
    Model[] modelItems = null;
    private ArrayList<Model> modelArray;
    Context context;

    public CustomAdapter(Context context, Model[] resource) {
        super(context, R.layout.row, resource);
        this.context = context;
        this.modelItems = resource;
    }
    public CustomAdapter(ArrayList<Model> data, Context context) {
        super(context, R.layout.row_for_subpoints, data);
        this.modelArray = data;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model model=getItem(position);
        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_for_subpoints, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.textView);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
            name.setText(model.getName());
            if (model.getEnabled() == true){
                cb.setChecked(true);
            } else{
                cb.setChecked(false);
            }
        }

        return convertView;

    }



}