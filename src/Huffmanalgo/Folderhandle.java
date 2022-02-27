package Huffmanalgo;

import java.io.*; 
import java.util.*;

class Node { 
	char ch; 
	int fr;
	Node left; 
	Node right; 
} 

class MyComp implements Comparator<Node> { 
	public int compare(Node a, Node b) 
	{ 
     int z = a.fr - b.fr;     
		return z; 
	} 
}


public class Folderhandle {
	
	private static char [] readchar = new char[9999];
	private static int [] chfreq = new int[9999];
	private static char [] txt = new char[9999999];
	private static int count;
	private static int countxt;
	private static HashMap<Character , String> dict = new HashMap<Character , String>(); 
	private static Node root = null;
	private static String mypath;
	private static long ela ;
	private static boolean isLeaf (Node node){
		return (node.right == null && node.left == null);}


	public static void folderfreq(File file)throws IOException , NullPointerException{
		
		FileInputStream instream = new FileInputStream(file);
	    char finput;     
	    while (instream.available() > 0) {	   
	    	
	        finput = (char) instream.read();
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
	    txt[countxt++] = '~';
	      }
	
	public static void BuildHuffman() {	
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
				FindCode("",root);
			}
	
	public static void FindCode(String tempS , Node root) throws NullPointerException
	{ 
		if (!isLeaf(root))
		{

			FindCode(tempS + "1" , root.right); //send one with the right
			FindCode(tempS + "0" , root.left);	//send zero with left
		   
		} 
		else {
			//System.out.println(root.ch + "=" + tempS); 
			   dict.put(root.ch , tempS); //populate the hashmap with ch/code
		}
	}
	
	public static void replacefolder(File fold) throws FileNotFoundException, UnsupportedEncodingException {
		String thepath = fold.getName() + ".zakf";
		PrintWriter writer = new PrintWriter(thepath , "UTF-8");
		writer.print(thepath.replace(".zakf" , ""));
		writer.print('`');
		for (int u = 0 ; u < count ;u++) 
			writer.print(readchar[u] + dict.get(readchar[u])  + '`');
		writer.println('{');		
		File[] listOfFiles = fold.listFiles();
		int tempp = 0;
		boolean flag = false;
		
		for (File files : listOfFiles) {		
			writer.print(files.getName());		
			writer.print('}');
			while(tempp < countxt && flag == false) {
				
				if (txt[tempp] != '~')
					writer.print(dict.get(txt[tempp]));
				else if (txt[tempp] == '~') {
					writer.print(txt[tempp]);
					flag = true;
					}
				tempp++;
				}
			flag = false;
		}
		writer.print('{');
		
		writer.close();
		
	}
		
}
