package com.example.tomek.moodestimation.dataProvidersAndHelpers;

import com.example.tomek.moodestimation.XmlWebService.WebService;
import com.example.tomek.moodestimation.XmlWebService.Request;

import java.util.List;

import CommonObjects.CommonTestsResults;



public class WebServiceProvider {

    public WebServiceProvider(){
    }



    protected WebService.IAbortable authorize(String[] stringArray, WebService.RequestCallback requestCallback, WebService webService){
        Request request = webService.createRequestInstance("authorization",stringArray);
      return  webService.request(request, requestCallback);
}

    protected WebService.IAbortable getTasks( WebService.RequestCallback requestCallback, int objectsCount, WebService webService){
        String [] array = {"iloscObiektow", String.valueOf(objectsCount)};
        Request request =webService.createRequestInstance("getTasks",array);
       return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getTest(WebService.RequestCallback requestCallback, int testId, WebService webService) {
        String [] array = {"testId", String.valueOf(testId)};
        Request request =webService.createRequestInstance("getTest",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable saveTestResult(WebService.RequestCallback requestCallback, CommonTestsResults commonTestResult, WebService webService) {
        String [] array = {"testResult"};
        Request request =webService.createRequestInstance("testResult",array);
        request.setBody(commonTestResult);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable saveFinishedTask(WebService.RequestCallback requestCallback, int taskId, WebService webService) {
        String [] array = {"taskId",String.valueOf(taskId)};
        Request request =webService.createRequestInstance("saveFinishedTask",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getUnsignedTests(WebService.RequestCallback requestCallback, WebService webService) {
        String [] array = {"getUnsignedTests"};
        Request request = webService.createRequestInstance("getUnsignedTests",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getTestCategories(WebService.RequestCallback requestCallback, WebService webService) {
        String [] array = {"getTestCategories"};
        Request request = webService.createRequestInstance("getTestCategories",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getTestResultAnswers(WebService.RequestCallback requestCallback, WebService webService, int categoryId) {
        String [] array = {"categoryId",String.valueOf(categoryId)};
        Request request = webService.createRequestInstance("getTestResultAnswers",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getQuestionsTitles(WebService.RequestCallback requestCallback, WebService webService, List<Integer> questionsIdsList) {
        String [] array = {"getQuestionsTitles"};
        Request request = webService.createRequestInstance("getQuestionsTitles",array);
        request.setBody(questionsIdsList);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getAnswersTitles(WebService.RequestCallback requestCallback, WebService webService, List<Integer> answersIdsForPam) {
        String [] array = {"getAnswersTitles"};
        Request request = webService.createRequestInstance("getAnswersTitles",array);
        request.setBody(answersIdsForPam);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable checkIfThereAreActiveTasks(WebService.RequestCallback requestCallback, WebService webService) {
        String [] array = {"checkIfThereAreActiveTasks"};
        Request request = webService.createRequestInstance("checkIfThereAreActiveTasks",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable getTestDescription(WebService.RequestCallback requestCallback, WebService webService, int testId) {
        String [] array = {"testId",String.valueOf(testId)};
        Request request = webService.createRequestInstance("getTestDescription",array);
        return webService.request(request, requestCallback);
    }

    public WebService.IAbortable register(WebService.RequestCallback requestCallback, WebService webService, String[] stringArray) {
        Request request = webService.createRequestInstance("register",stringArray);
        return webService.request(request, requestCallback);
    }
}
