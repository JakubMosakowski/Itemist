package com.example.kuba.itemist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class ChooseNoteActivity extends AppCompatActivity {
    private CustomAdapterWithButton adapter;
    ArrayList<String> notes;
    private Context context;
    private DynamicListView list;
    private Toolbar toolbar;
    private View v;
    private String[] subpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_note);

        context = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);

        list = (DynamicListView) findViewById(R.id.listView);

        v = findViewById(R.id.activity_choose_note);
        setToolbarTop();
        setBottomNavigation();
        try {
            enterNotesToListView();
        } catch (Exception e) {
            Log.e("Test enterNotesWywala",Arrays.toString(e.getStackTrace()));
        }


    }

    private void setBottomNavigation() {
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bnve.setTextVisibility(false);
        BottomNavigationItemView addNoteItem=findViewById(R.id.action_add);
        addNoteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseNoteActivity.this, AddNoteActivity.class);
                ChooseNoteActivity.this.startActivity(intent);
            }
        });
    }

    public void enterNotesToListView() {
        DataHandler data = new DataHandler(context);

        notes = new ArrayList<String>();
        String[] notesArray = data.getArrayWithNotes();
        int count = notesArray.length;

        for (int i = 0; i < count; i++) {
             /*====== this is where listView is populated =====*/
            if (notesArray[i] != null) {
                notes.add(notesArray[i]);
            }
        }
        setAdapter(notes);
    }

    public void setAdapter(ArrayList<String> notes) {
        adapter = new CustomAdapterWithButton( notes,this) {

            @Override
            public long getItemId(int position) {
                try {
                    return adapter.getItem(position).hashCode();
                } catch (IndexOutOfBoundsException e) {
                    return -1;
                }
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                    ImageButton imgButton=view.findViewById(R.id.edit_btn);
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogDeleteEdit(position);
                    }
                });
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
                        goToNote(position);

                    }
                });
                return view;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };

        list.setHoverOperation(new HoverOperationAllSwap(notes));
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    public void setToolbarTop() {
        toolbar.setNavigationIcon(null);
        final ImageButton  imgBtn=findViewById(R.id.overflow_icon);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ChooseNoteActivity.this, imgBtn);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.toolbar_menu_settings, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals(getResources().getString(R.string.settings)))
                            ;
                        else if(item.getTitle().toString().equals(getResources().getString(R.string.about_app)))
                            toAbout();

                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    private void toAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void goToNote(int position) {
        Intent intent = new Intent(ChooseNoteActivity.this, NoteActivity.class);
        intent.putExtra("location", adapter.getItem(position).toString());
        ChooseNoteActivity.this.startActivity(intent);
    }

    protected void deleteNote(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
                updateDataDelete(position);
                adapter.remove(adapter.getItem(position));
                list.setAdapter(adapter);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }

    protected void updateDataDelete(int position) {
        String noteName = adapter.getItem(position);
        DataHandler data = new DataHandler(noteName, context);
        subpoints=data.getArrayWithSubpoints();
        data.deleteNote(noteName);
    }

    @Override
    public void onPause() {
        super.onPause();
        updateData();
    }

    protected void updateData() {
        String[] array = new String[adapter.getCount()];
        for (int i = 0; i < adapter.getCount(); i++)
            array[i] = adapter.getItem(i);
        DataHandler data = new DataHandler(context);

        data.replaceFileWithNotes(array);
    }

    protected void updateDataEdit(int position) {
        String noteName = adapter.getItem(position);
        DataHandler data = new DataHandler(noteName, context);
        int len = adapter.getCount();

        String[] notes = new String[len];
       data.setStringWithSubpointsArray(subpoints);
        data.replaceFileWithSubpoints(subpoints);
        for (int i = 0; i < len; i++)
            notes[i] = adapter.getItem(i);
        data.setStringWithNotesArray(notes);
        data.replaceFileWithNotes(notes);

    }

    protected void editNote(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(ChooseNoteActivity.this);
        alert.setTitle(getResources().getString(R.string.edit));

        alert.setView(edittext);
        edittext.setText(adapter.getItem(position));
        edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edittext.setHint(adapter.getItem(position));
        alert.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                boolean sameNameNoteExists = false;
                for (int i = 0; i < adapter.getCount(); i++)
                    if (adapter.getItem(i).equals(edittext.getText().toString()))
                        sameNameNoteExists = true;

                if (!edittext.getText().toString().isEmpty() && sameNameNoteExists == false) {
                    updateDataDelete(position);
                    adapter.remove(adapter.getItem(position));

                    adapter.insert(edittext.getText().toString(), position);
                    updateDataEdit(position);
                    list.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), R.string.edited, Toast.LENGTH_SHORT).show();
                } else if (edittext.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.there_is_note_with_that_name, Toast.LENGTH_SHORT).show();
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
        builder.setTitle(getResources().getString(R.string.do_you_want_to_do_smth_with_this_note));
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.you_have_chosen) + adapter.getItem(position), Toast.LENGTH_LONG).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    deleteNote(position);
                } else if (which == 1) {
                    editNote(position);
                }

            }
        });
        builder.show();

    }
}
