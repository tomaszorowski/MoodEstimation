package com.example.tomek.moodestimation.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class ApplicationLifeCycle extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String SHARED_PREFERENCES_NAME = "ApplicationLifeCycle";
    private static final String SHARED_PREFERENCES_FIRST_RUN_KEY = "firstRun";

    public ApplicationLifeCycle() {
        registerActivityLifecycleCallbacks(this);
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (getSharedPreferences().getBoolean(SHARED_PREFERENCES_FIRST_RUN_KEY, true)) {
            getSharedPreferences().edit().putBoolean(SHARED_PREFERENCES_FIRST_RUN_KEY, false).commit();
            onApplicationFirstRun();
        }

        onApplicationStart();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

    public void onApplicationFirstRun() {}

    public void onApplicationUpdate(int oldVersion, int newVersion) {}

    public void onApplicationStart() {}

    //getters


    public static String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }
}
