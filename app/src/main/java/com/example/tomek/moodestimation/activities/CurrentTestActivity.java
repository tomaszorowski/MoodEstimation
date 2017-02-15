package com.example.tomek.moodestimation.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomek.moodestimation.R;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.WebService;
import com.example.tomek.moodestimation.XmlWebService.Response;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import CommonObjects.CommonAnswers;
import CommonObjects.CommonQuestions;
import CommonObjects.CommonTasks;
import CommonObjects.CommonTestResultAnswers;
import CommonObjects.CommonTests;
import CommonObjects.CommonTestsResults;

public class CurrentTestActivity extends BaseActivity {
    private static final String QUESTION_TEXT_NUMERIC_SLIDER ="question-text-numeric-slider";
    private static final String QUESTION_TEXT_NUMERIC_RADIO ="question-text-numeric-radio";
    private static final String QUESTION_TEXT_CHOOSE_IMAGE="question-text-choose-image";
    private WebService.IAbortable abort;
    private CommonTests currentTest;
    private CommonTasks currentTask;
    private LinearLayout linearLayout;
    private TextView textView;
    private TextView testDescription;
    private String testDescpription;
    private List<CommonQuestions> commonQuestionsList= new ArrayList<>();
    private List<Object> numericAnswerObjects = new ArrayList<>();
    private List<Integer> progresValues = new ArrayList<>();
    private List<CommonTasks> currentTasks;
    List<CommonAnswers> commonAnswers = new ArrayList<>();
    private int commonAnswerId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_test);
        getTest();
    }


    @Override
    protected void onAuthorize() {
        Core.setToken(Core.getInstance().getTokenFromSharedPreferences(getApplicationContext()));
        Intent intent = new Intent(this, CurrentTestActivity.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this.abort!=null) {
            this.abort.abort();
        }
    }

    private void getTest(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String testId = extras.getString("testId");
        String taskId = extras.getString("taskId");
        if(Integer.valueOf(taskId)>0){
            getTask(taskId);
        }else{
            currentTask=null;
        }
        this.abort=Core.getInstance().getTest(getCurrenTestCallback,Integer.valueOf(testId));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void generateTestScreen(){
        //wygenerowac tytul, nazwe, opis
        textView = (TextView) findViewById(R.id.testName);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        textView.setTextColor(Color.rgb(11,95,165));
        textView.setGravity(Gravity.CENTER);
        testDescription = (TextView) findViewById(R.id.testDescriprion);
        testDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        testDescription.setTextColor(Color.rgb(11,95,165));
        testDescription.setGravity(Gravity.CENTER);
        linearLayout =  (LinearLayout) findViewById(R.id.testLinearLayout);
        textView.append(currentTest.getTestName());
        testDescription.append(testDescpription);
        //wygenerowac skladowe testu
        for(CommonQuestions commonQuestion : commonQuestionsList) {
            if(!commonQuestion.getQuestionType().equals(QUESTION_TEXT_CHOOSE_IMAGE)) {
                TextView newTextView = new TextView(this);
                newTextView.setText(commonQuestion.getQuestionTitle());
                newTextView.setPadding(0,40,0,0);
                newTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                newTextView.setTextColor(Color.rgb(11,95,165));
                newTextView.setGravity(Gravity.CENTER);
                linearLayout.addView(newTextView);
            }
            if (commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_SLIDER) || commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_RADIO)) {


                //adding radio button group to answer
                for (CommonAnswers commonAnswer : commonQuestion.getAnswers()) {
                    if (commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_RADIO)) {
                        RadioGroup radioGroup = new RadioGroup(this);

                        for (int i = 0; i < 5; i++) {
                            RadioButton radioButton = new RadioButton(this);
                            radioButton.setPadding(50,0,50,60);
                            radioGroup.addView(radioButton);
                        }
                        radioGroup.setPadding(90,0,0,0);
                        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                        radioGroup.setGravity(Gravity.CENTER);
                        numericAnswerObjects.add(radioGroup);
                        linearLayout.addView(radioGroup);


                        //adding slider to answer
                    } else if (commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_SLIDER)) {
                        SeekBar seekBar = new SeekBar(this);
                        seekBar.setPadding(20,0,0,40);
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                progresValues.add(progress);
                            }
                        });
                        numericAnswerObjects.add(seekBar);
                        linearLayout.addView(seekBar);
                    }
                }

                //adding images to answer
            } else if (commonQuestion.getQuestionType().equals(QUESTION_TEXT_CHOOSE_IMAGE)) {

                final List<CommonAnswers> commonAnswerses = commonAnswers;
                Random random = new Random();
                int help = 16;
                TableLayout buttonTable  = new TableLayout(this);
                for(int row=0;row<4;row++){
                    TableRow tableRow =new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT,
                            1.0f
                    ));
                    buttonTable.addView(tableRow);


                    for(int j=0;j<4;j++){
                        int width =248;
                        int index = random.nextInt(help);
                        final CommonAnswers processedAnswer = commonAnswerses.get(index);
                        commonAnswerses.remove(index);
                        byte[] photo =processedAnswer.getImage();
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
                        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(theImage,width-50,width-50,true);
                        help--;
                        //Setting button
                        final ImageButton bt= new ImageButton(this);
                        bt.setMaxWidth(width);
                        bt.setMaxHeight(width);
                        bt.setMinimumWidth(width);
                        bt.setMinimumHeight(width);
                        bt.setPadding(10,10,10,10);
                        bt.setBackground(new BitmapDrawable(getResources(),scaledBitmap));
                        bt.setFocusableInTouchMode(true);
                        //setting button Listener

                        bt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                commonAnswerId =processedAnswer.getAnswerId();
                                Toast.makeText(getApplicationContext(),processedAnswer.getTextAnswerValue(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        tableRow.addView(bt);

                    }



                }
                linearLayout.addView(buttonTable);
                linearLayout.invalidate();



            }
        }


        //przycisk zatwierdzajacy test--------------------------------------------------------------
        int width = 150;
        final Button submitButton = new Button(this);
        submitButton.setMaxWidth(width);
        submitButton.setMaxHeight(width);
        submitButton.setMinimumWidth(width);
        submitButton.setMinimumHeight(width);
        submitButton.setBackgroundResource(R.drawable.buttonshape);
        submitButton.setTextColor(Color.parseColor("#F1F2F0"));
        submitButton.setText("Confirm");
        submitButton.setTextSize(20);
        submitButton.setTransformationMethod(null);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Core.getInstance().saveTestResult(callback,prepareTestResult());

            }
        });

        linearLayout.addView(submitButton);


    }





