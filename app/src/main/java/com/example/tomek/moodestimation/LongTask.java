package com.example.tomek.moodestimation;

import android.os.AsyncTask;

/**
 * Created by Tomek on 10/10/2016.
 * Klasa tworząca asynchroniczne wątki do długich zadań tj:
 * przetwarzanie obrazów,
 * dostęp do bazy
 */
public class LongTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
