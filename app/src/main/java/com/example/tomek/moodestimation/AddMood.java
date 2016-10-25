package com.example.tomek.moodestimation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class AddMood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
    }
    //sprawdza które scale zaznaczone w opcjach i tworzy widok
    public void choseProperScales(){}
    public void goToSpaneActivity(View view){
        Intent intent = new Intent(this, Spane.class);
        startActivity(intent);
    }
    public void goToPanas(View view){
        Intent intent = new Intent(this, Panas.class);
        startActivity(intent);
    }
    public void goToPam(View view){
        Intent intent = new Intent(this, Pam.class);
        startActivity(intent);
    }

    public void goToSpecial(View view){
        Intent intent = new Intent(this, Special.class);
        startActivity(intent);
    }
    public void goToMenuActivity(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
