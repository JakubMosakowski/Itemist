package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.kuba.itemist.R.id.listView;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;
    private Context context;
    private DynamicListView  list;
    private ArrayList<Model> modelList;
    private static final String KEY = "KEY";
    private CustomAdapterWithCheckboxesWithCounter adapter;
    private View v;
    private Button addButton;
    private int fontSize;
    private ImageButton buttonSettings;
    private TextView textView;
    private ConstraintLayout cLayout;
    private Bundle bundle;
    private final int defaultFontSize=25;

@Override
protected  void onResume(){
    super.onResume();
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    fontSize = prefs.getInt("fontSize",0)+defaultFontSize;
    if(getIntent().getBooleanArrayExtra(KEY)!=null){
        boolean[] enabled=getIntent().getBooleanArrayExtra(KEY);
        bundle=new Bundle();
        bundle.putBooleanArray(KEY, enabled);
    }

   mySetAdapter(bundle);
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fontSize = prefs.getInt("fontSize",0)+defaultFontSize;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        cLayout = (ConstraintLayout) findViewById(R.id.activity_note);
        textView = (TextView) findViewById(R.id.counter_textView);

        addButton = (Button) findViewById(R.id.button_add_subpoint);
        addButton.setVisibility(View.VISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubpoint();
            }
        });
        v = getWindow().getDecorView();

        list = (DynamicListView ) findViewById(listView);
        //TODO REMOVE BELOW
       /* textView.setLongClickable(true);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for (int i=0;i<20;i++){
                    updateAdapter("FakeSub"+i);
                    setTextView();
                }
                return false;
            }
        });*/
        //TODO REMOVE ABOVE
        try {
            setToolbar();
        } catch (Exception e) {
        }
        mySetAdapter(savedInstanceState);


    }
    private void toSettings(View view) {
        CheckBox cb;
        boolean[] enabled = new boolean[adapter.getCount()];
        for (int x = 0; x < modelList.size(); x++) {
            if (modelList.get(x).getEnabled()) {
                enabled[x] = true;
            }
        }
        getIntent().putExtra(KEY, enabled);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            CheckBox cb;
            boolean[] enabled = new boolean[adapter.getCount()];
            for (int x = 0; x < modelList.size(); x++) {
                if (modelList.get(x).getEnabled()) {
                    enabled[x] = true;
                }
            }
            outState.putBooleanArray(KEY, enabled);
        } catch (Exception e) {
        }
    }
    @Override
    public void onPause(){
        super.onPause();

        updateData();
    }
    public void setToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(intent.getStringExtra("location"));
        buttonSettings=findViewById(R.id.settings_button);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSettings(view);
            }
        });
        textView.setVisibility(View.VISIBLE);
        textView.setText("");

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.do_you_want_to_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NoteActivity.this, ChooseNoteActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
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

            adapter = new CustomAdapterWithCheckboxesWithCounter(modelList, NoteActivity.this, textView){

                @Override
                public long getItemId(int position) {
                    try {
                        return modelList.get(position).hashCode();
                    } catch (IndexOutOfBoundsException e) {
                        return -1;
                    }
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    textView.setTextSize(fontSize);


                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            list.startMoveById(getItemId(position));
                            //Toast.makeText(NoteActivity.this, Arrays.toString(array), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(NoteActivity.this, "ShortClick", Toast.LENGTH_SHORT).show();
                            dialogDeleteEdit(position);
                        }
                    });
                    return view;
                }

                @Override
                public boolean hasStableIds() {
                    return true;
                }

            };
            list.setHoverOperation(new HoverOperationAllSwap(modelList));


            Log.e("Sprawdz co z file",Arrays.toString(data.getArrayWithSubpoints()));
            list.setAdapter(adapter);
            list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);



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
               // adapter = new CustomAdapterWithCheckboxesWithCounter(modelList, NoteActivity.this, textView);
                adapter.notifyDataSetChanged();
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
                    //adapter = new CustomAdapterWithCheckboxesWithCounter(modelList, NoteActivity.this, textView);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);

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


        //adapter = new CustomAdapterWithCheckboxesWithCounter(modelList, NoteActivity.this, textView);

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), R.string.subpoint_added, Toast.LENGTH_SHORT).show();
        updateData();
    }
}
