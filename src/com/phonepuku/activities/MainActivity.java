package com.phonepuku.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.phonepuku.service.MyServices;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mathebula Mahlatse
 */
public class MainActivity extends Activity {

    String callHistory = "";
    ArrayList<String> callHistoryArrayList;
    static HashMap<String, ArrayList<String>> map;
    public ImageButton imageButton1;
    public ImageButton imageButton2;

    /*
     * action string for our broadcast receiver to get notified
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent dataFromSplash = getIntent();
        callHistory = dataFromSplash.getStringExtra(MyServices.OUTPUT_TEXT);
        callHistoryArrayList = dataFromSplash.getStringArrayListExtra(MyServices.OUTPUT_TEXT_2);
        map = (HashMap<String, ArrayList<String>>) dataFromSplash.getSerializableExtra(
                MyServices.OUTPUT_TEXT_3);


        // Get reference of the first image button
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        // Set a listener on the image button
        imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open a new activity

                Intent intent = new Intent(getApplicationContext(),
                        BrowseActivity.class);
                intent.putExtra(MyServices.OUTPUT_TEXT_2, callHistoryArrayList);
                intent.putExtra(MyServices.OUTPUT_TEXT_3, map);
                startActivity(intent);
            }
        });

        // Get reference of the first image button
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        // Set a listener on the image button
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open a new activity
                Intent intent = new Intent(getApplicationContext(),
                        HistoryActivity.class);
                intent.putExtra(MyServices.OUTPUT_TEXT, callHistory);
                startActivity(intent);
            }
        });
    }
}