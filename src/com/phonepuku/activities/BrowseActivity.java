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
import com.phonepuku.functions.Initialisation;

/**
 *
 * @author Mathebula Mahlatse
 */
public class BrowseActivity extends Activity {

    Initialisation initialisaton = new Initialisation();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        Intent intent = getIntent();
        initialisaton.arrayList = intent.getStringArrayListExtra(initialisaton.KEY_ARRAYLIST);
        initialisaton.map = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra(
                initialisaton.KEY_HASHMAP);

        // Get references of the list
        initialisaton.lv = (ListView) findViewById(R.id.list_view);
        // Get reference of the search option
        initialisaton.inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Initialize the adapter
        initialisaton.adapter = new ArrayAdapter<String>(this, R.layout.list, R.id.name, initialisaton.arrayList);
        // Set the adapter to the list
        initialisaton.lv.setAdapter(initialisaton.adapter);
        // Set listener on the search option
        initialisaton.inputSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BrowseActivity.this.initialisaton.adapter.getFilter().filter(s);
            }

            public void afterTextChanged(Editable s) {
            }
        });

        // Set listener on the list of contacts
        initialisaton.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                in.putExtra(initialisaton.KEY_CONTACT, initialisaton.map.get(name).toString());
                // Start a new activity
                startActivity(in);
            }
        });
    }
}