package com.example.kuba.itemist;

/**
 * Created by Kuba on 15.10.2017.
 */

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterWithCheckboxes extends ArrayAdapter<Model> {

    public ArrayList<Model> modelArray;
    Context context;
    int fTextSize;
    View v;



    public CustomAdapterWithCheckboxes(ArrayList<Model> data, Context context) {
        super(context, R.layout.row_for_subpoints, data);
        this.modelArray = data;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Model model = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_for_subpoints, parent, false);
        }
        v=convertView;
            TextView name = convertView.findViewById(R.id.textView);
            CheckBox cb = convertView.findViewById(R.id.checkBox);

            name.setText(model.getName());
            if (model.getEnabled() == true) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }

        return convertView;

    }


}