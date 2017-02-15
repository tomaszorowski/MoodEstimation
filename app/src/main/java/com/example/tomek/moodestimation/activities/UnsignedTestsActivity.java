package com.example.tomek.moodestimation.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CommonObjects.CommonTests;

public class UnsignedTestsActivity extends BaseActivity {
    private List<CommonTests> unsignedTestsList;
    private WebService.IAbortable abort;
    private ListView list ;
    private ArrayAdapter<String> adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsigned_tests);
        getUnsignedTestList();
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UnsignedTestsActivity.this,MenuActivity.class);
        startActivity(intent);
    }

    private void getUnsignedTestList(){
        Core.getInstance().getUnsignedTests(unsignedTestsCallback);
    }

    private void generateTestsListView(){
        list = (ListView) findViewById(R.id.unsignedTestsListView);
        TextView textView ;
        textView = (TextView) findViewById(R.id.titleUnsignedTests);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        textView.setTextColor(Color.rgb(11,95,165));
        list.setFastScrollEnabled(true);
        String testsNames[]=new String[unsignedTestsList.size()];
        for(int i = 0;i<unsignedTestsList.size();i++){
            testsNames[i]=unsignedTestsList.get(i).getTestName()+ "\n" ;
        }
        ArrayList<String> testList = new ArrayList<String>();
        testList.addAll( Arrays.asList(testsNames) );
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_row, testList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UnsignedTestsActivity.this,CurrentTestActivity.class);
                Bundle extras = new Bundle();
                extras.putString("testId", String.valueOf(unsignedTestsList.get(position).getTestId()));
                extras.putString("taskId",String.valueOf(0));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onAuthorize() {
        super.onAuthorize();
        Intent intent = new Intent(this, UnsignedTestsActivity.class);
        startActivity(intent);
    }
    //Callbacks
    WebService.RequestCallback unsignedTestsCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            unsignedTestsList= (List<CommonTests>) response.getBody();
            generateTestsListView();
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_unsigned_tests);
            rl.invalidate();
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };
}
