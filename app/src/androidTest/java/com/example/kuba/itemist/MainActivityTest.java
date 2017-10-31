package com.example.kuba.itemist;

import android.app.Activity;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testButtons() {
        Log.d("MainActivityTesting", "Check if activity has both buttons with correct subtitle.");
        Activity activity = activityTestRule.getActivity();
        View v = activity.findViewById(R.id.activity_main);
        Button one = v.findViewById(R.id.add_list_button);
        Button two = v.findViewById(R.id.choose_list_button);

        assertNotNull("First button don't exist", one);
        assertNotNull("Second button don't exist", two);
        assertEquals("Wrong subtitle for first button", v.getResources().getString(R.string.add_note_button), one.getText());
        assertEquals("Wrong subtitle for second button", v.getResources().getString(R.string.choose_note_button), two.getText());
    }

    @Test
    public void testTitle() {
        Log.d("MainActivityTesting", "Check if activity has correct title");
        Activity activity = activityTestRule.getActivity();
        View v = activity.findViewById(R.id.activity_main);
        assertEquals("MainActivity has wrong title", v.getResources().getString(R.string.app_name), activity.getTitle());
    }

    @Test//TODO finish testActivityChangeToCreateNote
    public void testActivityChangeToCreateNote() {
        Log.d("MainActivityTesting", "");
        Activity activity = activityTestRule.getActivity();

    }

    @Test//TODO finish testActivityChangeToChooseNote
    public void testActivityChangeToChooseNote() {
        Log.d("MainActivityTesting", "");
        Activity activity = activityTestRule.getActivity();

    }

    @Test//TODO check when will be empty
    public void testIfNotesAreNoteEmpty() {
        Log.d("MainActivityTesting", "Check check if notes file is empty.");
        Activity activity = activityTestRule.getActivity();
        Context ctx = activity.getApplicationContext();
        DataHandler data = new DataHandler(ctx);
        assertNotNull("Array with notes is empty", data.getArrayWithNotes());
    }
}

