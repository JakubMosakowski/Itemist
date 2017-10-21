package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;
    private Context context;
    private ListView list;
    private ArrayList<Model> modelList;
    private static final String KEY="KEY";
    private CustomAdapter adapter;
    private View v;
    private ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        imgButton=(ImageButton)findViewById(R.id.plus_button);
        v=findViewById(R.id.activity_note);
        context = getApplicationContext();
        list = (ListView) findViewById(R.id.listView);
        try {
            setToolbar();
        } catch (Exception e) {
            Log.e("TAG", "setAdapter się wywalił, stack:" + Arrays.toString(e.getStackTrace()));
        }

        setAdapter(savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            CheckBox cb;
            boolean[] enabled = new boolean[adapter.getCount()];
            for (int x = 0; x<list.getChildCount();x++){
                cb = (CheckBox)list.getChildAt(x).findViewById(R.id.checkBox);
                if(cb.isChecked()){
                    enabled[x] = true;
                }
            }
            outState.putBooleanArray(KEY, enabled);
        }catch(Exception e){
            Log.e("TAG", "OnSaveInstanceSieWywalil" + Arrays.toString(e.getStackTrace()));
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
    }

    public void setAdapter(Bundle bundle) {
        try {
            DataHandler data = new DataHandler( toolbar.getTitle().toString(),context);
            String[] notesArray = data.getArrayWithSubpoints();
            int count = notesArray.length;
            int howManyModels = setNumberOfModels(notesArray,count);
            boolean[] enabled=new boolean[howManyModels];


            if(bundle!=null) {
                enabled=bundle.getBooleanArray(KEY);
           }

            modelList=new ArrayList<Model>();
            for (int i = 0; i < howManyModels; i++)
                modelList.add(new Model(notesArray[i], enabled[i])) ;


            adapter = new CustomAdapter(modelList,NoteActivity.this);

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialogDeleteEdit(position);
                }
            });

        } catch (Exception e) {
            Log.e("TAG", "setAdapter się wywalił, stack:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public int setNumberOfModels(String[] array,int len){
        int howManyModels=0;
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
                adapter=new CustomAdapter(modelList,NoteActivity.this);
                list.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }
    protected void updateData(){
        String[] array=new String[adapter.getCount()];
        for(int i=0;i<adapter.getCount();i++)
            array[i]=adapter.getItem(i).getName();
        DataHandler data=new DataHandler(toolbar.getTitle().toString(),context,array);

        data.replaceFileWithSubpoints(array);
    }
    protected void editSubpoint(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(NoteActivity.this);
        alert.setTitle(getResources().getString(R.string.correct));

        alert.setView(edittext);
        edittext.setText("");
        edittext.setHint(adapter.getItem(position).getName());
        alert.setPositiveButton(getResources().getString(R.string.confirme_edit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!edittext.getText().toString().isEmpty()) {
                    Boolean enabled=adapter.getItem(position).getEnabled();
                    modelList.remove(position);
                    modelList.add( position,new Model(edittext.getText().toString(),enabled));
                    adapter=new CustomAdapter(modelList,NoteActivity.this);
                    list.setAdapter(adapter);
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
}
