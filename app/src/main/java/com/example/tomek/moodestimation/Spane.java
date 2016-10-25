package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Spane extends AppCompatActivity {
    private Map<String,Integer> feelings=new HashMap<String,Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spane);
        changeSeekBar(R.id.seekBarPositively, R.id.textPositively, "Positively");
        changeSeekBar(R.id.seekBarNegative, R.id.textNegative,"Negative");
        changeSeekBar(R.id.seekBarGood, R.id.textGood,"Good");
        changeSeekBar(R.id.seekBarBad, R.id.textBad,"Bad");
        changeSeekBar(R.id.seekBarPleasantly, R.id.textPleasantly,"Pleasantly");
        changeSeekBar(R.id.seekBarSad, R.id.textSad,"Sad");
        changeSeekBar(R.id.seekBarScared, R.id.textScared,"Scared");
        changeSeekBar(R.id.seekBarJoyful, R.id.textJoyful,"Joyful");
        changeSeekBar(R.id.seekBarGlad, R.id.textGlad, "Glad");


    }
    public void saveSpane(View view){
        DatabaseHelper dh = new DatabaseHelper(this);
        SQLiteDatabase db = dh.getWritableDatabase();
        for(Map.Entry<String, Integer> entry:feelings.entrySet()){
            ContentValues values = new ContentValues();
            values.put("NAME", entry.getKey());
            values.put("PERCENT_VALUE", entry.getValue());
            db.insertOrThrow("Feeling",null,values);
        }
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
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
                        feelings.put(feelingName,progress);

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
