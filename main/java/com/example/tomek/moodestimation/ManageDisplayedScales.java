package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class ManageDisplayedScales extends AppCompatActivity {
    private String spane,panas,pam,image,radio,slider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_displayed_scale);
        final CheckBox panasCheckBox = (CheckBox) findViewById(R.id.panasCheckBox);
        final CheckBox pamCheckBox = (CheckBox) findViewById(R.id.pamCheckBox);

    }
    public void saveManagement(View view){
        DatabaseHelper dh = new DatabaseHelper(this);
        SQLiteDatabase db = dh.getWritableDatabase();
        final CheckBox spaneCheckBox = (CheckBox) findViewById(R.id.spaneCheckBox);
        final CheckBox sliderSpaneCheckBox = (CheckBox) findViewById(R.id.sliderSpaneCheckBox);
        final CheckBox radioSpaneCheckBox = (CheckBox) findViewById(R.id.radioSpaneCheckBox);
        final CheckBox imageSpaneCheckBox = (CheckBox) findViewById(R.id.imageSpaneCheckBox);
        if(spaneCheckBox.isChecked()) {
            if(sliderSpaneCheckBox.isChecked())slider="true";
            if(radioSpaneCheckBox.isChecked())radio="true";
            if(imageSpaneCheckBox.isChecked())image="true";
            ContentValues values = new ContentValues();
            values.put("SCALE_NAME", "Spane");
            values.put("SLIDER", slider);
            values.put("RADIO", radio);
            values.put("IMAGE", image);
            db.insertOrThrow("Option", null, values);
        }
    }
    public void goToOptions(View view){
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

}
