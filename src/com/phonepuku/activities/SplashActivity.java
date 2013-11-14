package com.phonepuku.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import com.phonepuku.service.MyServices;
import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 5000;
    String callHistory = "";
    ArrayList<String> callHistoryArrayList;
    TextCapitalizeResultReceiver capitalizeResultReceiver;
    static HashMap<String, ArrayList<String>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);        
        
        Intent intent = new Intent(this, MyServices.class);
        startService(intent);
    }

    /**
     * registering BroadcastReceiver
     */
    private void registerReceiver() {
        /* create filter for exact intent what we want from other intent */
        IntentFilter intentFilter = new IntentFilter(
                TextCapitalizeResultReceiver.ACTION_TEXT_CAPITALIZED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        /* create new broadcast receiver */
        capitalizeResultReceiver = new TextCapitalizeResultReceiver();
        /* registering our Broadcast receiver to listen action */
        registerReceiver(capitalizeResultReceiver, intentFilter);
    }

    public class TextCapitalizeResultReceiver extends BroadcastReceiver {

        /**
         * action string for our broadcast receiver to get notified
         */
        public final static String ACTION_TEXT_CAPITALIZED = "com.phonepuku.activities.intent.action.ACTION_TEXT_CAPITALIZED";

        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            String resultText = intent.getStringExtra(MyServices.OUTPUT_TEXT);
            callHistoryArrayList = intent.getStringArrayListExtra(MyServices.OUTPUT_TEXT_2);
            map = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra(MyServices.OUTPUT_TEXT_3);
            callHistory = resultText;
        }
    };

    @Override
    protected void onPause() {
        /* we should unregister BroadcastReceiver here */
        unregisterReceiver(capitalizeResultReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        // Obtain the sharedPreference, default to true if not available
        boolean isSplashEnabled = true;

        if (isSplashEnabled) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Finish the splash activity so it can't be returned to.
                    SplashActivity.this.finish();
                    // Create an Intent that will start the main activity.
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mainIntent.putExtra(MyServices.OUTPUT_TEXT, callHistory);
                    mainIntent.putExtra(MyServices.OUTPUT_TEXT_2, callHistoryArrayList);
                    mainIntent.putExtra(MyServices.OUTPUT_TEXT_3, map);
                    SplashActivity.this.startActivity(mainIntent);
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            // if the splash is not enabled, then finish the activity immediately and go to main.
            finish();
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            mainIntent.putExtra(MyServices.OUTPUT_TEXT, callHistory);
            mainIntent.putExtra(MyServices.OUTPUT_TEXT_2, callHistoryArrayList);
            mainIntent.putExtra(MyServices.OUTPUT_TEXT_3, map);
            SplashActivity.this.startActivity(mainIntent);
        }

        /* we register BroadcastReceiver here */
        registerReceiver();
        super.onResume();
    }
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.phonepuku.service.MyServices".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}