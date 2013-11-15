package com.phonepuku.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.phonepuku.functions.Initialisation;

public class HistoryActivity extends Activity {

    Initialisation initialisaton = new Initialisation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        Intent intent = getIntent();
        initialisaton.callHistory = intent.getStringExtra(initialisaton.KEY_STRING);
        // Get reference of the textView in the current layout
        TextView textView = (TextView) findViewById(R.id.call);

        // Set text to the layout
        textView.setText(initialisaton.callHistory);
    }
}
