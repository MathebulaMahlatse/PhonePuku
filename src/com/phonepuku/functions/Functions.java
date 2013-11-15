package com.phonepuku.functions;

import java.util.ArrayList;

/**
 *
 * @author Mathebula Mahlatse
 */
public class Functions {
    public Functions(){
        
    }
    /*
     * Sorting arrayList
     */
    public void sort(String[] Array1, String[] Array2){
        
         for (int j = 0; j < Array1.length; j++) {
            for (int i = j + 1; i < Array1.length; i++) {
                if (Array1[i].compareTo(Array1[j]) < 0) {
                    String temp = Array1[j];
                    Array1[j] = Array1[i];
                    Array1[i] = temp;
                    
                    String temp2 = Array2[j];
                    Array2[j] = Array2[i];
                    Array2[i] = temp2;
                }
            }
        }
    }
    /*
     * Copy ArrayList to String[]
     */
    public void copyArrayListToStringArray( String [] array, ArrayList<String> arrayList){
        for (int i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
    }
    public void copyStringArrayToArrayList(ArrayList<String> arrayList, String[] Array1, String[] Array2){
        arrayList.clear();
        for(int i = 0; i < Array1.length; i++){
            arrayList.add(Array1[i] + " " + Array2[i]);
        }
        
    }
}
