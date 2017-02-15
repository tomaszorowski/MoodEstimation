package com.example.tomek.moodestimation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText nameEt;
    private EditText surnameEt;
    private EditText emailEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private EditText repeatPasswordEt;
    private String name,surname,email,username,password,repeatPassword;
public SignUp(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }
    public void backToSignIn(View view){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
    public void save(){
        DatabaseHelper dh = new DatabaseHelper(this);
        SQLiteDatabase db = dh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NAME", name);
            values.put("SURNAME", surname);
            values.put("EMAIL", email);
            values.put("USERNAME", username);
            values.put("PASSWORD", password);
            values.put("REPEAT_PASSWORD", repeatPassword);

        db.insertOrThrow("Patient",null,values);

    }
    public void signUp(View view){
        int counter = 0;
        nameEt = (EditText)findViewById(R.id.name_et);
        surnameEt = (EditText)findViewById(R.id.surname_et);
        emailEt = (EditText)findViewById(R.id.email_et);
        usernameEt = (EditText)findViewById(R.id.username_et);
        passwordEt = (EditText)findViewById(R.id.password_et);
        repeatPasswordEt = (EditText)findViewById(R.id.repeat_password_et);
        name = nameEt.getText().toString();
        surname = surnameEt.getText().toString();
        email = emailEt.getText().toString();
        username = usernameEt.getText().toString();
        password = passwordEt.getText().toString();
        repeatPassword = repeatPasswordEt.getText().toString();
        if(TextUtils.isEmpty(username)){
            usernameEt.setError("Username can't be empty");
        }
        if(TextUtils.isEmpty(name)){
            nameEt.setError("Name can't be empty");
            counter++;
        }
        if(TextUtils.isEmpty(surname)){
            surnameEt.setError("Surname can't be empty");
            counter++;
        }
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Email can't be empty");
            counter++;
        }
        if(password.length()<6){
            passwordEt.setError("Password to short");
        }
        if(TextUtils.isEmpty(password)){
            passwordEt.setError("Password can't be empty");
            counter++;
        }
        if(!password.equals(repeatPassword)){
            repeatPasswordEt.setError("Password does not match");
        }
       if(counter==0){
           save();
           Intent intent = new Intent(this, SignIn.class);
           startActivity(intent);
       }

    }
}
