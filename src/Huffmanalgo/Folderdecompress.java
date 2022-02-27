package Huffmanalgo;

import java.io.*; 
import java.util.*;

public class Folderdecompress {
	
	private static char [] detext = new char[9999999];
	private static char [] text = new char[99999999];
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
	        if (finput == '{')
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
	      finput = (char) instream.read();
	      finput = (char) instream.read();
	      while (instream.available() > 0) {
	    	  finput = (char) instream.read();
	    	  text[txtcount++] = finput;
	      }
	      readfiles();
 }
	public static void readfiles() throws FileNotFoundException, UnsupportedEncodingException {
		char [] filepath = new char [index+2];
		filepath[0]='d';
		filepath[1]='-';
		for (int l = 0; l<index ;l++)
			filepath[l+2] = outpath[l];
		  File myfolder = new File(String.valueOf(filepath));
	      myfolder.mkdir();
		String stemp = "", sstemp = "";
		int ss = 0 , fileindex = 0;
		while (text[ss] != '{' && ss < txtcount) {
		while (text[ss] != '}') {
			stemp =  stemp + text[ss++];
			//System.out.println("the equal loop");
		}
		ss++;
		while (text [ss]!= '~') {
			detext[decount++] = text[ss++];
			//System.out.println("approx loop");
		}
		ss++;
		String g = String.valueOf(stemp) + "-decompressed.txt";
		PrintWriter writer = new PrintWriter(g , "UTF-8");
		for(int y = fileindex ; y < decount ; y++) {
			//System.out.println("for loop");
			sstemp = sstemp + String.valueOf(detext[y]);
			if (dict.containsKey(sstemp)) {
			writer.print(dict.get(sstemp));
			sstemp = "";
		}	
	 }
		stemp = "";		
		fileindex = decount;
		writer.close();
   }
 }
}

