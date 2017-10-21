package com.example.kuba.itemist;

import android.app.Activity;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kuba on 19.10.2017.
 */

public class DataHandlerTest {
    @Rule
    public ActivityTestRule<AddNoteActivity> activityTestRule = new ActivityTestRule<AddNoteActivity>(AddNoteActivity.class);
    Activity activity;
    Context ctx;
    DataHandler data;
    String NOTENAME="noteName";
    @Before
    public void beginning(){
        activity = activityTestRule.getActivity();
        ctx=activity.getApplicationContext();
        data=new DataHandler(ctx);
        data.deleteAllFiles();
    }


    @Test
    public void saveDataAppend() {

        Log.d("MainActivityTesting", "Check if data is saving in note correctly.");

        int len=5;
        String[] notesNames=new String[len];
        String[] subpoints=new String[len];

        data= new DataHandler(ctx);
        data.setSavingName("dummy.txt");
        for(int j=0;j<len;j++) {
            notesNames[j] = "Dummy stuff NoteName no." + j;
            subpoints[j] = "Dummy subpoint no." + j;
        }

        for(int i=0;i<len;i++){
            data.setFilename(notesNames[i]);
            data.setStringWithSubpointsArray(subpoints);
            data.appendToFileWithNotes();
            data.appendToFileWithSubpoints();
        }

        assertArrayEquals("Strings(read and saved) are not matching",notesNames, data.getArrayWithNotes());
        for(int i=0;i<len;i++){
            data.setFilename(notesNames[i]);
            subpoints[i] = "Dummy subpoint no." + i;
            assertEquals("["+i+"]Strings(read and saved)are not matching",subpoints[i], data.getArrayWithSubpoints()[i]);
        }

    }
    @Test
    public void addNoteTest(){
        String[] array=new String[5];
        for(int i=0;i<5;i++)
            array[i]="Smth no."+String.valueOf(i);
        data=new DataHandler(NOTENAME,ctx,array);

        data.appendToFileWithNotes();
        data.appendToFileWithSubpoints();
        assertEquals("1Strings(read and saved) are not matching",NOTENAME, data.getFilename());
        assertArrayEquals("2Strings(read and saved) are not matching",array, data.getArrayWithSubpoints());
        assertEquals("3Strings(read and saved) are not matching","notes.txt", data.getNOTES());
        assertEquals("4Strings(read and saved) are not matching",NOTENAME, data.getArrayWithNotes()[0]);
        ctx.deleteFile("notes.txt");

    }
    @Test
    public void replaceNotesFileTest(){
        String[] array=new String[5];
        for(int i=0;i<5;i++)
            array[i]="Smth no."+String.valueOf(i);
        data=new DataHandler(NOTENAME,ctx,array);
        data.replaceFileWithNotes(array);
        assertArrayEquals("Strings(read and saved) are not matching",array,data.getArrayWithNotes());
        ctx.deleteFile("notes.txt");
    }

    @Test
    public void replaceSubpointsFileTest(){
        String[] array=new String[5];
        for(int i=0;i<5;i++)
            array[i]="Smth no."+String.valueOf(i);
        data=new DataHandler(NOTENAME,ctx,array);
        data.replaceFileWithSubpoints(array);
        assertArrayEquals("Strings(read and saved) are not matching",array,data.getArrayWithSubpoints());
        ctx.deleteFile("notes.txt");
    }

     @After
    public void delete(){
        data.deleteAllFiles();
    }

}
