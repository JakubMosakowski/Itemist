package com.example.kuba.applista;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class AddNoteActivity extends AppCompatActivity {

    private ListView list;
    private Intent intent;
    private ArrayAdapter<String> adapter;
    private EditText editTextSubpointOfTheList;
    private TextView textViewCounter;
    private int howManySubpoints = 0;
    private Context context;
    private Toolbar toolbar;
    private String STRING_KEY_RESTORE="string_key";
    private String INT_KEY="int_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        list = (ListView) findViewById(R.id.listView);
        textViewCounter = (TextView) findViewById(R.id.textView_counter);
        editTextSubpointOfTheList = (EditText) findViewById(R.id.editText_subpoint_of_the_list);
        context = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        //setters

        setAdapter(savedInstanceState);
        setToolbar();
        setCounter(savedInstanceState);

        editTextSubpointOfTheList.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    buttonAcceptOnClick(v);
                }
                return false;
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> stringList=new ArrayList<String>();
        for(int i=0 ; i<adapter.getCount() ; i++){
            stringList.add(adapter.getItem(i));
        }
        outState.putInt(INT_KEY,howManySubpoints);
        outState.putStringArrayList(STRING_KEY_RESTORE, stringList);
    }


    public void setAdapter(Bundle bundle){
        ArrayList<String> subpointsL = new ArrayList<String>();
        if(bundle!=null) {
            subpointsL=bundle.getStringArrayList(STRING_KEY_RESTORE);
        }

        adapter = new ArrayAdapter<String>(this, R.layout.row, subpointsL);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogDeleteEdit(position);
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
    public void setToolbar(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(intent.getStringExtra("location"));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.you_sure_you_want_to_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
    public void setCounter(Bundle bundle){
        if(bundle!=null)
            howManySubpoints=bundle.getInt(INT_KEY);
        String container =getResources().getString(R.string.counter_value) + String.valueOf(howManySubpoints);
        textViewCounter.setText(container);
    }

    protected void buttonAcceptOnClick(View v) {

        String subpoint = editTextSubpointOfTheList.getText().toString();

        if (!subpoint.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.subpoint_added, Toast.LENGTH_SHORT).show();
            addSubpoint(v, subpoint);
            counterUpdatePlus();
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_no_name, Toast.LENGTH_SHORT).show();
        }
    }

    protected void addSubpoint(View v, String subpoint) {
        editTextSubpointOfTheList.setText("");
        adapter.add(subpoint);
    }

    protected void counterUpdatePlus() {
        howManySubpoints++;
        textViewCounter.setText(getResources().getString(R.string.counter_value) + String.valueOf(howManySubpoints));
    }

    protected void counterUpdateMinus() {
        howManySubpoints--;
        textViewCounter.setText(getResources().getString(R.string.counter_value) + String.valueOf(howManySubpoints));
    }

    protected void deleteNote(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.remove(adapter.getItem(position));
                counterUpdateMinus();
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), null);
        builder.show();
    }

    protected void editNote(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(AddNoteActivity.this);
        alert.setTitle(getResources().getString(R.string.edit_your_note));

        alert.setView(edittext);
        edittext.setText("");
        edittext.setHint(adapter.getItem(position));
        alert.setPositiveButton(getResources().getString(R.string.confirme_edit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!edittext.getText().toString().isEmpty()) {
                    adapter.remove(adapter.getItem(position));
                    adapter.insert(edittext.getText().toString(), position);
                    Toast.makeText(getApplicationContext(), R.string.edited, Toast.LENGTH_SHORT).show();
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
                getResources().getString(R.string.delete_note),
                getResources().getString(R.string.edit_note),
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

    protected void finishNote(View v){
        DataHandler data=new DataHandler(toolbar.getTitle().toString(),adapter,context);
        data.writeToFileWithNotes();
        data.writeToFileWithSubpoints();
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), R.string.note_added, Toast.LENGTH_SHORT).show();
    }
}
