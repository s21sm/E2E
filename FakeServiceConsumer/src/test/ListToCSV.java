package test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListToCSV {
	
    private static boolean writeCSVToConsole = true;
    private static boolean writeCSVToFile = true;
   
    private static boolean sortTheList = true;
    
    public void write(ArrayList<String> rdg, String name) { 
        ListToCSV util = new ListToCSV();
        List<String> sampleList = rdg;
        String destinationCSVFile = "D:\\"+name;
        util.convertAndPrint(sampleList, writeCSVToConsole, writeCSVToFile, sortTheList, destinationCSVFile);
 
    }

    private void convertAndPrint(List<String> sampleList,
            boolean writeToConsole, boolean writeToFile, boolean sortTheList, String destinationCSVFile) {
        String commaSeparatedValues = "";
 
        /** If the list is not null and the list size is not zero, do the processing**/
        if (sampleList != null) {
            /** Sort the list if sortTheList was passed as true**/
            if(sortTheList) {
                Collections.sort(sampleList);
            }
            /**Iterate through the list and append comma after each values**/
            Iterator<String> iter = sampleList.iterator();
            while (iter.hasNext()) {
                commaSeparatedValues += iter.next() + ",";
            }
            /**Remove the last comma**/
            if (commaSeparatedValues.endsWith(",")) {
                commaSeparatedValues = commaSeparatedValues.substring(0,
                        commaSeparatedValues.lastIndexOf(","));
            }
        }
        /** If writeToConsole flag was passed as true, output to console**/
        if(writeToConsole) {
            System.out.println(commaSeparatedValues);
        }
        /** If writeToFile flag was passed as true, output to File**/      
        if(writeToFile) {
            try {
                FileWriter fstream = new FileWriter(destinationCSVFile, false);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(commaSeparatedValues);
                out.close();
                System.out.println("*** Also wrote this information to file: " + destinationCSVFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
    }

	

 


}
	

