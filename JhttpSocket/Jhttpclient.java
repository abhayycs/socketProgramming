//package jhttp;

import java.io.*;
import java.net.*;
import java.util.*;

class Jhttpclient {
    private static Scanner sc;
	private static File newFile;

	static public void main(String args[]) throws Exception {
        String get = "";
        int thePort;
        String hostname;
        
        // get the host name
        try {
          hostname = new String(args[0]);
        } catch (Exception e) { hostname = new String("localhost");}
        
        // set the port to connect on
        try {
          thePort = Integer.parseInt(args[1]);
          if (thePort < 0 || thePort > 65535) thePort = 80;
        } catch (Exception e) { thePort = 80; }
        
        //client info header
        System.out.println("\nPlease wait Client is connecting to the Server...");
        System.out.println("\t port: "+ thePort + " Address: " + hostname);            
    	
        try{      
    		//create client socket
            Socket cs =new Socket(InetAddress.getByName(hostname),thePort); 
            //input-output stream reader 
            BufferedReader is = new BufferedReader (new InputStreamReader(cs.getInputStream()));
            PrintWriter os = new PrintWriter(cs.getOutputStream());

            //Enter GET request.
            System.out.println("\nEnter your Request followed by filename and HTTP version");
            //scanner input
            sc = new Scanner(System.in);
            String request=sc.nextLine();  
            //Send request message
            os.print(request+"\r\n\r\n");  
            os.flush();   
            
            //default extension
            String extension = "";
            //method invoked
            String method = "";
            System.out.println(method);
            

            String file = "";
            String version = "";
            //Tokernizer
            StringTokenizer st = new StringTokenizer(request);
            try{method = st.nextToken();}catch(Exception e) {}
            //find the file extension
            try{
            	file = st.nextToken(); 
            	int i = file.lastIndexOf('.');
	            if (i > 0) {
	                extension = file.substring(i+1);
	            }
            }catch(Exception e) {}
            //find the HTTP version
            try{version = st.nextToken();}catch(Exception e) {}

            //Server Response incase it is HTTP protocol
            if(version.startsWith("HTTP/")) {
	            System.out.println("\nServer Response: "); 
	            while ((get = is.readLine()) != null) {
	        	   	System.out.println("\t" + get);
	        	   	//set file extension according to content type
	                if(get.startsWith("Content-type: ")){
	                	extension = new String(get.substring(get.lastIndexOf('/')+1));
	                }
	          	    if (get.trim().equals("")) break;        
	            }
	            if(extension.equals("plain")){
	            	extension = new String("txt");
	            }
            }
            else {
            	extension = "html";
            }
            //System.out.println("extension:" + extension);

            //create the Download file
	        try {
	        	newFile = new File("download."+extension);
	        	if(newFile.createNewFile()) {
	        		System.out.println("File download complete!");
	        	}
	        	else {
	        		System.out.println("Download File overwritten!");
	        	}
	        	try {
	        		//open file for writting the content
		        	FileWriter fw = new FileWriter(newFile);
		        	while((get = is.readLine()) != null) {
		        		fw.write(get);
		        	}
		        	//close file
		        	fw.close();
	        	} catch(Exception e) { System.out.println(e); }
	        } catch(Exception e) { System.out.println(e); }
	        //close output stream reader
            os.close();
            //close input stream reader
            is.close();
            //close connection
            cs.close();  
    	} catch(Exception e) { System.out.println(e); }
    	//finally block (Task is done!)
        finally {System.out.println("Done!");}
    }
}
