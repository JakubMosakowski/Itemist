package com.example.kuba.applista;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kuba on 17.10.2017.
 */

public class NameNoteActivityTest {


    @Rule
    public ActivityTestRule<NameNoteActivity> activityTestRule = new ActivityTestRule<NameNoteActivity>(NameNoteActivity.class);

    @Test
    public void testButton() {
        Log.d("MainActivityTesting", "Check if activity has both buttons with correct subtitle.");
        Activity activity = activityTestRule.getActivity();
        View v=activity.findViewById(R.id.activity_name_note);
        Button one=v.findViewById(R.id.button_name_of_note);

        assertNotNull("Button don't exist",one);
        assertEquals("Wrong subtitle for  button",v.getResources().getString(R.string.add_name_of_note_button),one.getText());
    }

    @Test
    public void testEditText() {
        Log.d("MainActivityTesting", "Check if activity has both buttons with correct subtitle.");
        Activity activity = activityTestRule.getActivity();
        View v=activity.findViewById(R.id.activity_name_note);
        EditText one=v.findViewById(R.id.editText_name_of_note);

        assertNotNull("EditText don't exist",one);
        assertEquals("Wrong hint for EditText",v.getResources().getString(R.string.enter_name_of_note),one.getHint());
    }

    @Test//TODO finish testActivityChangeToAddNote
    public void testActivityChangeToAddNote() {
        Log.d("MainActivityTesting", "");
        Activity activity = activityTestRule.getActivity();

    }
}
