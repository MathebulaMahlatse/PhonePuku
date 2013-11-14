package com.phonepuku.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import com.phonepuku.activities.SplashActivity.TextCapitalizeResultReceiver;

/**
 * @author Mathebula_WM
 *
 */
public class MyServices extends IntentService {

    public static final String OUTPUT_TEXT = "OUTPUT_TEXT";
    public static final String OUTPUT_TEXT_2 = "OUTPUT_TEXT_2";
    public static final String OUTPUT_TEXT_3 = "OUTPUT_TEXT_3";
    /*
     * Create a new Object
     * This ArrayList holds phone Numbers
     */
    static ArrayList<String> arrayList;
    static HashMap<String, ArrayList<String>> map;

    /**
     * initiate service in background thread with service name
     */
    public MyServices() {
        super(MyServices.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get call history
        String callHistory = getCallDetails();

        /* create new intent to broadcast our processed data to our activity */
        Intent broadcast = new Intent();

        /* set action here */
        broadcast.setAction(TextCapitalizeResultReceiver.ACTION_TEXT_CAPITALIZED);

        /* set intent category as default */
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);

        /* add data to intent */
        broadcast.putExtra(OUTPUT_TEXT, callHistory);
        broadcast.putExtra(OUTPUT_TEXT_2, arrayList);
        broadcast.putExtra(OUTPUT_TEXT_3, map);

        /* send broadcast */
        sendBroadcast(broadcast);

    }

    private String getCallDetails() {
        /*
         * Create a new Object This ArrayList Holds an ArrayList
         */
        ArrayList<ArrayList<String>> listOfArray = new ArrayList<ArrayList<String>>();
        /*
         * Create a new Object This ArrayList Holds contacts information
         */
        ArrayList<String> callDetails = new ArrayList<String>();

        // String buffer to hold Strings to display in the layout
        StringBuilder sb = new StringBuilder();
        // A cursor to hold call logs
        Cursor managedCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, null, null, null, null);
        // Get the number
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        // Get the name
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        // Get the type of the call
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        // Get the date of the call
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        // Get the time of the call
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        // Check whether the cursor has more elements
        while (managedCursor.moveToNext()) {
            // Get the phone number
            String phNumber = managedCursor.getString(number);
            // Get the name
            String nameType = managedCursor.getString(name);
            // Get the type of the call
            String callType = managedCursor.getString(type);
            // Get the date of the call
            String callDate = managedCursor.getString(date);
            // Get the date of the call
            Date callDayTime = new Date(Long.valueOf(callDate));
            // Get the length of the call
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            // Add phone number to the ArrayList
            callDetails.add(phNumber);
            // Check whether the name is null
            if (nameType != null) {
                // Add the name to the ArrayList
                callDetails.add(nameType);
            } else {
                // Add "Uknown" to the ArrayList
                callDetails.add("Unknown");
            }
            // Add time to the ArrayList
            callDetails.add(callDuration);
            // Add String Date to the ArrayList
            callDetails.add(callDayTime.toString());
            // Add call type to the ArrayList
            callDetails.add(dir);
            // Add ArrayList to the ArrayList
            listOfArray.add(callDetails);
            // Create a new object
            callDetails = new ArrayList<String>();
        }
        // Close the cursor
        managedCursor.close();

        // initialize all contact information to ZERO
        int outcoming = 0;
        int incoming = 0;
        int seconds = 0;
        int lastCallInSec = 0;
        int missed = 0;
        String contactName = "Unknown";
        String lastCall = "";

        /*
         * Create a new Object This ArrayList holds phone numbers
         */
        ArrayList<String> phonenumbers = new ArrayList<String>();

        /*
         * Create a new Object
         * The map to hold phone numbers as a key and call history details as value
         */
        map = new HashMap<String, ArrayList<String>>();

        arrayList = new ArrayList<String>();

        ArrayList<String> array1 = new ArrayList<String>();
        ArrayList<String> array2 = new ArrayList<String>();

