package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

import java.io.Serializable;

public class SignIn extends AppCompatActivity {
    private EditText usernameEt;
    private EditText passwordEt;
    private String username,password;
    private PatientModel patient;
    private Boolean finded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        }



    public void findUser(String username, String password) {
        DatabaseHelper dh = new DatabaseHelper(this);
        Cursor cursor = dh.getAllPatient();
        String s;
        String b;
        if (cursor.getCount() == 0) {
            usernameEt.setError("First you must register");
        } else {

            cursor.moveToFirst();
                do{
                  s = cursor.getString(4);
                  b = cursor.getString(5);
                    if (username.equals(cursor.getString(4)) && password.equals(cursor.getString(5))) {
                        patient = new PatientModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                        finded = true;
                        break;
                    }
                    if(cursor.isLast()){
                        finded=false;
                        break;
                    }

            }while(cursor.moveToNext());
        }
    }
    public void signIn(View view){
        usernameEt = (EditText)findViewById(R.id.username_et_si);
        passwordEt = (EditText)findViewById(R.id.password_et_si);
        username = usernameEt.getText().toString();
        password = passwordEt.getText().toString();
        findUser(username,password);
        int counter =0;
        if(TextUtils.isEmpty(username)){
            counter+=1;
            usernameEt.setError("Username can't be empty");
    }
        if(TextUtils.isEmpty(password)){
            counter+=1;
            passwordEt.setError("Password can't be empty");
        }
        if(!finded){
            usernameEt.setError("User doesn't exists");
        }
        if(counter==0&&finded){
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }

    }
    public void goToSignUp(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
