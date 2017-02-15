package com.example.tomek.moodestimation.dataProvidersAndHelpers;


import android.content.Context;

import com.example.tomek.moodestimation.XmlWebService.WebService;

import java.util.List;

import CommonObjects.CommonTasks;
import CommonObjects.CommonTestsResults;
import CommonObjects.CommonTokens;


public class ProviderManager {
    private SharedPreferencesDataManager sharedPreferencesDataManager;
    private WebServiceProvider webServiceProvider;

    public ProviderManager(){
        this.sharedPreferencesDataManager = new SharedPreferencesDataManager();
        this.webServiceProvider = new WebServiceProvider();

    }

    public WebService.IAbortable authorize(String[] stringArray, WebService.RequestCallback requestCallback, WebService webService){
        return getWebServiceProvider().authorize(stringArray, requestCallback,webService);
    }
    public WebService.IAbortable getTasks( WebService.RequestCallback requestCallback, int objectsCount, WebService webService){
        return getWebServiceProvider().getTasks(requestCallback, objectsCount, webService);
    }
    public WebService.IAbortable getTest(WebService.RequestCallback requestCallback, int testId, WebService webService) {
        return getWebServiceProvider().getTest(requestCallback, testId, webService);
    }
    public WebService.IAbortable saveTestResult(WebService.RequestCallback requestCallback, CommonTestsResults commonTestResult, WebService webService) {
        return getWebServiceProvider().saveTestResult(requestCallback, commonTestResult, webService);
    }
    public WebService.IAbortable saveFinishedTask(WebService.RequestCallback requestCallback, int taskId, WebService webService) {
        return getWebServiceProvider().saveFinishedTask(requestCallback, taskId, webService);
    }
    public WebService.IAbortable getUnsignedTests(WebService.RequestCallback requestCallback, WebService webService) {
        return getWebServiceProvider().getUnsignedTests(requestCallback, webService);
    }
    public WebService.IAbortable getTestCategories(WebService.RequestCallback requestCallback, WebService webService) {
        return getWebServiceProvider().getTestCategories(requestCallback, webService);
    }
    public WebService.IAbortable getTestResultAnswers(WebService.RequestCallback requestCallback, WebService webService, int categoryId) {
        return getWebServiceProvider().getTestResultAnswers(requestCallback, webService,categoryId);
    }
    public WebService.IAbortable getQuestionsTitles(WebService.RequestCallback requestCallback, WebService webService, List<Integer> questionsIdsList) {
        return getWebServiceProvider().getQuestionsTitles(requestCallback, webService,questionsIdsList);
    }
    public WebService.IAbortable getAnswersTitles(WebService.RequestCallback requestCallback, WebService webService, List<Integer> answersIdsForPam) {
        return getWebServiceProvider().getAnswersTitles(requestCallback, webService,answersIdsForPam);
    }
    public WebService.IAbortable checkIfThereAreActiveTasks(WebService.RequestCallback requestCallback, WebService webService) {
        return getWebServiceProvider().checkIfThereAreActiveTasks(requestCallback, webService);
    }
    public WebService.IAbortable getTestDescription(WebService.RequestCallback requestCallback, WebService webService, int testId) {
        return getWebServiceProvider().getTestDescription(requestCallback, webService,testId);
    }
    public WebService.IAbortable register(WebService.RequestCallback requestCallback, WebService webService, String[] stringArray) {
            return getWebServiceProvider().register(requestCallback, webService,stringArray);
    }


    public String getTokenFromSharedPreferences(Context context) {
        return getSharedPreferencesDataManager().getTokenFromSharedPreferences(context);
    }

    public void clearSharedPreferences(Context context) {
        getSharedPreferencesDataManager().clearSharedPreferences(context);
    }

    public void saveToken(CommonTokens token, Context context) {
        getSharedPreferencesDataManager().saveToken(token,context);
    }

    public void deleteTaskFromSharedPreferences(CommonTasks finishedTask, Context context) {
        getSharedPreferencesDataManager().deleteTaskFromSharedPreferences(finishedTask,context);
    }

    public void saveTasksInSharedPreferences(List<CommonTasks> commonTasks, Context context) {
        getSharedPreferencesDataManager().saveTasksInSharedPreferences(commonTasks,context);
    }

    public List<CommonTasks> getTasksFromSharedPreferences(Context context) {
        return getSharedPreferencesDataManager().getTasksFromSharedPreferences(context);
    }
    public boolean checkIsTokenValid(Context context){
        return getSharedPreferencesDataManager().checkIsTokenValid(context);
    }

    //getters
    public WebServiceProvider getWebServiceProvider() {
        return webServiceProvider;
    }
    public SharedPreferencesDataManager getSharedPreferencesDataManager() {
        return sharedPreferencesDataManager;
    }



}
