package Huffmanalgo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class BinaryDecompress {
	
	private static char [] detext = new char[9999999];
	private static char [] text = new char[9999999];
	private static char [] outpath = new char[100];
	private static int txtcount = 0;
	private static int decount = 0;
	private static int index = 0;
	private static int bindex = 0;
	private static HashMap<String , Character> dict = new HashMap<String , Character>();
	private static byte [] bch = new byte[999999];
	private static String [] ii;
	
	public static void readhashB(String path)throws IOException {
		File file = new File(path); 
		  FileInputStream instream = new FileInputStream(file);
	      char finput = '?';
	      char tempc = ' ';
	      String temps = "";
	      boolean next = true;
	      while (instream.available() > 0) {
	    	  finput = (char) instream.read();
	      if(finput == '~')
	    	  break;
	          outpath[index++] = finput;
	      }
	      while (instream.available() > 0) {	    	  
	        finput = (char) instream.read();
	        //System.out.print(finput);
	        if (finput == '$')
	        	break;
	        else if (finput == '~') {
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
	      decompress();
	      chartobyte();
	      writeDecompressedB();
	        
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
			  //System.out.print(detext[y]);
	}
	public static void chartobyte() throws IOException{

		StringBuilder sb = new StringBuilder();
		for (int p = 0 ; p<decount ; p++) {
			sb.append(detext[p]);
		}
		String kk = sb.toString();
		ii = kk.split("`");
		for(int i = 0 ; i< ii.length ; i++) {
		bch[i] = Byte.parseByte(ii[i]);
		bindex++;
		}
     		
	}
	
	public static void writeDecompressedB()throws IOException , FileNotFoundException, UnsupportedEncodingException {
		char [] filepath = new char[index+2];
		filepath[0]='d';
		filepath[1]='-';
		for (int l = 0; l < index ;l++)
			filepath[l+2] = outpath[l];
		String g = String.valueOf(filepath);
		Files.write(Paths.get(g) , bch);
	}

}
