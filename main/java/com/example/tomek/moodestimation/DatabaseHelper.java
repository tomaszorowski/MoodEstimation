package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Tomek on 10/8/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "PatientDatabase";
    private static final String patientTable = "Patient";
    private static final String scaleTable = "Scale";
    private static final String feelingTable = "Feeling";
    private static final String optionTable = "Option";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }


    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + feelingTable +
                "(ID_FEELING INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " PERCENT_VALUE INTEGER," +
                " IMAGE BLOB)");
        db.execSQL("create table " + patientTable +
                "(ID_PATIENT INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT, " +
                "SURNAME TEXT, " +
                "EMAIL TEXT, " +
                "USERNAME TEXT, " +
                "PASSWORD TEXT, " +
                "REPEAT_PASSWORD TEXT)");
        db.execSQL("create table " + optionTable +
                "(ID_OPTION INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SCALE_NAME TEXT, " +
                "SLIDER TEXT, " +
                "RADIO TEXT, " +
                "IMAGE TEXT )");
        db.execSQL("create table " + scaleTable +
                "(ID_SCALE INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ID_FEELING TEXT," +
                " DATE DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "ID_PATIENT, " +
                "FOREIGN KEY (ID_FEELING) REFERENCES Feeling (ID_FEELING)," +
                "FOREIGN KEY (ID_PATIENT) REFERENCES Patient (ID_PATIENT))");


    }
public Cursor getAllPatient(){
    String[] columns = {"ID_PATIENT","NAME","SURNAME","EMAIL", "USERNAME", "PASSWORD", "REPEAT_PASSWORD"};
    SQLiteDatabase db = getReadableDatabase();
    ArrayList<Cursor> cursors = new ArrayList<Cursor>();
    Cursor cursor = db.query("Patient",columns,null,null,null,null,null);
    return cursor;
}
    public Cursor getAllFeelings(){
        String[] columns = {"ID_FEELING","NAME","PERCENT_VALUE","IMAGE"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Feeling",columns,null,null,null,null,null);
        return cursor;
    }
    public Cursor getAllScales(){
        String[] columns = {"ID_SCALE","ID_FEELING","DATE","ID_PATIENT"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Scales",columns,null,null,null,null,null);
        return cursor;
    }
    public Cursor getLogged(){
        String[] columns = {"LOGGED"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Logged",columns,null,null,null,null,null);
        return cursor;
    }
    public Cursor getRegistredUser(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String TABLE="Patient";
        String[] FIELDS = {"ID_PATIENT"};
        String WHERE = "USERNAME='"+username+"' AND "+"PASSWORD='" + password+"'";
        Cursor cursor = db.query(TABLE,FIELDS,WHERE,null,null,null,null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ feelingTable);
        db.execSQL("DROP TABLE IF EXISTS"+ scaleTable);
        db.execSQL("DROP TABLE IF EXISTS"+ patientTable);
        db.execSQL("DROP TABLE IF EXISTS"+ optionTable);
        onCreate(db);
    }
    public void init(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", "test");
        values.put("SURNAME", "test");
        values.put("EMAIL", "test@gmail.com");
        values.put("USERNAME", "test");
        values.put("PASSWORD", "test123");
        values.put("REPEAT_PASSWORD", "test123");
        db.insertOrThrow(patientTable, null, values);

        ContentValues values1 = new ContentValues();
        values1.put("NAME", "Scary");
        values1.put("PERCENT_VALUE", 20);
        db.insertOrThrow(feelingTable, null, values);

    }

}
