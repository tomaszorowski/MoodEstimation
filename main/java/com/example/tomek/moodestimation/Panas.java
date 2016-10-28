package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Panas extends AppCompatActivity {
    private Map<String,Integer> feelings=new HashMap<String,Integer>();
    private int feelingPosition=0,feelingPosition1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panas);
        feelingPosition= findFeelingsPosition();
        changeSeekBar(R.id.seekBarInterested, R.id.textInterested, "Interested");
        changeSeekBar(R.id.seekBarDistressed, R.id.textDistressed,"Distressed");
        changeSeekBar(R.id.seekBarExcited, R.id.textExcited,"Excited");
        changeSeekBar(R.id.seekBarRestless, R.id.textRestless,"Restless");
        changeSeekBar(R.id.seekBarStrong, R.id.textStrong,"Strong");
        changeSeekBar(R.id.seekBarScared, R.id.textScared,"Scared");
        changeSeekBar(R.id.seekBarEnemy, R.id.textEnemy,"Enemy");
        changeSeekBar(R.id.seekBarEnthusiastic, R.id.textEnthusiastic, "Enthusiastic");
        changeSeekBar(R.id.seekBarPetulant, R.id.textPetulant, "Petulant");
        changeSeekBar(R.id.seekBarAlert, R.id.textAlert, "Alert");
        changeSeekBar(R.id.seekBarEmbarassed, R.id.textEmbarassed, "Embarassed");
        changeSeekBar(R.id.seekBarInspired, R.id.textInspired, "Inspired");
        changeSeekBar(R.id.seekBarAngry, R.id.textAngry, "Angry");
        changeSeekBar(R.id.seekBarDetermined, R.id.textDetermined, "Determinated");
        changeSeekBar(R.id.seekBarFocused, R.id.textFocused, "Focused");
        changeSeekBar(R.id.seekBarJittery, R.id.textJittery, "Jittery");
        changeSeekBar(R.id.seekBarActive, R.id.textActive, "Active");
    }
    public int findFeelingsPosition(){
        DatabaseHelper dh = new DatabaseHelper(this);
        Cursor cursor = dh.getAllFeelings();
        cursor.moveToLast();
        return cursor.getInt(0);

    }
    public void savePanas(View view){
        //pobieranie id uzytkownika
        DatabaseHelper dh = new DatabaseHelper(this);
        SQLiteDatabase db = dh.getWritableDatabase();
        //dodawanie feelingów wczytanych z seekbarów
        for(Map.Entry<String, Integer> entry:feelings.entrySet()){
            ContentValues values = new ContentValues();
            values.put("NAME", entry.getKey());
            values.put("PERCENT_VALUE", entry.getValue());
            db.insertOrThrow("Feeling",null,values);
        }
        feelingPosition1= findFeelingsPosition();
        String str= "";
        int [] feelingsId = new int [feelingPosition1-feelingPosition];
        int j = 0;
        for(int i =feelingPosition;i<feelingPosition1;i++){
            feelingsId[j]=i;
            str+=feelingsId[j]+"|";
            j++;
        }
        //dodawanie skali wraz z listą feelingów
        ContentValues values = new ContentValues();
        values.put("ID_FEELING",str);
        values.put("DATE", getDateTime());
        values.put("ID_PATIENT", 0);
        db.insertOrThrow("Scale", null, values);
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void changeSeekBar(int seekBarId, int textViewId, final String feelingName){
        final SeekBar seekBarTemp = (SeekBar)findViewById(seekBarId);
        final TextView textViewTemp = (TextView)findViewById(textViewId);
        textViewTemp.setText(feelingName + " " + seekBarTemp.getProgress() + " / " + seekBarTemp.getMax());

        seekBarTemp.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        textViewTemp.setText(feelingName + " " + progress + " / " + seekBarTemp.getMax());
                        feelings.put(feelingName, progress);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewTemp.setText(feelingName + " " + progressValue + " / " + seekBarTemp.getMax());
                        feelings.put(feelingName, progressValue);
                    }
                }
        );
    }
}
