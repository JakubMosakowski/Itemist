package com.example.kuba.itemist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class AddNoteTest {
    Activity activity;
    Context ctx;
    DataHandler data;
    final int NUM = 3;
    final String TITLE = "TEST x2";

    @Rule
    public ActivityTestRule<AddNoteActivity> mActivityTestRule = new ActivityTestRule<AddNoteActivity>(AddNoteActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, MainActivity.class);
            result.putExtra("location", TITLE);
            return result;
        }
    };

    @Before
    public void beginning() {
        activity = mActivityTestRule.getActivity();
        ctx = activity.getApplicationContext();
        data = new DataHandler(ctx);
        data.deleteAllFiles();
    }

    @Test
    public void addSubpointsTest() {
        ViewInteraction appCompatButton;

        ViewInteraction appCompatEditText;
        String[] notes = new String[NUM];
        for (int i = 0; i < NUM; i++) {
            appCompatEditText = onView(
                    allOf(withId(R.id.editText_subpoint_of_the_list),
                            isDisplayed()));
            appCompatEditText.perform(click());
            appCompatEditText = onView(
                    allOf(withId(R.id.editText_subpoint_of_the_list),
                            isDisplayed()));
            appCompatEditText.perform(replaceText(String.valueOf(i)), closeSoftKeyboard());

            appCompatButton = onView(
                    allOf(withId(R.id.button_accept_subpoint),
                            isDisplayed()));
            appCompatButton.perform(click());
            notes[i] = String.valueOf(i);
        }
        String[] notes2 = new String[NUM];

        DynamicListView lv = activity.findViewById(R.id.listView);
        for (int i = 0; i < NUM; i++) {
            notes2[i] = lv.getAdapter().getItem(i).toString();
        }
        Assert.assertArrayEquals("SUBNOTES NOT MATCHING", notes, notes2);


        View v = activity.findViewById(R.id.activity_add_note);
        Button buttonFinish = v.findViewById(R.id.button_finish);
        buttonFinish.callOnClick();

    }
    /*@Test TODO repair
    public void    fileSaveTest(){
        String[] title={TITLE};
        data.setFilename(TITLE);
        String[] notes=new String[NUM];
        for(int i=0;i<NUM;i++)
            notes[i]=String.valueOf(i);
        Assert.assertArrayEquals("TITLE NOT MATCHING",title, data.getArrayWithNotes());

        Assert.assertArrayEquals("SUBNOTES IN FILE NOT MATCHING",notes,data.getArrayWithSubpoints());
    }*/


    @After
    public void delete() {
        data.deleteAllFiles();
    }
}
