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

        //Spane i jemu podobne
        if(spaneCheckBox.isChecked()) {
            app.setOption("spane","true");
        }else{
            app.setOption("spane","false");
        }
        if(sliderSpaneCheckBox.isChecked()){
            app.setOption("sliderSpane","true");
        }else{
            app.setOption("sliderSpane","false");
        }
        if(radioSpaneCheckBox.isChecked()){
            app.setOption("radioSpane","true");
        }else{
            app.setOption("radioSpane","false");
        }
        if(imageSpaneCheckBox.isChecked()){
            app.setOption("imageSpane","true");
        }else{
            app.setOption("imageSpane","false");
        }

        //panas i jemu podobne
        if(panasCheckBox.isChecked()) {
            app.setOption("panas","true");
        }else{
            app.setOption("panas","false");
        }
        if(sliderPanasCheckBox.isChecked()){
            app.setOption("sliderPanas","true");
        }else{
            app.setOption("sliderPanas","false");
        }
        if(radioPanasCheckBox.isChecked()){
            app.setOption("radioPanas","true");
        }else{
            app.setOption("radioPanas","false");
        }
        if(imagePanasCheckBox.isChecked()){
            app.setOption("imagePanas","true");
        }else{
            app.setOption("imagePanas","false");
        }


        //pam i jemu podobne
        if(pamCheckBox.isChecked()){
            app.setOption("pam","true");
        }else{
            app.setOption("pam","false");
        }


        //setCheckBoxes();
    }
    public void setCheckBoxes(){
        if(app.getOptionValue("spane").contentEquals("true")){
            spaneCheckBox.setChecked(true);
        }else{
            spaneCheckBox.setChecked(false);
        }
        if(app.getOptionValue("sliderSpane").contentEquals("true")){
            sliderSpaneCheckBox.setChecked(true);
        }else{
            sliderSpaneCheckBox.setChecked(false);
        }
        if(app.getOptionValue("radioSpane").contentEquals("true")){
            radioSpaneCheckBox.setChecked(true);
        }else{
            radioSpaneCheckBox.setChecked(false);
        }
        if(app.getOptionValue("imageSpane").contentEquals("true")){
            imageSpaneCheckBox.setChecked(true);
        }else{
            imageSpaneCheckBox.setChecked(false);
        }
        if(app.getOptionValue("panas").contentEquals("true")){
            panasCheckBox.setChecked(true);
        }else{
            panasCheckBox.setChecked(false);
        }
        if(app.getOptionValue("sliderPanas").contentEquals("true")){
            sliderPanasCheckBox.setChecked(true);
        }else{
            sliderPanasCheckBox.setChecked(false);

        }
        if(app.getOptionValue("radioPanas").contentEquals("true")){
            radioPanasCheckBox.setChecked(true);
        }else{
            radioPanasCheckBox.setChecked(false);
        }
        if(app.getOptionValue("imagePanas").contentEquals("true")){
            imagePanasCheckBox.setChecked(true);
        }else{
            imagePanasCheckBox.setChecked(false);

        }
        if(app.getOptionValue("pam").contentEquals("true")){
            pamCheckBox.setChecked(true);
        }else{
            pamCheckBox.setChecked(false);
        }
    }
    public void goToOptions(View view){
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

}
