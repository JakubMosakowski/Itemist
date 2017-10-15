package com.example.kuba.applista;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kuba on 11.10.2017.
 */

public class DataHandler extends Application {
    private String filename;
    private String NOTES="notes.txt";

    private FileOutputStream outputStream;
    private Context ctx;
    public String[] stringWithNotes=new String[50];//TODO ZMIEN TE 50
    public String[] stringWithSubpoints=new String[50];

    DataHandler(String fn,Context c,String[] s){

        filename=fn;
        ctx=c;
        stringWithSubpoints=s;
        createNotesFile();
        createSubpointFile();
        for(int i=0;i<50;i++)
            stringWithNotes[i]="0";
    }
    DataHandler(Context c){
            ctx = c;
            createNotesFile();
    }
    DataHandler(Context c,String fn){
        ctx = c;
        filename=fn;
        createNotesFile();
    }
    public void addFilename(String file){
        filename=file;
    }
    public void addStringArray(String[] array){stringWithSubpoints=array;}


    public void appendToFileWithSubpoints(){
        try {
            String smth;
            outputStream = ctx.openFileOutput(filename, Context.MODE_APPEND);
            for(int i=0;i<stringWithSubpoints.length;i++){
                 smth=stringWithSubpoints[i]+"\n";
                outputStream.write(smth.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void appendToFileWithNotes(){
        try {
            String smth="\n"+filename;
            outputStream = ctx.openFileOutput(NOTES, Context.MODE_APPEND);
            outputStream.write(smth.getBytes());

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFileWithSubpoints(){
        try {
            FileInputStream fis=ctx.openFileInput(filename);
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            int i=0;
            String line;
            while ((line = br.readLine()) != null) {
                stringWithSubpoints[i]=line;
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFileWithNotes(){
        try {
            FileInputStream fis=ctx.openFileInput(NOTES);
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            String s;
            int i=0;
            while (((s=br.readLine())!=null)) {
                stringWithNotes[i]=s;
                i++;
            }
        } catch (Exception e) {
            Log.e("TAG","CRASH w read StackTrace: "+ Arrays.toString(e.getStackTrace()));
        }

    }

    public String[] getArrayWithSubpoints(){
        readFromFileWithSubpoints();
        return stringWithSubpoints;
    }

    public String[] getArrayWithNotes(){
        readFromFileWithNotes();
        return stringWithNotes;
    }

    public void createNotesFile(){

        try {

                FileOutputStream fileOs = ctx.openFileOutput(NOTES, ctx.MODE_APPEND);
                fileOs.close();

        } catch (Exception e) {
            Log.e("TAG","CRASH w createNote "+ Arrays.toString(e.getStackTrace()));

        }
    }
    public void createSubpointFile(){
        FileInputStream inputStream = null;
        try {
            FileOutputStream fOut = ctx.openFileOutput(filename,Context.MODE_APPEND);
            fOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(String name){
        //najpierw usuń z pliku z notatkami, potem cały plik z notatką.
        List<String> notes = new ArrayList<String>(Arrays.asList(getArrayWithNotes()));
        notes.remove(name);
        appendToFileWithNotes();

        try {
            ctx.deleteFile(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