        // Loop through the ArrayList holding contact information as ArrayList
        for (int i = 0; i < listOfArray.size(); i++) {
            /*
             * Create a new Object Store elements of the old ArrayList to the
             * new ArrayList
             */
            ArrayList<String> callDetail = listOfArray.get(i);
            /*
             * Create a new Object Store phone number from the ArrayList
             */
            String phoneNumber = callDetail.get(0);
            // Check whether the phone number already exists in the ArrayList of
            // phone Numbers
            if (phonenumbers.contains(phoneNumber)) {
                // Do nothing
            } else {
                // Add the phone number to the ArrayList
                phonenumbers.add(phoneNumber);
                // Loop through the ArrayList
                for (int j = 0; j < listOfArray.size(); j++) {
                    /*
                     * Create a new Object Store elements of the old ArrayList
                     * to the new ArrayList
                     */
                    ArrayList<String> callDetail2 = listOfArray.get(j);
                    // Get phone number from the ArrayList
                    String phoneNumberCompare = callDetail2.get(0);
                    // Compare, check if the phone number repreats its self in
                    // the Arraylist
                    if (phoneNumberCompare.equals(phoneNumber)) {

                        // If the contact Arrylist has incoming calls
                        if (callDetail2.get(4).equals("INCOMING")) {
                            // increment
                            incoming++;
                        }

                        // If the contact Arrylist has outgoing calls
                        if (callDetail2.get(4).equals("OUTGOING")) {
                            // increment
                            outcoming++;
                        }

                        // If the contact Arrylist has missed calls
                        if (callDetail2.get(4).equals("MISSED")) {
                            // increment
                            missed++;
                        }

                        // Get the date of the last call
                        lastCall = callDetail2.get(3);
                        // Get contact name
                        contactName = callDetail2.get(1);
                        // Get last a time of the last call
                        lastCallInSec = Integer.parseInt(callDetail2.get(2));
                        // Get the total time of the current phone number
                        seconds += Integer.parseInt(callDetail2.get(2));
                    }
                }

                // Append to the String Buffer
                sb.append("\nPhone number: " + phoneNumber + " \nName: "
                        + contactName + " \nIncoming calls: " + incoming
                        + " \nOutgoing calls: " + outcoming
                        + " \nMissed calls: " + missed
                        + " \nLast call in sec: " + lastCallInSec
                        + " \nCall duration in sec: " + seconds
                        + "\nLast call: " + lastCall);
                sb.append("\n----------------------------------");

                //arrayList.add(phoneNumber);
                arrayList.add(contactName + " " + phoneNumber);
                array1.add(contactName);
                array2.add(phoneNumber);

                ArrayList<String> list = new ArrayList<String>();
                list.add(phoneNumber);
                list.add(contactName);
                list.add(Integer.toString(incoming));
                list.add(Integer.toString(outcoming));
                list.add(Integer.toString(missed));
                list.add(Integer.toString(seconds));
                list.add(Integer.toString(lastCallInSec));
                list.add(lastCall.toString());
                map.put(contactName + " " + phoneNumber, list);

                // Initialize to ZERO
                incoming = 0;
                outcoming = 0;
                seconds = 0;
                missed = 0;
                lastCallInSec = 0;
            }
        }

        String Array1[] = new String[array1.size()];
        for (int i = 0; i < Array1.length; i++) {
            Array1[i] = array1.get(i);
        }

        String Array2[] = new String[array2.size()];
        for (int i = 0; i < Array2.length; i++) {
            Array2[i] = array2.get(i);
        }

        for (int j = 0; j < Array1.length; j++) {
            for (int i = j + 1; i < Array1.length; i++) {
                if (Array1[i].compareTo(Array1[j]) < 0) {
                    String temp = Array1[j];
                    Array1[j] = Array1[i];
                    Array1[i] = temp;
                }

            }

        }
        // Return String Buffer as a string
        return sb.toString();
    }
}
