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
    protected void populateButtons(int NUM_ROWS, int NUM_COLS, TableLayout table) {
        Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
        for(int row=0; row< NUM_ROWS; row++){

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col++){
                final int ROW =row;
                final int COL=col;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setPadding(0, 0, 0, 0);
                button.setMaxWidth(160);
                button.setMinWidth(160);

                button.setMinHeight(200);
                button.setMaxHeight(200);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }
}