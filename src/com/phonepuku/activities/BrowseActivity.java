package com.phonepuku.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.phonepuku.service.MyServices;

/**
 *
 * @author Mathebula Mahlatse
 */

public class BrowseActivity extends Activity {


    // The list to hold contacts
    ListView lv;
    // An adapter to handle the layout
    ArrayAdapter<String> adapter;
    // The Edit option to help search items in the list
    EditText inputSearch;
    // The map to hold phone numbers as a key and call history details as value
    static HashMap<String, ArrayList<String>> map;
    ArrayList<String> arrayList;
    // use KEY_CONTACT as KEY in hashMap
    static final String KEY_CONTACT = "contact";
    
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		
		Intent intent = getIntent();
		arrayList = intent.getStringArrayListExtra(MyServices.OUTPUT_TEXT_2);
		map = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra(
                        MyServices.OUTPUT_TEXT_3);

		// Get references of the list
        lv = (ListView) findViewById(R.id.list_view);
        // Get reference of the search option
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Initialize the adapter
        adapter = new ArrayAdapter<String>(this, R.layout.list, R.id.name, arrayList);
        // Set the adapter to the list
        lv.setAdapter(adapter);
        // Set listener on the search option
        inputSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BrowseActivity.this.adapter.getFilter().filter(s);
            }

            public void afterTextChanged(Editable s) {
            }
        });

        // Set listener on the list of contacts
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();

                // Open a new activity
                Intent in = new Intent(getApplicationContext(), BrowseSelectedActivity.class);
                /*
                 * Put extra information in the intent
                 * map.get(name) get the value associated with an item clicked in the list
                 */
                in.putExtra(KEY_CONTACT, map.get(name).toString());
                // Start a new activity
                startActivity(in);
            }
        });
    }
}