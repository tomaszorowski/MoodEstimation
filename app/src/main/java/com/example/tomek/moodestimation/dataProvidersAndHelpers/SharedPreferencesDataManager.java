package com.example.tomek.moodestimation.dataProvidersAndHelpers;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.tomek.moodestimation.utils.Core;
import com.example.tomek.moodestimation.XmlWebService.XStreamHelper;
import java.util.List;
import CommonObjects.CommonTasks;
import CommonObjects.CommonTokens;



public class SharedPreferencesDataManager {

    public SharedPreferencesDataManager(){

    }

    public void saveToken(CommonTokens token, Context context){
        XStreamHelper<CommonTokens> xStreamHelper = new XStreamHelper();
        String stringToken = xStreamHelper.parseModelToString(token);
        SharedPreferences sharedPref = context.getSharedPreferences(
        Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        sharedPref.edit().putString("token", stringToken).commit();
    }
    public void clearSharedPreferences( Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }


    public boolean checkIsTokenValid(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
        Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        String helpString = sharedPref.getString("token","NoToken");
        if(helpString.equalsIgnoreCase("NoToken")){
            return false;
        }else {
            XStreamHelper<CommonTokens> xstream = new XStreamHelper();
            CommonTokens token = xstream.parseStringToModel(helpString);
            java.util.Date date =new java.util.Date();
            int comparison =date.compareTo(token.getFinishDate());
            if(comparison==-1 || comparison ==0){
                return true;
            }else{return false;}
        }

    }
    public String getTokenFromSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        XStreamHelper<CommonTokens> xStreamHelper = new XStreamHelper();
        CommonTokens commonToken = xStreamHelper.parseStringToModel(sharedPref.getString("token","NoToken"));
        String helpString = commonToken.getToken();
        return helpString;
    }


    //tasks
    public void saveTasksInSharedPreferences(List<CommonTasks> commonTasks, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        if(commonTasks.size()>0) {
            XStreamHelper<List<CommonTasks>> xStreamHelper = new XStreamHelper();
            String stringTasks = xStreamHelper.parseModelToString(commonTasks);
            sharedPref.edit().putString("tasks", stringTasks).commit();
        }else{
            sharedPref.edit().remove("tasks").commit();
        }
    }
    public void deleteTaskFromSharedPreferences(CommonTasks finishedTask, Context context) {
        List<CommonTasks> commonTasks = getTasksFromSharedPreferences(context);
        if(commonTasks!=null){
            commonTasks.remove(finishedTask);
            saveTasksInSharedPreferences(commonTasks,context);
        }
    }
    public List<CommonTasks> getTasksFromSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
        Core.getSharedPreferencesName(), Context.MODE_PRIVATE);
        XStreamHelper<List<CommonTasks>> xStreamHelper = new XStreamHelper();
        String helpString = sharedPref.getString("tasks","noTasks");
        if(helpString.equalsIgnoreCase("noTasks")){
            return null;
        }else{
            List<CommonTasks> commonTasks = xStreamHelper.parseStringToModel(helpString);
            return commonTasks;
        }

    }


}
