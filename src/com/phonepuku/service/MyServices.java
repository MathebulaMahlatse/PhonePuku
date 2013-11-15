package com.phonepuku.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import com.phonepuku.functions.Functions;
import com.phonepuku.functions.Initialisation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Mathebula_WM
 *
 */
public class MyServices extends IntentService {
    
    public MyServices() {
        super(MyServices.class.getSimpleName());
    }
    
    Functions functions = new Functions();
    Initialisation initialisaton = new Initialisation();

    @Override
    protected void onHandleIntent(Intent intent) {
        // get call history
         initialisaton.callHistory = getCallDetails();

        /* create new intent to broadcast our processed data to our activity */
        initialisaton.intent = new Intent();

        /* set action here */
        initialisaton.intent.setAction(initialisaton.ACTION_TEXT_CAPITALIZED);

        /* set intent category as default */
        initialisaton.intent.addCategory(Intent.CATEGORY_DEFAULT);

        /* add data to intent */
        initialisaton.intent.putExtra(initialisaton.KEY_STRING, initialisaton.callHistory);
        initialisaton.intent.putExtra(initialisaton.KEY_ARRAYLIST, initialisaton.arrayList);
        initialisaton.intent.putExtra(initialisaton.KEY_HASHMAP, initialisaton.map);

        /* send broadcast */
        sendBroadcast(initialisaton.intent);

    }

    private String getCallDetails() {
       
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
            initialisaton.callDetails.add(phNumber);
            // Check whether the name is null
            if (nameType != null) {
                // Add the name to the ArrayList
                initialisaton.callDetails.add(nameType);
            } else {
                // Add "Uknown" to the ArrayList
                initialisaton.callDetails.add("Unknown");
            }
            // Add time to the ArrayList
            initialisaton.callDetails.add(callDuration);
            // Add String Date to the ArrayList
            initialisaton.callDetails.add(callDayTime.toString());
            // Add call type to the ArrayList
            initialisaton.callDetails.add(dir);
            // Add ArrayList to the ArrayList
            initialisaton.listOfArray.add(initialisaton.callDetails);
            // Create a new object
            initialisaton.callDetails = new ArrayList<String>();
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
         * Create a new Object
         * The map to hold phone numbers as a key and call history details as value
         */
        initialisaton.map = new HashMap<String, ArrayList<String>>();

        initialisaton.arrayList = new ArrayList<String>();

        // Loop through the ArrayList holding contact information as ArrayList
        for (int i = 0; i < initialisaton.listOfArray.size(); i++) {
            /*
             * Create a new Object Store elements of the old ArrayList to the
             * new ArrayList
             */
            ArrayList<String> callDetail = initialisaton.listOfArray.get(i);
            /*
             * Create a new Object Store phone number from the ArrayList
             */
            String phoneNumber = callDetail.get(0);
            // Check whether the phone number already exists in the ArrayList of
            // phone Numbers
            if (initialisaton.phonenumbers.contains(phoneNumber)) {
                // Do nothing
            } else {
                // Add the phone number to the ArrayList
                initialisaton.phonenumbers.add(phoneNumber);
                // Loop through the ArrayList
                for (int j = 0; j < initialisaton.listOfArray.size(); j++) {
                    /*
                     * Create a new Object Store elements of the old ArrayList
                     * to the new ArrayList
                     */
                    ArrayList<String> callDetail2 = initialisaton.listOfArray.get(j);
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
                initialisaton.sb.append("\nPhone number: " + phoneNumber + " \nName: "
                        + contactName + " \nIncoming calls: " + incoming
                        + " \nOutgoing calls: " + outcoming
                        + " \nMissed calls: " + missed
                        + " \nLast call in sec: " + lastCallInSec
                        + " \nCall duration in sec: " + seconds
                        + "\nLast call: " + lastCall);
                initialisaton.sb.append("\n----------------------------------");

                //arrayList.add(phoneNumber);
                initialisaton.arrayList.add(contactName + " " + phoneNumber);
                initialisaton.array1.add(contactName);
                initialisaton.array2.add(phoneNumber);

                ArrayList<String> list = new ArrayList<String>();
                list.add(phoneNumber);
                list.add(contactName);
                list.add(Integer.toString(incoming));
                list.add(Integer.toString(outcoming));
                list.add(Integer.toString(missed));
                list.add(Integer.toString(seconds));
                list.add(Integer.toString(lastCallInSec));
                list.add(lastCall.toString());
                initialisaton.map.put(contactName + " " + phoneNumber, list);

                // Initialize to ZERO
                incoming = 0;
                outcoming = 0;
                seconds = 0;
                missed = 0;
                lastCallInSec = 0;
            }
        }

        // Initialize Array of Strings
        String Array1[] = new String[initialisaton.array1.size()];
        String Array2[] = new String[initialisaton.array2.size()];        
        
        /*
         * Copy ArrayList to String[]
         */
        functions.copyArrayListToStringArray(Array1, initialisaton.array1);
        functions.copyArrayListToStringArray(Array2, initialisaton.array2);

        /*
         * Sort 2 String[]
         */
        functions.sort(Array1, Array2);
        
        /*
         * Copy String[] to ArrayList
         */        
        functions.copyStringArrayToArrayList(initialisaton.arrayList, Array1, Array2);
        // Return String Buffer as a string
        return initialisaton.sb.toString();
    }
}