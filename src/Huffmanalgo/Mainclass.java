package Huffmanalgo;

import java.io.*; 
import java.util.*;
import Huffmanalgo.Decompress;
import Huffmanalgo.Folderhandle;
import Huffmanalgo.Binary;


public class Mainclass {
	
	private static char [] readchar = new char[999];
	private static int [] chfreq = new int[999];
	private static char [] txt = new char[9999999];
	private static int count;
	private static int countxt;
	private static HashMap<Character , String> dict = new HashMap<Character , String>(); 
	private static Node root = null;
	private static String mypath;
	private static long ela ;
	private static boolean isLeaf (Node node){
		return (node.right == null && node.left == null);}
	private static Scanner mys = new Scanner(System.in);
	
	public static void FindFreq(File file)throws IOException , NullPointerException{

		
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

	public static void replace(String thepath) throws FileNotFoundException, UnsupportedEncodingException {
		//c:\\Users\\zako_\\Desktop\\input.txt
		thepath = thepath + ".zak";
		PrintWriter writer = new PrintWriter(thepath , "UTF-8");
		writer.print(thepath.replace(".zak" , ""));
		writer.print('`');
		for (int u = 0 ; u < count ;u++) {
			writer.print(readchar[u] + dict.get(readchar[u])  + '`');
		}
		writer.println('§');
		for (int u = 0 ; u < countxt ; u++) {
			//System.out.println(dict.get(txt[u]));		
			writer.print(dict.get(txt[u]));
		}
		writer.close();
	}
	
	public static int intro() {
		  System.out.println("Welcome to the Huffman Compression & Decompression .");
		  System.out.println("====================================================");
		  System.out.println("1- Compress a File.");
		  System.out.println("2- Decompress a File.");
		  System.out.println("3- Binary Files.");
		  System.out.println("4- Exit");
		  int ans = mys.nextInt();
        if (ans == 4) {
			  System.out.println("Thank You For Using The Program.");
			  System.exit(0);
            }
		  if (ans > 4) {
			  System.out.println("Please Try Again .");
			  ans = intro();
		      }
		return ans;
	}
	
	public static void Compression()throws IOException , NullPointerException {
		System.out.println("Enter The path of The File You Wish to Compress .");
		mys.nextLine();
		mypath = mys.nextLine();
		long t1 = System.currentTimeMillis();
		File file = new File(mypath);
		double size1 = 0;
		double size2 = 0;
		if (!file.isDirectory()) {
	    size1 = (double) file.length() / (1024 * 1024);
		FindFreq(file);
		BuildHuffman();
		FindCode("" , root); 
	    replace(mypath);
	    File compfile = new File(mypath + ".zak");
		size2 = (double) compfile.length() / (1024 * 1024);
		}
		else {
			File[] listOfFiles = file.listFiles();
			for (File files : listOfFiles) {
				Folderhandle.folderfreq(files);
			}
			Folderhandle.BuildHuffman();
			Folderhandle.replacefolder(file);
		}
		long t2 = System.currentTimeMillis();
		ela = t2 - t1;
	    System.out.println("Compression is done and the compressed file is : " + mypath);
	    System.out.println("The Compression ratio is : " + (int)((size2 / size1)*100) + "%");
	    System.out.println("The Execution time : " + ela + "ms");
	    mys.nextLine();
	}

	public static void Decompression()throws IOException , NullPointerException {
		
		System.out.println("Enter The path of The File You Wish to Decompress .");
		mys.nextLine();
		mypath = mys.nextLine();
		//mypath = "infol.zakf";
		double size1 = 0;
		double size2 = 0;
		long t1 = System.currentTimeMillis();
		if (mypath.indexOf(".zakf") != -1) {
			Folderdecompress.readhash(mypath);
		}
		else {
			File mysize1 = new File(mypath);
			size1 = (double) mysize1.length() / (1024 * 1024);
		    Decompress.readhash(mypath);
		    Decompress.decompress();
		    String sizestring = Decompress.writeDecompressed();
		    File mysize2 = new File(sizestring);
			size2 = (double) mysize2.length() / (1024 * 1024);
		}
		long t2 = System.currentTimeMillis();
		ela = t2 - t1;
		System.out.println("Decompression is done and the decompressed file is : " + mypath.replace(".zak" , "-decompressed.txt"));
	    System.out.println("The Compression ratio is : " + (int)((size1 / size2)*100) + "%");
	    System.out.println("The Executuion time : " + ela+ "ms");
	    mys.nextLine();
	}
	public static void main(String[] args)throws IOException , NullPointerException {
		  while (true) {
			  int ans = intro();
			  if(ans == 1)
			  Compression();
			  else if (ans == 2)
			  Decompression();
			  else if (ans ==3)
		      Binary.binaryintro();
		  }
	}

}
