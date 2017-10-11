/*
package com.example.kuba.applista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.sql.Statement;

*/
/**
 * Created by Kuba on 10.10.2017.
 *//*


public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_database";


    //table name
    private static final String TABLE_NOTES_NAME = "Notes";
    private static final String TABLE_SUBPOINTS_NAME= "Subpoints "
    // Contacts Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_SUBPOINT = "subpoint";
    private static final String KEY_NOTES_NAME="note";
    private static final String KEY_NOTE_ID="NoteID";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_NOTES_NAME + " TEXT)";
        String CREATE_TABLE_SUBPOINTS = "CREATE TABLE " + TABLE_SUBPOINTS_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_NOTE_ID+" INTEGER FOREIGN KEY REFERENCES "+TABLE_NOTES_NAME+"("+KEY_ID+"),"+KEY_SUBPOINT + " TEXT)";


        db.execSQL(CREATE_TABLE_NOTES);

        db.execSQL(CREATE_TABLE_SUBPOINTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES_NAME);
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBPOINTS_NAME);

        // Create tables again
        onCreate(db);
    }

    public void addNote(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTES_NAME,name);
        db.insert(TABLE_SUBPOINTS_NAME,null,values);

    }
    public void removeNote(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }

    public void addSubpoint(String subpoint,int note_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUBPOINT,subpoint);
        values.put(KEY_NOTE_ID,note_id);
        db.insert(TABLE_SUBPOINTS_NAME,null,values);
    }
    public int findNoteTable(String name){
        if(checkIfTableExists(name)){

        }

    }

    public boolean checkIfTableExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+name+" FROM "+TABLE_NOTES_NAME, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public void removeSubpoint(String subpoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }

}
*/
