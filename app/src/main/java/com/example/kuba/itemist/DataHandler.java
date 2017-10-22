package com.example.kuba.itemist;

import android.app.Application;
import android.content.Context;

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
            outputStream = ctx.openFileOutput(filename, Context.MODE_APPEND);
            for (int i = 0; i < stringWithSubpoints.length; i++) {
                smth = stringWithSubpoints[i] + "\n";
                outputStream.write(smth.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendToFileWithNotes() {
        try {
            String smth = filename + "\n";
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(NOTES, Context.MODE_APPEND));
            outputStreamWriter.write(smth);
            outputStreamWriter.close();
        } catch (Exception e) {

        }
    }

    public void readFromFileWithSubpoints() {
        try {
            FileInputStream fis = ctx.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                stringWithSubpoints[i] = line;
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFileWithNotes() {
        try {
            InputStream inputStream = ctx.openFileInput(NOTES);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                int i = 0;
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringWithNotes[i] = receiveString;
                    i++;
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(NOTES, Context.MODE_PRIVATE));
            for (int i = 0; i < len; i++) {
                outputStreamWriter.write(notes[i] + "\n");
            }
            outputStreamWriter.close();
        } catch (Exception e) {
        }
    }

    public void replaceFileWithSubpoints(String[] subpoints) {
        try {
            int len = subpoints.length;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(filename, Context.MODE_PRIVATE));
            for (int i = 0; i < len; i++) {
                outputStreamWriter.write(subpoints[i] + "\n");
            }
            outputStreamWriter.close();
        } catch (Exception e) {
        }
    }

    public void deleteNote(String name) {
        List<String> notes = new ArrayList<String>(Arrays.asList(getArrayWithNotes()));
        notes.remove(name);
        setStringWithNotesArray(notes.toArray(stringWithNotes));
        replaceFileWithNotes(notes.toArray(stringWithNotes));

        try {
            ctx.deleteFile(name);

        } catch (Exception e) {
        }
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
