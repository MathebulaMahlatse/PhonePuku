package com.phonepuku.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.phonepuku.functions.Initialisation;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mathebula Mahlatse
 */
public class MainActivity extends Activity {
    
    Initialisation initialisaton = new Initialisation();
    /*
     * action string for our broadcast receiver to get notified
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
         * Receive data from previous Activity
         * receive infor such as call history as a String, contact details as ArrayList, and HashMap which holds all call history
         */

        Intent dataFromSplash = getIntent();
        initialisaton.callHistory = dataFromSplash.getStringExtra(initialisaton.KEY_STRING);
        initialisaton.arrayList = dataFromSplash.getStringArrayListExtra(initialisaton.KEY_ARRAYLIST);
        initialisaton.map = (HashMap<String, ArrayList<String>>) dataFromSplash.getSerializableExtra(
                initialisaton.KEY_HASHMAP);


        // Get reference of the first image button
        initialisaton.imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        // Set a listener on the image button
        initialisaton.imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open a new activity

                Intent intent = new Intent(getApplicationContext(),
                        BrowseActivity.class);
                intent.putExtra(initialisaton.KEY_ARRAYLIST, initialisaton.arrayList);
                intent.putExtra(initialisaton.KEY_HASHMAP, initialisaton.map);
                startActivity(intent);
            }
        });

        // Get reference of the first image button
        initialisaton.imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        // Set a listener on the image button
        initialisaton.imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open a new activity
                Intent intent = new Intent(getApplicationContext(),
                        HistoryActivity.class);
                intent.putExtra(initialisaton.KEY_STRING, initialisaton.callHistory);
                startActivity(intent);
            }
        });
    }
}