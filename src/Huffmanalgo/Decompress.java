package Huffmanalgo;

import java.io.*; 
import java.util.*;

public class Decompress {
	
	private static char [] detext = new char[9999999];
	private static char [] text = new char[9999999];
	private static char [] outpath = new char[100];
	private static int txtcount = 0;
	private static int decount = 0;
	private static int index = 0;
	private static HashMap<String , Character> dict = new HashMap<String , Character>(); 
	
	public static void readhash(String path)throws IOException {
		File file = new File(path); 
		  FileInputStream instream = new FileInputStream(file);
	      char finput = '?';
	      char tempc = ' ';
	      String temps = "";
	      boolean next = true;
	      while (instream.available() > 0) {
	    	  finput = (char) instream.read();
	      if(finput == '`')
	    	  break;
	          outpath[index++] = finput;
	      }
	      while (instream.available() > 0) {	    	  
	        finput = (char) instream.read();
	        //System.out.print(finput);
	        if (finput == '§')
	        	break;
	        else if (finput == '`') {
	        	dict.put(temps , tempc);
	        	//System.out.println(temps + "=" + tempc);
	        	temps = "";
	        	next = true;
	        	continue;
	        }	       
	      if (next == false) {
	    	 // System.out.print(finput);
	    	  temps = temps + finput; //string of code for each char
	      }
	      else  {
	    	  tempc = finput; //temp for char
	      }
             next = false;
	      }
	      while (instream.available() > 0) {
	    	  finput = (char) instream.read();
	    	  text[txtcount++] = finput;
	      }
	        
 }
	public static void decompress() {
		String tempde = "";
		for (int u = 2; u < txtcount ; u++) { //begin from 2 to compensate for the empty space and line
			
			tempde = tempde + String.valueOf(text[u]);
			//System.out.print(tempde);
			if (dict.containsKey(tempde)) {
				//System.out.print(dict.get(tempde));
				detext[decount++] = dict.get(tempde);
			    tempde = "";
			    }
		}
		//for (int y = 0 ; y < decount ; y++)
			 // System.out.print(detext[y]);
	}
	
	public static String writeDecompressed()throws FileNotFoundException, UnsupportedEncodingException {
		char [] filepath = new char[index];
		for (int l = 0; l<index ;l++)
			filepath[l] = outpath[l];
		String g = String.valueOf(filepath) + "-decompressed.txt";
		PrintWriter writer = new PrintWriter(g , "UTF-8");
		for ( int u = 0 ; u < decount ; u++)
			writer.print(detext[u]);
		writer.close();
		return g ;
	}
	
	

}
