package com.msd.util;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;
import java.util.List;  
  
public class readCommittees {  
  
	//NOTICE: the format of the file name should be "XXX2000-pc.txt" 

    public static void main(String[] args) {  
        String filePath = "/Users/AnkitaNallana/Desktop/committees/ecoop2002-pc.txt";  
        readFileByLines(filePath);  
    }  
      
 

    public static ArrayList<ArrayList<String>> readFileByLines(String filePath) {
    	//This function takes the whole path to the file as input,
    	//Returns the ArrayList of String containing all information 
    	//about all committees in the file
    	// stores the whole information of committees
    	ArrayList<ArrayList<String>> Info = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<String>> finale = new ArrayList<ArrayList<String>>();
    	
        File file = new File(filePath);  
        String[] splitFilePath = filePath.split("/");
        String rawFileName = splitFilePath[splitFilePath.length-1];
        // to get the file name like "ecoop2009-pc.txt"
        String fileName= "";
        fileName= rawFileName.substring(0, rawFileName.length() - 7);
        //  to remove the "-pc.txt" part from the raw file name
        String confname = fileName.replaceAll("\\d+", "");
        // get the non-number part of the conference
  		String confyear = fileName.replaceAll("\\D+", ""); 
  		// get the number part of the conference
        
  		BufferedReader reader = null;  
        try {   
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            String[] item = null; 
            // item stores all strings after split on ":"
            while ((tempString = reader.readLine()) != null) {
            	List<String> information = new ArrayList<String>();
          		information.add(confname);
          		information.add(confyear);
                  item=tempString.split(":");
                    if(item!=null&&item.length>1){
                    	information.add(item[1]);
                    	//System.out.println(item[0]);  
                    	if(item[0].contains("G")){
                    		information.add("G");
                    	}
                    	else if (item[0].contains("P")){
                    		information.add("P");
                    	} 
                    	else if (item[0].contains("C")){
                    		information.add("C");
                    	}
                    	else if (item[0].contains("E")){
                    		information.add("E");
                    	}
                    	else{
                    		information.add("M");
                    	}
                    }
                    
                    else{
                    	information.add(item[0]);
                    	information.add("M");
                    }
                    
                Info.add((ArrayList<String>) information);
                
                //add information list to the array list Info
            }  
            reader.close();  
            
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }
        
        
       /* for (int i = 0; i < Info.size(); i++){
        	System.out.println(Info.get(i).toString());
        }*/
        
        return Info;
    }

 
  
}  