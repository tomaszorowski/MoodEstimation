package com.example.tomek.moodestimation.activities;

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
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import CommonObjects.CommonCategories;

public class StatisticsActivity extends BaseActivity {
    private List<CommonCategories> commonCategoriesList;
    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getCategories();

    }



    private void getCategories(){
        Core.getInstance().getTestCategories(categoriesNameCallback);
    }

    private void generateTestsListView(){
        list = (ListView) findViewById(R.id.testCategoriesListView);
        list.setFastScrollEnabled(true);
        TextView textView ;
        textView = (TextView) findViewById(R.id.titleStatistics);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        textView.setTextColor(Color.rgb(11,95,165));
        String categoriesNames[]=new String[commonCategoriesList.size()];
        for(int i = 0;i<commonCategoriesList.size();i++){
            categoriesNames[i]=commonCategoriesList.get(i).getName()+ "\n" ;
        }
        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.addAll( Arrays.asList(categoriesNames) );
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_row, categoryList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StatisticsActivity.this,SelectedCategoryStatisticsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("categoryId", String.valueOf(commonCategoriesList.get(position).getCategoryId()));
                extras.putString("categoryName",commonCategoriesList.get(position).getName());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onAuthorize() {
        super.onAuthorize();
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }


    //callbacks
    WebService.RequestCallback categoriesNameCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            commonCategoriesList=(List<CommonCategories>) response.getBody();
            generateTestsListView();
        }

        @Override
        public void OnError(Response response) {
            Log.i("categoty name ->>",response.getResponseMessage()+" "+response.getResponseCode());
        }

        @Override
        public void OnException(Exception exception) {

            Log.i("categoty name ->>","exception");
        }
    };
}
