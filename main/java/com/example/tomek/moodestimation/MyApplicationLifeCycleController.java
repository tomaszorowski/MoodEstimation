package com.example.tomek.moodestimation;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by developer on 28/10/2016.
 * http://fulmanski.pl/zajecia/mobile_os/zajecia_20152016/lecture_02.pdf
 */

public class MyApplicationLifeCycleController extends Application{

    private SharedPreferences myPref;
    private static MyApplicationLifeCycleController instance;
    private static ArrayList<ApplicationLifeCycleListener> listeners = new ArrayList<ApplicationLifeCycleListener>();

    public static void registerLifeCycleListener(ApplicationLifeCycleListener listener) {
        listeners.add(listener);
    }

    public static void unregisterLifeCycleListener(ApplicationLifeCycleListener listener) {
        listeners.remove(listener);
    }

    static {
        MyApplicationLifeCycleController.registerLifeCycleListener(new MyApplicationLifeCycleController.ApplicationLifeCycleListener() {
            @Override
            public void onApplicationCreate() {
                Log.i(" --> ", "onApplicationCreate");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(" --> ", "onActivityPaused");
            }
        });
    }

    public MyApplicationLifeCycleController() {
        instance = this;
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (ApplicationLifeCycleListener listener : listeners) listener.onActivityPaused(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static MyApplicationLifeCycleController getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
        Log.i(" Application --> ", "onApplicationCreate");
        super.onCreate();
        for (ApplicationLifeCycleListener listener : listeners) listener.onApplicationCreate();
    }

    public interface ApplicationLifeCycleListener {
        void onApplicationCreate();
        void onActivityPaused(Activity activity);

    }

}
