package com.example.kuba.itemist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kuba on 22.10.2017.
 */

public class CustomAdapterWithCounter extends CustomAdapter {

    private boolean[] selected;
    private TextView textView;

    public CustomAdapterWithCounter(ArrayList<Model> data, Context context, TextView v) {
        super(data, context);
        textView = v;
        selected = new boolean[data.size()];
    }

    private View.OnClickListener pressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selected[(Integer) v.getTag()] = ((CheckBox) v).isChecked();
            String firstNum, secondNum, wholeText;
            secondNum = String.valueOf(modelArray.size());
            int howManyChecked = 0;
            for (int i = 0; i < selected.length; i++)
                if (selected[i])
                    howManyChecked++;
            firstNum = String.valueOf(howManyChecked);
            wholeText = firstNum + "/" + secondNum;
            textView.setText(wholeText);
        }
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Model model = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_for_subpoints, parent, false);
        }
            TextView name = convertView.findViewById(R.id.textView);
            CheckBox cb = convertView.findViewById(R.id.checkBox);
            cb.setTag(position);
            name.setText(model.getName());
            if (model.getEnabled() == true) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(pressed);


        return convertView;

    }
}
