package com.example.copperadmin.testingadapters;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by Copper Admin on 10/8/2014.
 */

public class PollService extends IntentService {
    private static final String TAG = "PollService";
    public PollService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        //Checks for Background Network availability
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() &&
                cm.getActiveNetworkInfo() != null;
        if (!isNetworkAvailable) return;

        Log.i(TAG, "Received an intent: " + intent);
    }
}
