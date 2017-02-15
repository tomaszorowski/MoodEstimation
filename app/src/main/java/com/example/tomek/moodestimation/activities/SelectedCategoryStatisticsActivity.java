package com.example.tomek.moodestimation.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.XmlWebService.Response;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;


import CommonObjects.CommonAnswers;
import CommonObjects.CommonQuestions;
import CommonObjects.CommonTestResultAnswers;
import CommonObjects.CommonTestsResults;

public class SelectedCategoryStatisticsActivity extends BaseActivity {
    private List<CommonTestsResults> commonTestsResultsList;
    private String categoryName;
    private int  categoryId;
    private List<Integer> commonQuestionsIdsList;
    private List<CommonQuestions> commonQuestionsList;
    private List<CommonAnswers> commonAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category_statistics);
        getTestsResultsForChoosenCategory();
    }






    private void generateChartFirstOption(){
        GraphView graph = (GraphView) findViewById(R.id.statistics_chart);
        int commonResultListSize = commonTestsResultsList.size();
        int commonAnswersSize = commonTestsResultsList.get(0).getCommonTestResultAnswers().size();
        commonTestsResultsList.subList(1,commonResultListSize).clear();;
        commonQuestionsList.subList(commonQuestionsList.size()/commonResultListSize,commonQuestionsList.size()).clear();


          int gg = commonAnswersSize;
            gg=gg/commonResultListSize;
             List<CommonTestResultAnswers> tra= new ArrayList<>();
            for(int j = 0;j<commonAnswersSize;j++){

                if(j<gg){
                    tra.add(j,commonTestsResultsList.get(0).getCommonTestResultAnswers().get(j));
                }
                if(j>=gg){
                    int divider = j%tra.size();
                    int value = tra.get(divider).getValue();
                    int value2 = commonTestsResultsList.get(0).getCommonTestResultAnswers().get(j).getValue();
                    CommonTestResultAnswers help =  commonTestsResultsList.get(0).getCommonTestResultAnswers().get(j);
                    help.setValue(value+value2);
                    tra.set(divider,help);
                }

            }
        for(int j = 0;j<gg;j++){
            int value = tra.get(j).getValue()/commonResultListSize;
            tra.get(j).setValue(value);
        }

            commonTestsResultsList.get(0).setCommonTestResultAnswers(tra);


        DataPoint[] dataPointTable = new DataPoint[gg];
        for (int i = 0; i < dataPointTable.length; i++) {
            DataPoint dp = new DataPoint(i,0);
            dataPointTable[i]=dp;
        }
            for (int i = 0; i < dataPointTable.length; i++) {
                int y=commonTestsResultsList.get(0).getCommonTestResultAnswers().get(i).getValue();
                DataPoint dp = new DataPoint(i,y);
                dataPointTable[i]=dp;
            }


        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPointTable);
        setTextViews(commonResultListSize);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(SelectedCategoryStatisticsActivity.this, commonQuestionsList.get((int) dataPoint.getX()).getQuestionTitle()+" "+dataPoint.getY()+" %", Toast.LENGTH_SHORT).show();
            }
        });
        series.setColor(Color.BLUE);
        series.setSpacing(50);
        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);

    }

    private void generateChartSecondOption() {
         GraphView graph = (GraphView) findViewById(R.id.statistics_chart);
         BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
         final List<Integer> valuesList = new ArrayList<>();
        int sizeHelper = commonAnswers.size();
        for(int j=0; j<sizeHelper;j++){
            commonAnswers.get(j).setNumericAnswerValue(1);
        }
        for(int i=0; i<commonAnswers.size();i++){
            for(int j=i+1; j<commonAnswers.size();j++){
                if(commonAnswers.get(i).getTextAnswerValue().equals(commonAnswers.get(j).getTextAnswerValue())){
                    int help = commonAnswers.get(i).getNumericAnswerValue()+1;
                    commonAnswers.get(i).setNumericAnswerValue(help);
                    commonAnswers.remove(j);
                }
            }
        }

        for(int i=0;i<commonAnswers.size();i++) {
            series.appendData(new DataPoint(i,commonAnswers.get(i).getNumericAnswerValue()),true,commonAnswers.size());
        }
        setTextViews(sizeHelper);


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(SelectedCategoryStatisticsActivity.this, commonAnswers.get((int) dataPoint.getX()).getTextAnswerValue()+" "+dataPoint.getY()+" times", Toast.LENGTH_SHORT).show();
            }
        });
        series.setColor(Color.BLUE);
        series.setSpacing(50);
        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);

        }


    private void getQuestionsTitles(List<Integer> questionsIdsList){
        if(categoryName.equals("Panas")||categoryName.equals("Spane")) {
            Core.getInstance().getQuestionsTitles(getTitlesCallback,questionsIdsList);
        }else if(categoryName.equals("Pam")){
            List<Integer> answersIdsForPam = new ArrayList<>();
            for(int i=0; i<commonTestsResultsList.get(0).getCommonTestResultAnswers().size();i++){
                answersIdsForPam.add(commonTestsResultsList.get(0).getCommonTestResultAnswers().get(i).getAnswerId());
            }
            Core.getInstance().getAnswersTitles(getTitlesCallback,answersIdsForPam);
        }
    }
    private void getTestsResultsForChoosenCategory(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        categoryId =Integer.valueOf(extras.getString("categoryId"));
        categoryName = extras.getString("categoryName");
        setTextViews(0);

        Core.getInstance().getTestResultAnswers(getTestsResultsCallback,categoryId);
    }
    private void setTextViews(int count){
        TextView text1 = (TextView) findViewById(R.id.titleCurrentStatistics);
        text1.setText(categoryName);
        text1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        text1.setTextColor(Color.rgb(11,95,165));
        text1.invalidate();
        TextView text2 = (TextView) findViewById(R.id.statisticsDescriptionTextView);
        text2.setText("General statistics of "+categoryName+ " complited " + count+ " times.");
        text2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        text2.setTextColor(Color.rgb(35, 41, 41));
        text2.setPadding(5,0,0,0);
        text2.setTypeface(null, Typeface.ITALIC);
    }

    WebService.RequestCallback getTestsResultsCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            commonTestsResultsList = (List<CommonTestsResults>) response.getBody();
            commonQuestionsIdsList = new ArrayList<>();
            for(int i=0;i<commonTestsResultsList.get(0).getCommonTestResultAnswers().size();i++){
                commonQuestionsIdsList.add(commonTestsResultsList.get(0).getCommonTestResultAnswers().get(i).getQuestionId());
            }
            getQuestionsTitles(commonQuestionsIdsList);

        }
        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };
    WebService.RequestCallback getTitlesCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            if(categoryName.equals("Panas")||categoryName.equals("Spane")) {
                commonQuestionsList = (List<CommonQuestions>) response.getBody();
                generateChartFirstOption();
            }else if(categoryName.equals("Pam")){
                commonAnswers = (List<CommonAnswers>) response.getBody();
                generateChartSecondOption();
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
