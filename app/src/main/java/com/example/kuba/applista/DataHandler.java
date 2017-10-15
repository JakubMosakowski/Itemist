package com.example.kuba.applista;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Kuba on 11.10.2017.
 */

public class DataHandler extends Application {
    private String filename;
    private String NOTES="notes.txt";

    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private Context ctx;
    public String[] stringWithNotes=new String[50];//TODO ZMIEN TE 50
    public String[] stringWithSubpoints=null;

    DataHandler(String fn,Context c,String[] s){
        try {
        filename=fn;
        ctx=c;
        stringWithSubpoints=s;
        createNotesFile();
        createSubpointFile();


        for(int i=0;i<50;i++)
            stringWithNotes[i]="0";//TODO USUN
    }catch (Exception e){
        Log.e("TAG",NOTES);
    }
    }
    DataHandler(Context c){
        try {
            ctx = c;

            //createNotesFile();
        }catch (Exception e){
            Log.e("TAG",NOTES);
        }

    }
    public void addFilename(String file){
        filename=file;
    }
    public void addStringArray(String[] array){stringWithSubpoints=array;}


    public void writeToFileWithSubpoints(){
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
    public void writeToFileWithNotes(){
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
            inputStream = ctx.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            int i=0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
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

           // Toast.makeText(ctx, "TO SIĘ ROBI W TRY", Toast.LENGTH_SHORT).show();//TODO usun
        } catch (Exception e) {
            Log.e("TAG","CRASH w read StackTrace: "+ Arrays.toString(e.getStackTrace()));
            //Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();//TODO usun
        }

    }

    public String[] returnArrayWithSubpoints(){
        readFromFileWithSubpoints();
        return stringWithSubpoints;
    }

    public String[] returnArrayWithNotes(){
        readFromFileWithNotes();
        return stringWithNotes;
    }

    public void createNotesFile(){

        try {

                FileOutputStream fileOs = ctx.openFileOutput(NOTES, ctx.MODE_APPEND);
                fileOs.close();

           /* Log.e("TAG","CRASH w createNote write:"+file.canWrite());
            Log.e("TAG","CRASH w createNote read:"+ file.canRead());
            Log.e("TAG","CRASH w createNote path: "+  file.getCanonicalPath());*/

        } catch (Exception e) {
            Log.e("TAG","CRASH w createNote "+ Arrays.toString(e.getStackTrace()));

        }
    }
    public void createSubpointFile(){
        FileInputStream inputStream = null;
        try {
            FileOutputStream fOut = ctx.openFileOutput(filename,Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //TODO zmien na stringi
    /*public void deleteNote(String name){
        //najpierw usuń z pliku z notatkami, potem cały plik z notatką.
        readFromFileWithNotes();
        adapterWithNotes.remove(name);
        writeToFileWithNotes();

        try {
            ctx.deleteFile(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
