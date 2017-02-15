package com.example.tomek.moodestimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Special extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
    }
    public void backToMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
