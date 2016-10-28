package com.example.tomek.moodestimation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MyApplicationLifeCycleController myApp = (MyApplicationLifeCycleController)getApplicationContext();
    }
    public void goToAddMoodActivity(View view){
        Intent intent = new Intent(this, AddMood.class);
        startActivity(intent);
    }
    public void goToSignIn(View view){

        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
    public void goToOptions(View view){

        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void exitApplication(View view){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
