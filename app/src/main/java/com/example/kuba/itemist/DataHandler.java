package com.example.kuba.itemist;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kuba on 11.10.2017.
 */

public class DataHandler extends Application {
    private String filename;
    private String NOTES = "notes.txt";


    private FileOutputStream outputStream;
    private Context ctx;
    private final int HOWMANY = 50;
    public String[] stringWithNotes = new String[HOWMANY];
    public String[] stringWithSubpoints = new String[HOWMANY];

    DataHandler(String fn, Context c, String[] s) {
        filename = fn;
        ctx = c;
        stringWithSubpoints = s;
        createNotesFile();
       createSubpointFile();
    }

    DataHandler(Context c) {
        ctx = c;
       createNotesFile();
    }

    DataHandler(String fn, Context c) {
        ctx = c;
        filename = fn;
       createNotesFile();
        createSubpointFile();
    }

    public void setFilename(String file) {
        filename = file;
    }

    public void setStringWithSubpointsArray(String[] array) {
        stringWithSubpoints = array;
    }

    public void setStringWithNotesArray(String[] array) {
        stringWithNotes = array;
    }


    public void appendToFileWithSubpoints() {
        try {
            String smth;
            Log.e("Test sub przed add",Arrays.toString(stringWithSubpoints));


            outputStream = ctx.openFileOutput(filename, Context.MODE_APPEND);
            for (int i = 0; i < stringWithSubpoints.length; i++) {
                smth = stringWithSubpoints[i] + "\n";
                outputStream.write(smth.getBytes());
            }
            Log.e("Test fName  po add",filename);
            Log.e("Test sub  po add",Arrays.toString(getArrayWithSubpoints()));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendToFileWithNotes() {
        try {
            Log.e("Test not  przed add",Arrays.toString(getArrayWithNotes()));
            String smth = filename + "\n";
            Log.e("Test not przed add(smth",smth);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(NOTES, Context.MODE_APPEND));
            outputStreamWriter.write(smth);
            outputStreamWriter.close();
            Log.e("Test not  po add",Arrays.toString(getArrayWithNotes()));
        } catch (Exception e) {
            Log.e("Test NIE DZIALA APPEND", Arrays.toString(e.getStackTrace()));
        }
    }

    public void readFromFileWithSubpoints() {
        try {
            InputStream is = ctx.openFileInput(filename);
            if(is!=null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                int i = 0;
                if(stringWithSubpoints.length==0)
                    stringWithSubpoints=new String[HOWMANY];
                String line="";
                while ((line = br.readLine()) != null && i < HOWMANY) {
                    stringWithSubpoints[i] = line;
                    i++;
                }
                Log.e("Test Dziala readDataH", "s");
            }
            is.close();
        } catch (Exception e) {
            Log.e("Test NieDzialaReadDataH",Arrays.toString(e.getStackTrace()));
        }
    }

    public void readFromFileWithNotes() {
        try {
            InputStream inputStream = ctx.openFileInput(NOTES);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                Log.e("Test czyta dlugosc sWN",String.valueOf(stringWithNotes.length));
                if(stringWithNotes.length==0)
                    stringWithNotes=new String[HOWMANY];
                int i = 0;
                while ((receiveString = bufferedReader.readLine()) != null && i<HOWMANY) {
                    stringWithNotes[i] = receiveString;
                    i++;
                }

                inputStream.close();
                Log.e("Test czyta stringWi",Arrays.toString(stringWithNotes));
            }
        } catch (Exception e) {
            Log.e("Test nie działa czytaie",Arrays.toString(e.getStackTrace()));
        }

    }

    public String[] getArrayWithSubpoints() {
        readFromFileWithSubpoints();
        String[] array = stringWithSubpoints;
        array = trimEmptyArray(array);
        return array;
    }

    public String[] getArrayWithNotes() {
        readFromFileWithNotes();
        String[] array = stringWithNotes;
        array = trimEmptyArray(array);
        return array;
    }

    public void createNotesFile() {

        try {

            FileOutputStream fileOs = ctx.openFileOutput(NOTES, Context.MODE_APPEND);
            fileOs.close();

        } catch (Exception e) {


        }
    }

    public void createSubpointFile() {
        FileInputStream inputStream = null;
        try {
            FileOutputStream fOut = ctx.openFileOutput(filename, Context.MODE_APPEND);
            fOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceFileWithNotes(String[] notes) {
        try {
            int len = notes.length;
            //stringWithNotes=trimEmptyArray(notes);
            Log.e("Test not przed rep",Arrays.toString(notes));
            OutputStreamWriter outputStreamWriter;
            if(len>0){
                 outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(NOTES, Context.MODE_PRIVATE));
                for (int i = 0; i < len; i++) {
                    outputStreamWriter.write(notes[i] + "\n");
                }
            }else{
                 outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(NOTES, Context.MODE_PRIVATE));
            }


            outputStreamWriter.close();
            // Log.e("Test not po rep",Arrays.toString(getArrayWithNotes()));
        } catch (Exception e) {
        }
    }

    public void replaceFileWithSubpoints(String[] subpoints) {
        try {
            int len = subpoints.length;
           // stringWithSubpoints=trimEmptyArray(subpoints);
            Log.e("Test sub przed rep",Arrays.toString(stringWithSubpoints));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(filename, Context.MODE_PRIVATE));
            for (int i = 0; i < len; i++) {
                outputStreamWriter.write(subpoints[i] + "\n");
            }
           Log.e("Test sub po rep",Arrays.toString(getArrayWithSubpoints()));
            outputStreamWriter.close();
        } catch (Exception e) {
        }
    }

    public void deleteNote(String name) {
        String []notesArray=getArrayWithNotes();
        for(int i=0;i<notesArray.length;i++)
            if(notesArray[i].equals(name))
                notesArray[i]="null";

        notesArray=trimEmptyArray(notesArray);
        Log.e("Test notesArray po trim",Arrays.toString(notesArray));

            setStringWithNotesArray(notesArray);

            replaceFileWithNotes(notesArray);
        ctx.deleteFile(name);
        if(getArrayWithNotes()!=null)
        Log.e("Test not  po usunieciu",Arrays.toString(getArrayWithNotes()));


    }

    public void deleteAllFiles() {
        int len = getArrayWithNotes().length;
        String[] array = getArrayWithNotes();
        ctx.deleteFile(NOTES);

        for (int i = 0; i < len; i++) {
            File f = ctx.getFilesDir();
            try {
                f = new File(f.getCanonicalPath() + array[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (f.exists() && !f.isDirectory())
                ctx.deleteFile(array[i]);
        }

    }

    public String getFilename() {
        return this.filename;
    }

    public String[] trimEmptyArray(String[] array) {
        int len = 0;
        for (int i = 0; i < array.length; i++)
            if (array[i] != null)
                if (!array[i].equals("") && !array[i].equals("0") && !array[i].equals("null"))
                    len++;
        String[] newArray = new String[len];
        for (int i = 0; i < len; i++)
            newArray[i] = array[i];
        return newArray;
    }

    public void setSavingName(String name) {
        NOTES = name;
    }

    public String getNOTES() {
        return NOTES;
    }
}
