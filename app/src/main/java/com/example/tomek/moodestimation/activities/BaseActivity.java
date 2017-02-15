package com.example.tomek.moodestimation.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tomek.moodestimation.utils.AuthorizationException;
import com.example.tomek.moodestimation.utils.Core;

public class BaseActivity extends Activity{

    public static final int AUTHORIZATION_REQUEST_CODE = 16001;

    protected void processAuthoorizationException(AuthorizationException exception) {
        onAuthoorizationException();
        startAuthorizationActivityForResult();
    }

    protected void checkAuthorization() {
        if(!Core.getInstance().checkIfTokenIsValid(getApplicationContext())){
           processAuthoorizationException(new AuthorizationException("Brak waznego tokenu"));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        checkAuthorization();
    }
    protected void onAuthoorizationException() {}
    protected void onAuthorize() {
        Core.setToken(Core.getInstance().getTokenFromSharedPreferences(getApplicationContext()));
    }
    protected void onAuthorizationFailed() {}



    protected void startAuthorizationActivityForResult() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, AUTHORIZATION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTHORIZATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                onAuthorize();
            } else {
                onAuthorizationFailed();
            }
        }
    }
    protected boolean checkIsTokenValid(){
        if(Core.getInstance().checkIfTokenIsValid(getApplicationContext())){
            Core.setToken(Core.getInstance().getTokenFromSharedPreferences(getApplicationContext()));
            return true;
        }else{
            return false;
        }

    }

}
