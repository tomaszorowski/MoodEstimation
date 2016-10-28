package com.example.tomek.moodestimation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.ArrayList;
import java.util.Random;


public class Pam extends AppCompatActivity {

    private static final int NUM_ROWS =4;
    private static final int NUM_COLS = 4;
    private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    private ArrayList<String> resources = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pam);
        populateButtons();
        initResources();
        randMood();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
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
    public void findYourMood(View view){
        randMood();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void randMood(){
    int ii=0;
        for(int i=0; i<4;i++){
            for(int j=0;j<4;j++){
                ii++;
                        Button button = buttons[i][j];
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),randImageName(ii));
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 160, 200, true);
                        Resources resource = getResources();
                        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

                }
            }
        }
public void savePam(View view){
    Intent intent = new Intent(this, Menu.class);
    startActivity(intent);
}
//ładuje nazwy obrazków
public void initResources(){
    for(int i=1; i<19; i++) {
        String s="image"+i;
        resources.add(s);
    }
}

public int randId(int count){
    Random generator = new Random();
    return generator.nextInt(count);
}
public int randImageName(int i){
     int id = getResources().getIdentifier(resources.get(i), "drawable", getPackageName());
    return id;
}

}
