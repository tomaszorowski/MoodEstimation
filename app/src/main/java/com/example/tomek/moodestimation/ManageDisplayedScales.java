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
    private CheckBox spaneCheckBox,sliderSpaneCheckBox,radioSpaneCheckBox,imageSpaneCheckBox,panasCheckBox,
            sliderPanasCheckBox,radioPanasCheckBox,pamCheckBox,imagePanasCheckBox;
    private ApplicationGlobalVariables app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_displayed_scale);
        spaneCheckBox = (CheckBox) findViewById(R.id.spaneCheckBox);
        sliderSpaneCheckBox = (CheckBox) findViewById(R.id.sliderSpaneCheckBox);
        radioSpaneCheckBox = (CheckBox) findViewById(R.id.radioSpaneCheckBox);
        imageSpaneCheckBox = (CheckBox) findViewById(R.id.imageSpaneCheckBox);
        panasCheckBox = (CheckBox) findViewById(R.id.panasCheckBox);
        sliderPanasCheckBox = (CheckBox) findViewById(R.id.sliderPanasCheckBox);
        radioPanasCheckBox = (CheckBox) findViewById(R.id.radioPanasCheckBox);
        imagePanasCheckBox = (CheckBox) findViewById(R.id.imagePanasCheckBox);
        pamCheckBox = (CheckBox) findViewById(R.id.pamCheckBox);
        app = (ApplicationGlobalVariables)getApplicationContext();
        setCheckBoxes();
    }
    public void saveManagement(View view){
        if(spaneCheckBox.isChecked()) {
            app.setOption("spane","true");
            if(sliderSpaneCheckBox.isChecked())app.setOption("sliderSpane","true");
            if(radioSpaneCheckBox.isChecked())app.setOption("radioSpane","true");
            if(imageSpaneCheckBox.isChecked())app.setOption("imageSpane","true");
        }
        if(panasCheckBox.isChecked()) {
            app.setOption("panas","true");
            if(sliderPanasCheckBox.isChecked())app.setOption("sliderPanas","true");
            if(radioPanasCheckBox.isChecked())app.setOption("radioPanas","true");
            if(imagePanasCheckBox.isChecked())app.setOption("imagePanas","true");
        }
        if(pamCheckBox.isChecked())app.setOption("pam","true");
    }
    public void setCheckBoxes(){
        if(app.getOptionValue("spane").contentEquals("true")){
            spaneCheckBox.setChecked(true);
            if(app.getOptionValue("sliderSpane").contentEquals("true"))sliderSpaneCheckBox.setChecked(true);
            if(app.getOptionValue("radioSpane").contentEquals("true"))radioSpaneCheckBox.setChecked(true);
            if(app.getOptionValue("imageSpane").contentEquals("true"))imageSpaneCheckBox.setChecked(true);
        }
        if(app.getOptionValue("panas").contentEquals("true")){
            spaneCheckBox.setChecked(true);
            if(app.getOptionValue("sliderPanas").contentEquals("true"))sliderPanasCheckBox.setChecked(true);
            if(app.getOptionValue("radioPanas").contentEquals("true"))radioPanasCheckBox.setChecked(true);
            if(app.getOptionValue("imagePanas").contentEquals("true"))imagePanasCheckBox.setChecked(true);
        }
        if(app.getOptionValue("pam").contentEquals("true")){
            pamCheckBox.setChecked(true);
        }
    }
    public void goToOptions(View view){
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

}
