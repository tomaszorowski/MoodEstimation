package com.example.tomek.moodestimation.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CommonObjects.CommonTasks;
import CommonObjects.CommonTests;

public class TestsListActivity  extends BaseActivity {
    private CommonTasks currentTask;
    private List<CommonTests> tests;
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_list);
        setCurrentTaskAndTests();
        generateTestsListView();
        setTextViews();

    }







    @Override
    protected void onAuthorize() {
        Core.setToken(Core.getInstance().getTokenFromSharedPreferences(getApplicationContext()));
        Intent intent = new Intent(this, TestsListActivity.class);
        startActivity(intent);
    }
    private void generateTestsListView(){
        TextView textView ;
        textView = (TextView) findViewById(R.id.testsTitle);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.invalidate();
        list = (ListView) findViewById(R.id.testListView);
        list.setFastScrollEnabled(true);
        String testsNames[]=new String[tests.size()];
        for(int i = 0;i<tests.size();i++){
            testsNames[i]=tests.get(i).getTestName()+ "\n" ;
        }
        ArrayList<String> testList = new ArrayList<String>();
        testList.addAll( Arrays.asList(testsNames) );
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_row, testList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TestsListActivity.this,CurrentTestActivity.class);
                Bundle extras = new Bundle();
                extras.putString("testId", String.valueOf(tests.get(position).getTestId()));
                extras.putString("taskId",String.valueOf(currentTask.getTaskId()));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void setTextViews(){

        TextView taskName = (TextView) findViewById(R.id.taskName);
        taskName.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        taskName.setTextColor(Color.rgb(106, 38, 111));
        taskName.setTypeface(null, Typeface.BOLD);

        TextView taskNameValue = (TextView) findViewById(R.id.taskNameValue);
        taskNameValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        taskNameValue.setTextColor(Color.rgb(35, 41, 41));
        taskNameValue.setPadding(5,0,0,0);
        taskNameValue.setTypeface(null, Typeface.ITALIC);

        TextView taskDescriptionValue = (TextView) findViewById(R.id.taskDescriptionValue);
        taskDescriptionValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        taskDescriptionValue.setTextColor(Color.rgb(35, 41, 41));
        taskNameValue.setPadding(5,0,0,0);
        taskDescriptionValue.getMeasuredWidth();


        taskName.setText("Task name:");
        taskNameValue.setText(this.currentTask.getTaskName());
        taskDescriptionValue.setText(this.currentTask.getShortDescription());

    }
    private void setCurrentTaskAndTests(){
        List<CommonTasks> tasks = Core.getInstance().getTasksFromSharedPreferences(getApplicationContext());
        this.currentTask=tasks.get(Integer.valueOf(getIntent().getStringExtra("task")));
        tests = new ArrayList<>();
        for(CommonTests test: currentTask.getTests()){
            this.tests.add(test);
        }
        Log.i("CURRENT_TASK--->", currentTask.getTaskId()+ " " + currentTask.getTaskName());
    }
}
