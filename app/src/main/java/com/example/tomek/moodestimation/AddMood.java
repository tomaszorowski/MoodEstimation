package com.example.tomek.moodestimation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class AddMood extends AppCompatActivity {
    private Button buttons[] = new Button[4];
    private ArrayList<String> scaleNames=new ArrayList<String>();

    ApplicationGlobalVariables app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        app = (ApplicationGlobalVariables)this.getApplicationContext();
        adjustScales();
        populateButtons();
    }
    private void populateButtons() {
        int dpConverted=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 800, getResources().getDisplayMetrics());
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for(int row=0; row< scaleNames.size(); row++){

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < 1; col++) {
                final int ROW = row;
                final int COL = col;
                    final Button button = new Button(this);
                    button.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    button.setPadding(0, 0, 0, 0);
                    button.setText(scaleNames.get(row));
                    button.setMaxWidth(dpConverted);
                    button.setMinWidth(dpConverted);

                    button.setMinHeight(120);

                button.setId(row);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(button.getText().equals("Spane")){
                                Intent i = new Intent(getApplicationContext(), Spane.class);
                                startActivity(i);
                            }
                            if(button.getText().equals("Panas")){
                                Intent i = new Intent(getApplicationContext(), Panas.class);
                                startActivity(i);
                            }
                            if(button.getText().equals("Pam")){
                                Intent i = new Intent(getApplicationContext(), Pam.class);
                                startActivity(i);
                            }
                            if(button.getText().equals("Special")){
                                Intent i = new Intent(getApplicationContext(), Special.class);
                                startActivity(i);
                            }
                        }
                    });
                    tableRow.addView(button);
                    buttons[row] = button;

            }
        }
    }
    public void adjustScales(){
        scaleNames.add("Special");
        if(app.getOptionValue("spane").contentEquals("true"))scaleNames.add("Spane");
        if(app.getOptionValue("panas").contentEquals("true"))scaleNames.add("Panas");
        if(app.getOptionValue("pam").contentEquals("true"))scaleNames.add("Pam");

    }
    public void goToMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }


}
