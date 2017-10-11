package com.example.kuba.applista;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Kuba on 11.10.2017.
 */

public class DataHandler {
    private String filename;
    private String NOTES="notes";
    private ArrayAdapter<String> adapterWithSupoints;
    private ArrayAdapter<String> adapterWithNotes;
    private FileOutputStream outputStream;
    private Context ctx;

    DataHandler(String fn,ArrayAdapter<String> a,Context c){
        filename=fn;
        adapterWithSupoints=a;
        ctx=c;
    }
    DataHandler(Context c){
        ctx=c;
    }
    public void addFilename(String file){
        filename=file;
    }


    public void writeToFileWithSubpoints(){
        try {
            outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            for(int i=0;i<adapterWithSupoints.getCount();i++){
                String smth=adapterWithSupoints.getItem(i).toString()+"\n";
                outputStream.write(smth.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeToFileWithNotes(){
        try {
            outputStream = ctx.openFileOutput(NOTES, Context.MODE_PRIVATE);
            for(int i=0;i<adapterWithNotes.getCount();i++){
                outputStream.write(filename.getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFileWithSubpoints(){
        FileInputStream inputStream = null;
        try {
            inputStream = ctx.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                adapterWithSupoints.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFileWithNotes(){
        FileInputStream inputStream = null;
        try {
            inputStream = ctx.openFileInput(NOTES);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                adapterWithNotes.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayAdapter<String> returnAdapterWithSubpoints(){
        readFromFileWithSubpoints();
        return adapterWithSupoints;
    }

    public ArrayAdapter<String>  returnAdapterWithNotes(){
        readFromFileWithNotes();
        return adapterWithNotes;
    }

    public void deleteNote(String name){
        //najpierw usuń z pliku z notatkami, potem cały plik z notatką.
        readFromFileWithNotes();
        adapterWithNotes.remove(name);
        writeToFileWithNotes();

        try {
            ctx.deleteFile(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
