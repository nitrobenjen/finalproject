package com.util;

import java.util.*;
import java.io.File;
import java.text.SimpleDateFormat;
 
public class MyFileRename {
	
     public static String rename(String originalfilename) {
    	 
          long currentTime = System.currentTimeMillis();
          SimpleDateFormat simDf = new SimpleDateFormat("HHMMmmyyyyssdd");
          Random r = new Random();
          String uniqueFileName = String.format("%04d%s", r.nextInt(1000), simDf.format(new Date(currentTime)));
 
          String newFileName = null;
          int dot = originalfilename.lastIndexOf(".");
          if (dot != -1) {
        	  newFileName = uniqueFileName + originalfilename.substring(originalfilename.lastIndexOf("."));  // includes "."
          }
          
          return newFileName;
     }
     
     public static String addName(String savePath, String originalfilename) {

 		// long currentTime = System.currentTimeMillis();
 		// SimpleDateFormat simDf = new SimpleDateFormat("HHMMmmyyyyssdd");
 		// Random r = new Random();
 		File upFile = new File(savePath+"\\"+originalfilename);
 		System.out.println("upFile:" +"\\"+ upFile);
 		int idx = 1;
 		String newFileName = originalfilename;
 		
 		while(true) {
 			if(!upFile.exists()) break;
 			String uniqueFileName = String.format("_%s", idx);
 			System.out.println("upFile:" + upFile);
 			newFileName = originalfilename.substring(0, originalfilename.lastIndexOf(".")) + uniqueFileName
 						+ originalfilename.substring(originalfilename.lastIndexOf(".")); // includes "."
 			//System.out.println(newFileName);
 			upFile = new File(savePath+"\\"+newFileName);
 			idx++;
 		}
 		System.out.println(newFileName);
 		return newFileName;
 	}

}
