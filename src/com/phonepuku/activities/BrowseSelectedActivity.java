package com.phonepuku.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 *
 * @author Mathebula Mahlatse
 */

public class BrowseSelectedActivity extends Activity {

	// use KEY_CONTACT as KEY in hashMap
    static final String KEY_CONTACT = "contact";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_selected);
		
		// Get infor from previous activity
        Intent intent = getIntent();
        
        // Get String from the intent
        String SelectedContact = intent.getStringExtra(KEY_CONTACT);
        
        // Get reference of the textView in the current layout
        TextView textView = (TextView) findViewById(R.id.call);
        
        // Remove unwanted characters such as "," from the String
        String[] regularExpressonContact = SelectedContact.split(",");
        
        // Set information to the textView
        textView.setText("\nPhone number: " + regularExpressonContact[0].substring(1, 
                regularExpressonContact[0].length())
                + " \nName: " + regularExpressonContact[1]
                + " \nIncoming calls: " + regularExpressonContact[2]
                + " \nOutgoing calls: " + regularExpressonContact[3]
                + " \nMissed calls: " + regularExpressonContact[4]
                + " \nCall duration in sec: " + regularExpressonContact[5]
                + " \nLast call in sec: " + regularExpressonContact[6]
                + "\nLast call: " + regularExpressonContact[7].substring(0, 
                regularExpressonContact[7].length() - 1));
    }
}