@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
private CommonTestsResults prepareTestResult(){
    List<CommonTestResultAnswers> commonTestResultAnswers = new ArrayList<>();
    for(int i=0; i<commonQuestionsList.size();i++){
        CommonTestResultAnswers commonTestResultAnswer = new CommonTestResultAnswers();
        CommonQuestions commonQuestion;
        commonQuestion=commonQuestionsList.get(i);
        commonTestResultAnswer.setQuestionId(commonQuestion.getQuestionId());
        if(commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_SLIDER) || commonQuestion.getQuestionType().equals(QUESTION_TEXT_NUMERIC_RADIO)){
            if(numericAnswerObjects.get(i) instanceof SeekBar){
                SeekBar seek = (SeekBar) numericAnswerObjects.get(i);
                commonTestResultAnswer.setType("seek-bar-numeric");
                commonTestResultAnswer.setValue(seek.getProgress());
            }else if(numericAnswerObjects.get(i) instanceof  RadioGroup){
                RadioGroup radio = (RadioGroup) numericAnswerObjects.get(i);
                commonTestResultAnswer.setType("radio-button-numeric");
                commonTestResultAnswer.setValue((radio.getCheckedRadioButtonId())%5*20);
            }
            commonTestResultAnswers.add(commonTestResultAnswer);

            //Pam wyniki
        }else if(commonQuestion.getQuestionType().equals(QUESTION_TEXT_CHOOSE_IMAGE)){
                commonTestResultAnswer.setAnswerId(commonAnswerId);
                commonTestResultAnswer.setType("choose-image");
            commonTestResultAnswers.add(commonTestResultAnswer);

        }


    }
    CommonTestsResults newTestResult = new CommonTestsResults();
    newTestResult.setFinishDate(new Date());
    if(currentTask!=null){
        newTestResult.setTaskId(currentTask.getTaskId());
    }else{
        newTestResult.setTaskId(6);
    }
    newTestResult.setTestId(currentTest.getTestId());
    newTestResult.setCommonTestResultAnswers(commonTestResultAnswers);
    return newTestResult;
}
    private void prepareTest(Response response) {
        this.currentTest = (CommonTests) response.getBody();
        this.commonQuestionsList.addAll(currentTest.getCommonQuestions());
        if(commonQuestionsList.get(0).getQuestionType().equals(QUESTION_TEXT_CHOOSE_IMAGE)){
          this.commonAnswers.addAll(commonQuestionsList.get(0).getAnswers());

        }
    }
    private void updateTest() {
        if(currentTask!=null) {
            for (CommonTests commonTest : currentTask.getTests()) {
                if (commonTest.getTestId() == currentTest.getTestId()) {
                    currentTask.getTests().remove(commonTest);
                    if (currentTask.getTests().size() < 1) {
                        Core.getInstance().saveFinishedTask(finishedTask, currentTask.getTaskId());
                    } else {
                        Core.getInstance().saveTasksInSharedPreferences(this.currentTasks, getApplicationContext());
                    }

                }
            }
        }else{
            Intent intent = new Intent(CurrentTestActivity.this,MenuActivity.class);
            startActivity(intent);
        }
    }
    //deleting finished test from task
    private void deleteFinishedTask(){
        for(CommonTasks finishedTask: currentTasks){
            currentTasks.remove(finishedTask);
            break;
        }
        Core.getInstance().saveTasksInSharedPreferences(currentTasks, getApplicationContext());
    }
    private void getTask(String taskId){
        int id = Integer.valueOf(taskId);
        currentTasks = Core.getInstance().getTasksFromSharedPreferences(getApplicationContext());
        for(CommonTasks commonTask: currentTasks){
            if(commonTask.getTaskId()==id)this.currentTask=commonTask;
        }
    }
    private void getTestDescription(int testId){
        Core.getInstance().getTestDescription(getTestDescriptionCallback,testId);
    }



    //callbaki
    WebService.RequestCallback callback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            updateTest();

        }

        @Override
        public void OnError(Response response) {
            Toast.makeText(CurrentTestActivity.this,
                    "Sending error try again", Toast.LENGTH_LONG).show();

        }

        @Override
        public void OnException(Exception exception) {
            Toast.makeText(CurrentTestActivity.this,
                    "Sending exception try again", Toast.LENGTH_LONG).show();
        }
    };


    WebService.RequestCallback finishedTask = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            if(currentTask!=null)deleteFinishedTask();
            Intent intent = new Intent(CurrentTestActivity.this,MenuActivity.class);
            startActivity(intent);
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };

    WebService.RequestCallback getCurrenTestCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            prepareTest(response);
            getTestDescription(currentTest.getTestId());
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };

    WebService.RequestCallback getTestDescriptionCallback = new WebService.RequestCallback() {
        @Override
        public void OnSuccess(Response response) {
            testDescpription = (String)response.getBody();
            generateTestScreen();
        }

        @Override
        public void OnError(Response response) {

        }

        @Override
        public void OnException(Exception exception) {

        }
    };



}
