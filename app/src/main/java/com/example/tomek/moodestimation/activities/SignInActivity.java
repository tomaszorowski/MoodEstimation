package com.example.tomek.moodestimation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.XmlWebService.WebService;

import CommonObjects.CommonTokens;

public class SignInActivity extends Activity implements WebService.RequestCallback {
    private EditText usernameEt;
    private EditText passwordEt;
    private String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView textView ;
        textView = (TextView) findViewById(R.id.registerTitle);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.invalidate();
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void signIn(View view) {
        usernameEt = (EditText) findViewById(R.id.surname);
        passwordEt = (EditText) findViewById(R.id.password_et_si);
        username = usernameEt.getText().toString();
        password = passwordEt.getText().toString();


        int counter = 0;
        if (TextUtils.isEmpty(username)) {
            counter += 1;
            usernameEt.setError("Username can't be empty");
        }
        if (TextUtils.isEmpty(password)) {
            counter += 1;
            passwordEt.setError("Password can't be empty");
        }

        if (counter == 0) {

            String[] stringArray = {"login", username, "password", password};
            Core.getInstance().authorize(stringArray,this);

        }

    }


    @Override
    public void OnSuccess(Response response) {
      String stringToken=response.getBody().toString();
       Log.i("ONSUCCES -->", stringToken);
        CommonTokens commonToken = (CommonTokens) response.getBody();
        Core.getInstance().saveTokenInSharedPreferences(commonToken, getApplicationContext());
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnError(Response response) {
        Context context = getApplicationContext();
        CharSequence text = response.getResponseMessage();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void OnException(Exception exception) {

        Log.i("ONEXCEPTION -->", "full exception");
    }

    @Override
    public void onBackPressed() {}

}
