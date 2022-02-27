package Huffmanalgo;

import java.util.*;
import java.io.*;
import java.nio.file.*;


public class Binary {
	
	private static char [] readchar = new char[999];
	private static int [] chfreq = new int[999];
	private static char [] txt = new char[9999999];
	private static int count;
	private static int countxt;
	private static HashMap<Character , String> dict = new HashMap<Character , String>(); 
	private static Node root = null;
	private static String mypath;
	private static String sp = "";
	private static long ela ;
	private static boolean isLeaf (Node node){
		return (node.right == null && node.left == null);}
	private static Scanner mys = new Scanner(System.in);
	
	
	private static byte [] mybytes ;
	
	public static void binaryintro() throws IOException {
		System.out.println("1- To compress a binary file.");
		System.out.println("2- To decompress a binary file.");
		System.out.println("3- Exit.");
		int ans = mys.nextInt();
		if (ans == 3) {
			System.exit(0);
			System.out.println("Thank you for using the program.");
		}
		else if (ans == 1) {
		System.out.println("Please Enter the Path of The File You Wish to Compress.");
		mys.nextLine();
		mypath = mys.nextLine();
		readbinary(mypath);
		FindFreqB();
		BuildHuffmanB();
		FindCodeB("" ,root);
		replaceB(mypath);
		System.out.println("The Compression is done!");
		mys.nextLine();
		}
		else if (ans == 2) {
		    System.out.println("Please Enter the Path of The File You Wish to Decompress.");
			mys.nextLine();
			mypath=mys.nextLine();
		    BinaryDecompress.readhashB(mypath);
		    System.out.println("The Deompression is done!");
		    mys.nextLine();
		}
	}
	
	public static void readbinary(String path) throws IOException {
		
		mybytes = Files.readAllBytes(Paths.get(path));
		for(int u = 0; u <mybytes.length; u++) 
			sp += mybytes[u] +"`";		
		
	}
	public static void FindFreqB()throws IOException , NullPointerException{
		char finput;
	    int countb = 0;     
	    char[] btxt = new char[sp.length()];   	        
        for (int u = 0; u < sp.length(); u++) 
            btxt[u] = sp.charAt(u); 
	    while (countb < sp.length()) {	    	      	    	
	        finput = btxt[countb++];
	        txt[countxt++] = finput;     
	        int tempU=0;	        
	            for(int zz=0 ;zz <= count; zz++){
	                if (readchar[zz] == finput){
	                        chfreq[zz]++;
	                        tempU=1;
	                        break;
	                }
	            }
	         if (tempU==0){
	            readchar[count] = finput;
	            chfreq[count++] = 1;
	        }
	            else tempU=0;
	        }
	      }
	
	public static void BuildHuffmanB() {	
		 PriorityQueue<Node> prio = new PriorityQueue<Node>(count, new MyComp()); 
		  
			for (int i = 0; i < count; i++) { 
//adding the letters to the priority queue using comparator to sort them
				Node node = new Node();  
				
				node.ch = readchar[i]; 
				node.fr = chfreq[i]; 
				node.right = null; 
				node.left = null; 
				prio.add(node); 
				} 

				while (prio.size() > 1) { //till there is only one in the priority q
				  Node a = prio.poll(); 
				  Node b = prio.poll(); 
				  Node z = new Node(); //creating a new node to be a parent for the 2 next in queue
				  z.ch = '$'; 
				  z.fr = a.fr + b.fr; 			  
				  z.right = a; 
				  z.left = b; 
				  prio.add(root = z); //send the node back to the queue
				}
				
			}
	
	public static void FindCodeB(String tempS , Node root) throws NullPointerException
	{ 
		if (!isLeaf(root))
		{

			FindCodeB(tempS + "1" , root.right); //send one with the right
			FindCodeB(tempS + "0" , root.left);	//send zero with left
		   
		} 
		else {
			//System.out.println(root.ch + "=" + tempS); 
			   dict.put(root.ch , tempS); //populate the hashmap with ch/code
		}
	}

	public static void replaceB(String thepath) throws FileNotFoundException, UnsupportedEncodingException {

		thepath = thepath + ".zakb";
		PrintWriter writer = new PrintWriter(thepath , "UTF-8");
		writer.print(thepath.replace(".zakb" , ""));
		writer.print('~');
		for (int u = 0 ; u < count ;u++) {
			writer.print(readchar[u] + dict.get(readchar[u])  + '~');
		}
		writer.println('$');
		for (int u = 0 ; u < countxt ; u++) {
			//System.out.println(dict.get(txt[u]));		
			writer.print(dict.get(txt[u]));
		}
		writer.close();
	}

}
