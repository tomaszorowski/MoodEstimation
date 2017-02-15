package com.example.tomek.moodestimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void goToManageDisplayedScales(View view){
        Intent intent = new Intent(this, ManageDisplayedScales.class);
        startActivity(intent);
    }


    public void goToMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }


}
