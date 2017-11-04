/*package com.example.kuba.itemist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChooseNoteTest {

    @Rule
    public ActivityTestRule<ChooseNoteActivity> activityTestRule = new ActivityTestRule<ChooseNoteActivity>(ChooseNoteActivity.class);
    Activity activity;
    Context ctx;
    DataHandler data;
    final int NUM = 20;
    final String TITLE = "TEST x2";
    String []notes=new String[NUM];
    ArrayAdapter<String> adapter;
    ListView list;

    @Before
    public void createNotes(){
        activity = activityTestRule.getActivity();
        ctx = activity.getApplicationContext();
        data = new DataHandler(ctx);
        data.deleteAllFiles();
        for(int i=0;i<NUM;i++)
            notes[i]="Note no."+i;
        data.setStringWithNotesArray(notes);
        adapter = new ArrayAdapter<String>(ctx, R.layout.row_for_notes_names, data.getArrayWithNotes());
        list=(ListView)activity.findViewById(R.id.listView);
        list.setAdapter(adapter);
    }


    @Test
    public void editNoteTest(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("Test ile ma adapter",String.valueOf(adapter.getCount()));
        for(int i=0;i<adapter.getCount();i+=2){



        }


    }



    @Test
    public void deleteAllNotes(){

    }

    @After
    public void clear(){
        data.deleteAllFiles();
    }


}
*/