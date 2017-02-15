package com.example.tomek.moodestimation.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;

import CommonObjects.CommonTokens;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText firstNameTextView;
    private EditText surnameTextView;
    private EditText emailTextView;
    private EditText loginTextView;
    private EditText passwordTextView;
    private EditText repeatPasswordTextView;
    private String firstName,surname,email,login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        generateButtons();
    }
    private void exit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void validation(){
        firstNameTextView = (EditText) findViewById(R.id.firstName);
        surnameTextView = (EditText) findViewById(R.id.surname);
        emailTextView = (EditText) findViewById(R.id.email);
        loginTextView = (EditText) findViewById(R.id.login);
        passwordTextView = (EditText) findViewById(R.id.passsword);
        repeatPasswordTextView = (EditText) findViewById(R.id.repeatPassword);

        firstName = firstNameTextView.getText().toString();
        surname = surnameTextView.getText().toString();
        email = emailTextView.getText().toString();
        login = loginTextView.getText().toString();
        password = passwordTextView.getText().toString();
            int counter = 0;
            if (TextUtils.isEmpty(firstName)) {
                counter += 1;
                firstNameTextView.setError("First name can't be empty");
            }
            if (TextUtils.isEmpty(surname)) {
                counter += 1;
                surnameTextView.setError("Surname can't be empty");
            }
        if (TextUtils.isEmpty(email)) {
            counter += 1;
            emailTextView.setError("Email can't be empty");
        }
        if (TextUtils.isEmpty(login)) {
            counter += 1;
            loginTextView.setError("Login can't be empty");
        }
        if (TextUtils.isEmpty(password)) {
            counter += 1;
            passwordTextView.setError("Password name can't be empty");
        }
        if (TextUtils.isEmpty(repeatPasswordTextView.getText().toString())) {
            counter += 1;
            repeatPasswordTextView.setError("Reapeted password can't be empty");
        }
        if ((!TextUtils.isEmpty(repeatPasswordTextView.getText().toString())) && !password.equals(repeatPasswordTextView.getText().toString())) {
            counter += 1;
            repeatPasswordTextView.setError("Password don't match");
        }


            if (counter == 0) {

                String[] stringArray = {"firstName", firstName, "surname", surname, "email", email, "login", login, "password", password};
                Core.getInstance().register(stringArray,registerCallback);

            }
    }
    public WebService.RequestCallback registerCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            CommonTokens commonToken = (CommonTokens) response.getBody();
            Core.getInstance().clearSharedPreferences(getApplicationContext());
            Core.getInstance().saveTokenInSharedPreferences(commonToken, getApplicationContext());
            Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.register:{
                validation();

                break;}
            case R.id.exit: {
                exit();
                break;
            }

            default:
                break;

        }

    }
    private void generateButtons(){
        //declaring buttons
        final Button button = (Button) findViewById(R.id.register);
        final Button button1 =  (Button) findViewById(R.id.exit);
        //change menu style
        TextView textView ;
        textView = (TextView) findViewById(R.id.registerTitle);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.invalidate();
        //setting onClickListeners to buttons
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }
}
