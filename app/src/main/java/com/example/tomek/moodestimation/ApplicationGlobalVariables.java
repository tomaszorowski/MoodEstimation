package com.example.tomek.moodestimation;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by Tomek on 10/22/2016.
 */

public class ApplicationGlobalVariables extends Application {
    private static ApplicationGlobalVariables sInstance;

    SharedPreferences mPref;

    public static ApplicationGlobalVariables getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    private void initializeInstance() {
        mPref = this.getApplicationContext().getSharedPreferences("pref_key", MODE_PRIVATE);

    }


    public boolean isFirstRun() {
        return mPref.getBoolean("is_first_run", true);
    }

    public void setRunned() {
        SharedPreferences.Editor edit = mPref.edit();
        edit.putBoolean("is_first_run", false);
        edit.commit();
    }

    public void setOption(String key, String value){
        SharedPreferences.Editor edit = mPref.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public String getOptionValue(String key){

        return mPref.getString(key,"Brak opcji dla klucza: "+ key);
    }

}