package com.example.tomek.moodestimation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.utils.AuthorizationException;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;

public class MenuActivity extends BaseActivity implements View.OnClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        generateButtons();
        checkIfThereAreActiveTasks();

    }

    @Override
    protected void onAuthorize() {
        Core.setToken(Core.getInstance().getTokenFromSharedPreferences(getApplicationContext()));
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }




    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.tasks_btn:{
                if(super.checkIsTokenValid()){
                    Intent intent = new Intent(this, TasksActivity.class);
                    startActivity(intent);
                }else{
                    processAuthoorizationException(new AuthorizationException("Brak waznego tokenu"));
                }
                break;}
            case R.id.logOut_btn: {
                //czyszczenie shared
                Core.getInstance().clearSharedPreferences(getApplicationContext());
                if(super.checkIsTokenValid()){
                    Intent intent = new Intent(this, TasksActivity.class);
                    startActivity(intent);
                }else{
                    processAuthoorizationException(new AuthorizationException("Brak waznego tokenu"));
                }
                break;
            }
            case R.id.exit_btn: {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.unsignedTests_btn: {
                Intent intent = new Intent(this,UnsignedTestsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.statistics_btn: {
                Intent intent = new Intent(this,StatisticsActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;

        }

    }
    private void generateButtons(){
        final Button button = (Button) findViewById(R.id.tasks_btn);
        final Button button1 =  (Button) findViewById(R.id.logOut_btn);
        final Button button2 =  (Button) findViewById(R.id.unsignedTests_btn);
        final Button button3 =  (Button) findViewById(R.id.statistics_btn);
        final Button button4 =  (Button) findViewById(R.id.exit_btn);
        //change menu style
        TextView textView ;
        textView = (TextView) findViewById(R.id.titleMenu);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.invalidate();
        //setting onClickListeners to buttons
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

    }
    private void checkIfThereAreActiveTasks(){
        Core.getInstance().checkIfThereAreActiveTasks(checkIfThereAreActiveTasksCallback);
    }
    private void buildPopOut(){
        new AlertDialog.Builder(MenuActivity.this)
                .setTitle("There is some tasks for you :)")
                .setMessage("If you want to solve solve them, press 'Ok'")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MenuActivity.this,TasksActivity.class);
                        startActivity(intent);                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public WebService.RequestCallback checkIfThereAreActiveTasksCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {

            boolean boolHelper = (boolean) response.getBody();
            if(boolHelper){
                buildPopOut();
            }
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };
}
