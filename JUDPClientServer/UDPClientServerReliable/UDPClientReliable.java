/*
A ServerSocket is for accepting incoming network connections on some stream protocol; e.g. TCP/IP.
A DatagramSocket is for sending and receiving datagrams on some connectionless datagram / message protocol; e.g. UDP/IP
references@
https://docs.oracle.com/javase/7/docs/api/java/net/DatagramSocket.html
https://docs.oracle.com/javase/7/docs/api/java/net/DatagramPacket.html
https://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html

*/
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.*;


class UDPClientReliable {
    static public void main(String args[]) throws Exception {
    	
    	//create socket
    	//client doesn't know which socket to connect with. create socket
    	DatagramSocket ds = new DatagramSocket();
		
		//get ip address
    	InetAddress ia = InetAddress.getLocalHost();
    	//port number
    	int port = 8001;

    	//Number of paragraphs wants to retrive.
    	System.out.println(" Client\n Enter the number of paragraphs to retrive");
    	Scanner sc = new Scanner(System.in);
    	int n = sc.nextInt();
    	//message 
    	String requestMessage = new String(n + " Paragraphs");
    	//string to byte conversion send buffer
    	byte[] buff = requestMessage.getBytes();
    	
        //datagram packet to send
    	DatagramPacket dp = new DatagramPacket(buff, buff.length,ia,port);
    	ds.send(dp);

        //to keep the record of received paragraphs
        int[] array = new int[n];
        //mark all array elements to zero.
        Arrays.fill(array, 0);
        String[] paragraph = new String[n];

        int flag=0;
        int timeout = 10000;		//Default 30 seconds timeout.
        int i=0;

        long startTime = System.currentTimeMillis(); //fetch starting time
        //System.out.println(startTime);
        ds.setSoTimeout(timeout);
        while(i<n){
        	i++;
            //recieve buffer.
        	byte[] receivedMessage = new byte[1024];
            //packet receive
            DatagramPacket dp1 = new DatagramPacket(receivedMessage, receivedMessage.length);
            ds.receive(dp1);
            //message copyied to another string.
            String str1 = new String(dp1.getData(),0,dp1.getLength());
            //mark the array element to one i.e. the paragraph is received.
            array[Integer.parseInt(String.valueOf(str1.charAt(0)))] = 1;
            //copy the paragraph
            paragraph[Integer.parseInt(String.valueOf(str1.charAt(0)))] = str1;
            //System.out.println(str1);
            
            //measure the time taken to complete the transfer of one paragraph.
            if(flag == 0){
                long startTime2 = System.currentTimeMillis(); //fetch starting time
                //maximum time the loop should run for.
                long uptoTime = n*(startTime2 - startTime);
                flag = 1;
                timeout = (int) uptoTime;
            }
        	//System.out.println(timeout);
        }
        //System.out.println(Arrays.toString(array));
        //checks all messages are received or not. if not then request again.
        i=1;
        timeout = 2*timeout;
        while(true){
        	//timeout incase packet is still unreached.
        	ds.setSoTimeout(timeout);
        	i--;
	        for(;i<n;i++){
	            if(array[i] == 0){
	                //re-request section. asking for paragraph by sending the paragraph index.
	                //index of missing paragraph.
	                String againRequestMessage = new String();
	                againRequestMessage = Integer.toString(i);
	                //string to byte conversion. send buffer
	                byte[] buff2 = againRequestMessage.getBytes();
	                //datagram packet to send
	                DatagramPacket dp2 = new DatagramPacket(buff2, buff2.length,ia,port);
	                ds.send(dp2);

	                //receive section
	                byte[] receivedMessage2 = new byte[1024];
	                DatagramPacket dp3 = new DatagramPacket(receivedMessage2, receivedMessage2.length);
	                ds.receive(dp3);
	                String str2 = new String(dp3.getData(),0,dp3.getLength());
	                //mark the array element to one i.e. the paragraph is received.
	                array[Integer.parseInt(String.valueOf(str2.charAt(0)))] = 1;
	                //copy the paragraph
	                paragraph[Integer.parseInt(String.valueOf(str2.charAt(0)))] = str2;
	                
	            }
	            //System.out.println(str2);
	        }
	        if(i == n){
	        	break;
	        }
	    }
        String completionMessage = new String("done");
        //string to byte conversion
        byte[] buff3 = completionMessage.getBytes();
        //datagram packet to send
        DatagramPacket dp4 = new DatagramPacket(buff3, buff3.length,ia,port);
        ds.send(dp4);
        //display paragraphs.
        for(i=0;i<paragraph.length;i++){
            String res = paragraph[i];
            System.out.println(res.substring(1));
        }
    }
}
