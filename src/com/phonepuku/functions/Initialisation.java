package com.phonepuku.functions;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mathebula_WM
 */
public class Initialisation {

    public Initialisation() {
    }
    /**
     * action string for our broadcast receiver to get notified
     */
    public final String ACTION_TEXT_CAPITALIZED = "com.phonepuku.activities.intent.action.ACTION_TEXT_CAPITALIZED";
    public final String KEY_STRING = "callHistoryString";
    public final String KEY_ARRAYLIST = "phoneNumberWithNameArrayList";
    public final String KEY_HASHMAP = "contactHistoryHashMap";
    // use KEY_CONTACT as KEY in hashMap
    public final String KEY_CONTACT = "contact";
    // The list to hold contacts
    public ListView lv;
    // An adapter to handle the layout
    public ArrayAdapter<String> adapter;
    // The Edit option to help search items in the list
    public EditText inputSearch;
    public String callHistory = "";
    public ImageButton imageButton1;
    public ImageButton imageButton2;
    /*
     * Create a new Object
     * This ArrayList holds phone Numbers
     */
    public ArrayList<String> arrayList;
    /*
     * Create a new Object
     * The map to hold phone numbers as a key and call history details as value
     */
    public HashMap<String, ArrayList<String>> map;
    /**
     * initiate service in background thread with service name
     */
    /*
     * Create a new Object This ArrayList Holds an ArrayList
     */
    public ArrayList<ArrayList<String>> listOfArray = new ArrayList<ArrayList<String>>();
    /*
     * Create a new Object This ArrayList Holds contacts information
     */
    public ArrayList<String> callDetails = new ArrayList<String>();
    // String buffer to hold Strings to display in the layout
    public StringBuilder sb = new StringBuilder();
    /*
     * Create a new Object This ArrayList holds phone numbers
     */
    public ArrayList<String> phonenumbers = new ArrayList<String>();
    public ArrayList<String> array1 = new ArrayList<String>();
    public ArrayList<String> array2 = new ArrayList<String>();
    
    public Intent intent;
}
