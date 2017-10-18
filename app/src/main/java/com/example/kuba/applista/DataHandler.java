package com.example.kuba.applista;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
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
    private int HOWMANY=30;
    public String[] stringWithNotes=new String[HOWMANY];
    public String[] stringWithSubpoints=new String[HOWMANY];

    DataHandler(String fn,Context c,String[] s){

        filename=fn;
        ctx=c;
        stringWithSubpoints=s;
        createNotesFile();
        createSubpointFile();
        for(int i=0;i<HOWMANY;i++)
            stringWithNotes[i]="0";
    }
    DataHandler(Context c){
            ctx = c;
            createNotesFile();
    }
    DataHandler(String fn,Context c){
        ctx = c;
        filename=fn;
        createNotesFile();
    }
    public void addFilename(String file){
        filename=file;
    }
    public void addStringWithSubpointsArray(String[] array){stringWithSubpoints=array;}
    public void addStringWithNotesArray(String[] array)
    {stringWithNotes=array;}


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
    public void replaceFileWithSubpoints(){
        try {
            String smth;
            outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            stringWithSubpoints=getArrayWithSubpoints();
            for(int i=0;i<stringWithSubpoints.length;i++){
                smth=stringWithSubpoints[i]+"\n";
                outputStream.write(smth.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void replaceFileWithNotes(){
        try {
            String smth;
            outputStream = ctx.openFileOutput(NOTES, Context.MODE_PRIVATE);
            stringWithNotes=getArrayWithNotes();
            for(int i=0;i<stringWithNotes.length;i++){
                smth=stringWithNotes[i]+"\n";
                outputStream.write(smth.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void appendToFileWithNotes(){
        try {
            String smth=filename+"\n";
            outputStream = ctx.openFileOutput(NOTES, Context.MODE_APPEND);
            outputStream.write(smth.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("TAG","CRASH w append: "+ Arrays.toString(e.getStackTrace()));
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
        String[] array=stringWithSubpoints;
        array=trimEmptyArray(array);
        return array;
    }

    public String[] getArrayWithNotes(){
        readFromFileWithNotes();
        String[] array=stringWithNotes;
        array=trimEmptyArray(array);
        return array;
    }

    public void createNotesFile(){

        try {

                FileOutputStream fileOs = ctx.openFileOutput(NOTES, Context.MODE_APPEND);
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
        List<String> notes = new ArrayList<String>(Arrays.asList(getArrayWithNotes()));
        notes.remove(name);
        addStringWithNotesArray(notes.toArray(stringWithNotes));
        replaceFileWithNotes();

        try {
            ctx.deleteFile(name);

        } catch (Exception e) {
            Log.e("TAG", "Cos sie nie usuwa:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deleteAllFiles() {
        int len=getArrayWithNotes().length;
        String[] array=getArrayWithNotes();
        ctx.deleteFile(NOTES);

        for(int i=0;i<len;i++){
            File f = ctx.getFilesDir();
            try {
                f=new File(f.getCanonicalPath()+array[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(f.exists() && !f.isDirectory())
                ctx.deleteFile(array[i]);
        }

    }
    public String getFilename(){
        return this.filename;
    }
    public String[] trimEmptyArray(String[] array){
        int len=0;
        for(int i=0;i<array.length;i++)
            if(array[i]!=null)
                if(!array[i].equals("")&&!array[i].equals("0")&&!array[i].equals("null"))
                    len++;
        String[] newArray=new String[len];
        for(int i=0;i<len;i++)
            newArray[i]=array[i];
        return newArray;
    }
}
