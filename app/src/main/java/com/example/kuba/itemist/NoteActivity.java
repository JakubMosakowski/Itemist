package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.kuba.itemist.R.id.listView;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;
    private Context context;
    TextView toolbarTitle;
    private DynamicListView list;
    private ArrayList<Model> modelList;
    private static final String KEY = "KEY";
    private CustomAdapterWithCheckboxes adapter;
    private View v;
    private Button addButton;
    private int fontSize;
    private ConstraintLayout cLayout;
    private Bundle bundle;
    private final int defaultFontSize = 10;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fontSize = prefs.getInt("fontSize", 0) + defaultFontSize;
        if (getIntent().getBooleanArrayExtra(KEY) != null) {
            Log.e("Test booleanOnResume", Arrays.toString(getIntent().getBooleanArrayExtra(KEY)));
            boolean[] enabled = getIntent().getBooleanArrayExtra(KEY);
            bundle = new Bundle();
            bundle.putBooleanArray(KEY, enabled);
            onDestroy();
            onCreate(bundle);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fontSize = prefs.getInt("fontSize", 0) + defaultFontSize;

        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        intent = getIntent();
        cLayout = (ConstraintLayout) findViewById(R.id.activity_note);

        addButton = (Button) findViewById(R.id.button_add_subpoint);
        addButton.setVisibility(View.VISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubpoint();
            }
        });
        v = getWindow().getDecorView();

        list = (DynamicListView) findViewById(listView);
        try {
            setToolbar();
        } catch (Exception e) {
        }
        mySetAdapter(savedInstanceState);
        setBottomNavigation();


    }

    private void setBottomNavigation() {
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bnve.setTextVisibility(false);
    }

    private void toSettings() {
        CheckBox cb;
        boolean[] enabled = new boolean[adapter.getCount()];
        for (int x = 0; x < modelList.size(); x++) {
            if (modelList.get(x).getEnabled()) {
                Log.e("Test modelOnSettings", String.valueOf(modelList.get(x).getEnabled()));
                enabled[x] = true;
            }
        }
        getIntent().putExtra(KEY, enabled);
        Log.e("Test booleanOnSettings", Arrays.toString(getIntent().getBooleanArrayExtra(KEY)));
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
    public void onPause() {
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
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.DEFAULT);
        toolbarTitle.setText(intent.getStringExtra("location"));
        final ImageButton imgBtn = findViewById(R.id.overflow_icon);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(NoteActivity.this, imgBtn);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.toolbar_menu_settings, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equals(getResources().getString(R.string.settings)))
                            toSettings();
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NoteActivity.this, ChooseNoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void mySetAdapter(Bundle bundle) {
        try {
            DataHandler data = new DataHandler(toolbarTitle.getText().toString(), context);
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

            adapter = new CustomAdapterWithCheckboxes(modelList, NoteActivity.this) {

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
                            return true;
                        }
                    });

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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


            Log.e("Sprawdz co z file", Arrays.toString(data.getArrayWithSubpoints()));
            list.setAdapter(adapter);
            list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        } catch (Exception e) {
            Log.e("Test Nie dziala wyswie", Arrays.toString(e.getStackTrace()));
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
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }

    protected void updateData() {
        String[] array = new String[adapter.getCount()];
        for (int i = 0; i < modelList.size(); i++)
            array[i] = modelList.get(i).getName();
        DataHandler data = new DataHandler(toolbarTitle.getText().toString(), context, array);

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
                    adapter.notifyDataSetChanged();
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

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), R.string.subpoint_added, Toast.LENGTH_SHORT).show();
        updateData();
        boolean[] enabled = new boolean[adapter.getCount()];
        for (int x = 0; x < modelList.size(); x++) {
            if (modelList.get(x).getEnabled()) {
                enabled[x] = true;
            }
        }
        bundle = new Bundle();
        bundle.putBooleanArray(KEY, enabled);
        onDestroy();
        onCreate(bundle);
    }
}
