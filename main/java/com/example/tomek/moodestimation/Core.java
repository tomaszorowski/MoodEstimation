package com.example.tomek.moodestimation;

import android.app.Activity;
import android.util.Log;

public class Core {

    public static int dummy;

    static {
        Log.i(" --> ", "On Core Static Init");
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

}
