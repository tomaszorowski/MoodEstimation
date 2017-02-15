package com.example.tomek.moodestimation.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;
import com.example.tomek.moodestimation.XmlWebService.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CommonObjects.CommonTasks;


public class TasksActivity extends BaseActivity {
    private List<CommonTasks> tasks;
    private WebService.IAbortable abort;
    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getNewTasks();
    }




    private void createTaskListView(){
        TextView textView ;
        textView = (TextView) findViewById(R.id.titleTasks);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.invalidate();
        list = (ListView) findViewById(R.id.tasksListView);
        list.setFastScrollEnabled(true);
        String tasks[]=new String[this.tasks.size()];

        for(int i = 0; i< this.tasks.size(); i++){
            tasks[i]= "Task name: "+this.tasks.get(i).getTaskName()+ "\nTarget date: "+ this.tasks.get(i).getTargetDate() ;
        }
        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(tasks) );
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_row, carL);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TasksActivity.this,TestsListActivity.class);
                intent.putExtra("task", String.valueOf(position));
                startActivity(intent);
            }
        });}


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(tasks!=null) {
            if(tasks.size()>0){
                saveTasks();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkIfThereTasksSaved()){
              this.tasks=getSavedTasks();
              createTaskListView();
        }
    }



    @Override
    protected void onAuthorize() {
        super.onAuthorize();
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this.abort!=null) {
            this.abort.abort();
        }
    }



    private void saveTasks(){
        Core.getInstance().saveTasksInSharedPreferences(this.tasks, getApplicationContext());
    }

    private void getNewTasks(){
        WebService.RequestCallback newTaskCallback = new WebService.RequestCallback() {
            @Override
            public void OnSuccess(Response response) {
                tasks = (List<CommonTasks>) response.getBody();
                if(tasks==null){
                    buildPopOut();
                }else {
                    if(tasks.size()>0) {
                        createTaskListView();
                    }else{
                        buildPopOut();
                    }
                }
        }

            @Override
            public void OnError(Response response) {
                Log.i("TASKS_ERROR--> ", response.getBody().toString());
            }

            @Override
            public void OnException(Exception exception) {
                Log.i("TASKS_EXCEPTION--> ", exception.getMessage());

            }
        };
        this.abort=Core.getInstance().getTasks(newTaskCallback, 0);
    }

    private List<CommonTasks> getSavedTasks(){
        return Core.getInstance().getTasksFromSharedPreferences(getApplicationContext());
    }

    private boolean checkIfThereTasksSaved(){
        if((Core.getInstance().getTasksFromSharedPreferences(getApplicationContext())!=null)&&(Core.getInstance().getTasksFromSharedPreferences(getApplicationContext()).size()>0)){
            return true;
        }
        else{
            return false;
        }
    }
    private void buildPopOut(){
        new AlertDialog.Builder(TasksActivity.this)
                .setTitle("There is no task for you :(")
                .setMessage("If you want to solve some unassigned test press 'Ok'")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TasksActivity.this,UnsignedTestsActivity.class);
                        startActivity(intent);                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TasksActivity.this,MenuActivity.class);
                        startActivity(intent);                         }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
