package com.lowes.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvReader {
	
	public static void readCSV(){
		
		  CSVReader reader;
		  String ruta = Utilerias.getFilesPath()+"amex/";
		  
		try {
			
			reader = new CSVReader(new FileReader(ruta+"amex.csv"));
			String [] nextLine;
			
			List<String[]> myEntries = reader.readAll();
			String c = null;
			
			
//		     while ((nextLine = reader.readNext()) != null) {
//		        // nextLine[] is an array of values from the line
//		        System.out.println(nextLine[0] + nextLine[1] + "etc...");
//		     }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		     
		
	}
	
}
