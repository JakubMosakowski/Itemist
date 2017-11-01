package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;
    private Context context;
    private ListView list;
    private ArrayList<Model> modelList;
    private static final String KEY = "KEY";
    private CustomAdapterWithCounter adapter;
    private View v;
    private ImageButton imgButton;
    private TextView textView;
    private ConstraintLayout cLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        cLayout = (ConstraintLayout) findViewById(R.id.activity_note);
        textView = (TextView) findViewById(R.id.counter_textView);
        textView.setVisibility(View.VISIBLE);
        textView.setText("");
        imgButton = (ImageButton) findViewById(R.id.plus_button);
        v = getWindow().getDecorView();
        context = getApplicationContext();
        list = (ListView) findViewById(R.id.listView);

        try {
            setToolbar();
        } catch (Exception e) {
        }
        mySetAdapter(savedInstanceState);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            CheckBox cb;
            boolean[] enabled = new boolean[adapter.getCount()];
            for (int x = 0; x < list.getChildCount(); x++) {
                cb = list.getChildAt(x).findViewById(R.id.checkBox);
                if (cb.isChecked()) {
                    enabled[x] = true;
                }
            }
            outState.putBooleanArray(KEY, enabled);
        } catch (Exception e) {
        }
    }

    public void setToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(intent.getStringExtra("location"));
        imgButton.setVisibility(View.VISIBLE);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubpoint();
            }
        });


    }

    public void setTextView() {

        int howManyChecked = 0;
        String firstNum, secondNum, wholeText;
        int len = adapter.getCount();
        for (int i = 0; i < len; i++)
            if (adapter.getItem(i).getEnabled())
                howManyChecked++;
        firstNum = String.valueOf(howManyChecked);
        secondNum = String.valueOf(len);
        wholeText = firstNum + "/" + secondNum;
        textView.setText(wholeText);
    }


    public void mySetAdapter(Bundle bundle) {
        try {
            DataHandler data = new DataHandler(toolbar.getTitle().toString(), context);
            String[] notesArray = data.getArrayWithSubpoints();
            int count = notesArray.length;
            int howManyModels = setNumberOfModels(notesArray, count);
            boolean[] enabled = new boolean[howManyModels];


            if (bundle != null) {
                enabled = bundle.getBooleanArray(KEY);
            }

            modelList = new ArrayList<Model>();
            for (int i = 0; i < howManyModels; i++)
                modelList.add(new Model(notesArray[i], enabled[i]));

            adapter = new CustomAdapterWithCounter(modelList, NoteActivity.this, textView);
            Log.e("Sprawdz co z file",Arrays.toString(data.getArrayWithSubpoints()));
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialogDeleteEdit(position);
                }
            });
            setTextView();
        } catch (Exception e) {
            Log.e("Test Nie dziala wyswie",Arrays.toString(e.getStackTrace()));
        }
    }

    public int setNumberOfModels(String[] array, int len) {
        int howManyModels = 0;
        for (int i = 0; i < len; i++) {
            if (array[i] != null) {
                howManyModels++;
            }
        }
        return howManyModels;
    }

    protected void deleteSubpoint(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modelList.remove(position);
                adapter = new CustomAdapterWithCounter(modelList, NoteActivity.this, textView);
                list.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
                updateData();
                setTextView();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }

    protected void updateData() {
        String[] array = new String[adapter.getCount()];
        for (int i = 0; i < modelList.size(); i++)
            array[i] = modelList.get(i).getName();
        DataHandler data = new DataHandler(toolbar.getTitle().toString(), context, array);

        data.replaceFileWithSubpoints(array);
    }

    protected void editSubpoint(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(NoteActivity.this);
        alert.setTitle(getResources().getString(R.string.correct));

        alert.setView(edittext);
        edittext.setText(adapter.getItem(position).getName());
        edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edittext.setHint(adapter.getItem(position).getName());
        alert.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!edittext.getText().toString().isEmpty()) {
                    boolean enabled = adapter.getItem(position).getEnabled();
                    modelList.remove(position);
                    modelList.add(position, new Model(edittext.getText().toString(), enabled));
                    adapter = new CustomAdapterWithCounter(modelList, NoteActivity.this, textView);

                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    setTextView();

                    Toast.makeText(getApplicationContext(), R.string.edited, Toast.LENGTH_SHORT).show();
                    updateData();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel), null);
        alert.show();
    }

    protected void dialogDeleteEdit(final int position) {
        CharSequence options[] = new CharSequence[]{
                getResources().getString(R.string.delete),
                getResources().getString(R.string.edit),
                getResources().getString(R.string.do_nothing)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.do_you_want_to_do_smth_with_this_subpoint));

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    deleteSubpoint(position);
                } else if (which == 1) {
                    editSubpoint(position);
                }

            }
        });
        builder.show();
    }

    protected void addSubpoint() {
        if (adapter.getCount() >= 50)
            Toast.makeText(getApplicationContext(), R.string.you_cant_have_more_subpoints, Toast.LENGTH_SHORT).show();
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(NoteActivity.this);
            alert.setTitle(getResources().getString(R.string.add_subpoint));

            alert.setView(edittext);
            edittext.setText("");
            edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            edittext.setHint(getResources().getString(R.string.enter_content_of_subpoint));
            alert.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (!edittext.getText().toString().isEmpty()) {
                        updateAdapter(edittext.getText().toString());
                        setTextView();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.setNegativeButton(getResources().getString(R.string.cancel), null);
            alert.show();

        }
    }

    protected void updateAdapter(String subpointName) {
        modelList.add(list.getCount(), new Model(subpointName, false));


        adapter = new CustomAdapterWithCounter(modelList, NoteActivity.this, textView);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), R.string.subpoint_added, Toast.LENGTH_SHORT).show();
        updateData();
    }
}
