package com.example.tomek.moodestimation.utils;

import android.content.Context;

import com.example.tomek.moodestimation.XmlWebService.WebService;
import com.example.tomek.moodestimation.dataProvidersAndHelpers.ProviderManager;

import java.util.List;
import CommonObjects.CommonTasks;
import CommonObjects.CommonTestsResults;
import CommonObjects.CommonTokens;


public final class Core extends ApplicationLifeCycle{

    private static WebService webService;
    private static Core INSTANCE=null;
    private static ProviderManager providerManager;
    private static final String WEBSERVICE_URL ="http://10.0.2.2:8080/XmlWebService/EP";


    public static Core getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Core();
        }
        return INSTANCE;
    }


    @Override
    public void onApplicationStart() {
        this. webService = new WebService(WEBSERVICE_URL);
        this.providerManager = new ProviderManager();
    }


    public WebService.IAbortable authorize(String[] stringArray, WebService.RequestCallback requestCallback){
        return getProviderManager().authorize(stringArray,requestCallback,getWebService());
    }
    public boolean checkIfTokenIsValid(Context context){
        return getProviderManager().checkIsTokenValid(context);
    }
    public WebService.IAbortable getTasks(WebService.RequestCallback requestCallback, int objectsCount){
        return getProviderManager().getTasks(requestCallback,objectsCount,getWebService());
    }
    public String getTokenFromSharedPreferences(Context context){
        return getProviderManager().getTokenFromSharedPreferences(context);
    }
    public WebService.IAbortable getTest(WebService.RequestCallback requestCallback, int testId) {
        return getProviderManager().getTest(requestCallback,testId,getWebService());
    }
    public WebService.IAbortable saveTestResult(WebService.RequestCallback requestCallback, CommonTestsResults commonTestResult) {
        return getProviderManager().saveTestResult(requestCallback,commonTestResult,getWebService());
    }
    public WebService.IAbortable saveFinishedTask(WebService.RequestCallback requestCallback, int taskId) {
        return getProviderManager().saveFinishedTask(requestCallback,taskId,getWebService());
    }
    public WebService.IAbortable getUnsignedTests(WebService.RequestCallback requestCallback) {
        return getProviderManager().getUnsignedTests(requestCallback,getWebService());
    }
    public WebService.IAbortable getTestCategories(WebService.RequestCallback requestCallback) {
        return getProviderManager().getTestCategories(requestCallback,getWebService());
    }
    public WebService.IAbortable getTestResultAnswers(WebService.RequestCallback requestCallback, int categoryId) {
        return getProviderManager().getTestResultAnswers(requestCallback,getWebService(),categoryId);
    }
    public WebService.IAbortable getQuestionsTitles(WebService.RequestCallback requestCallback, List<Integer> questionsIdsList) {
        return getProviderManager().getQuestionsTitles(requestCallback,getWebService(),questionsIdsList);
    }
    public WebService.IAbortable getAnswersTitles(WebService.RequestCallback requestCallback, List<Integer> answersIdsForPam) {
        return getProviderManager().getAnswersTitles(requestCallback,getWebService(),answersIdsForPam);
    }
    public WebService.IAbortable checkIfThereAreActiveTasks(WebService.RequestCallback requestCallback) {
        return getProviderManager().checkIfThereAreActiveTasks(requestCallback,getWebService());
    }
    public WebService.IAbortable getTestDescription(WebService.RequestCallback requestCallback, int testId) {
        return getProviderManager().getTestDescription(requestCallback,getWebService(),testId);

    }
    public WebService.IAbortable register(String[] stringArray, WebService.RequestCallback requestCallback) {
        return getProviderManager().register(requestCallback,getWebService(),stringArray);
    }



    //shared preferences operations
    public void clearSharedPreferences(Context context){
        getProviderManager().clearSharedPreferences(context);
    }
    public void saveTokenInSharedPreferences(CommonTokens token, Context context){
        setToken(token.getToken());
        getProviderManager().saveToken(token,context);
    }
    public void deleteTaskFromSharedPreferences(CommonTasks finishedTask, Context context){
        getProviderManager().deleteTaskFromSharedPreferences(finishedTask,context);
    }

    public void saveTasksInSharedPreferences(List<CommonTasks> commonTasks, Context context){
        getProviderManager().saveTasksInSharedPreferences(commonTasks,context);
    }
    public List<CommonTasks> getTasksFromSharedPreferences(Context context){
        return getProviderManager().getTasksFromSharedPreferences(context);
    }


    private static ProviderManager getProviderManager() {
        return providerManager;
    }

    private static WebService getWebService() {
        return webService;
    }


    public static void setToken(String token) {
        getWebService().setToken(token);
    }



}
