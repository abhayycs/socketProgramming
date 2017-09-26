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
import java.util.Arrays;

class UDPServerReliable {

    public static void main(String args[]) throws Exception{
        System.out.println("Server\nWaiting for Client request");
        //opens a socket
        int port = 8001;
        DatagramSocket ds = new DatagramSocket(port);
        //owner of paragraph
		String message = new String(" Winter is Coming\n The biggest gaming expo ever\n Solar Power plantation in India\n Games you want to play\n MOTO E4 PLUS");
		
        //receive buffer
        byte[] clientRequest = new byte[1024];
        DatagramPacket dp = new DatagramPacket(clientRequest, clientRequest.length);
        ds.receive(dp);
        //message copied to string
        String receivedMessage = new String(dp.getData(),0,dp.getLength());
        //number of paragraphs to send
        int n = Integer.parseInt(String.valueOf(receivedMessage.charAt(0)));

        //if(receivedMessage.equals("3 Paragraphs")){
    	String paragraph[] = message.split("\\r?\\n");
		InetAddress ia = InetAddress.getLocalHost();

        //send the paragraphs in order
		for(int i=0;i<n;i++){
			//System.out.println(paragraph[i]);
			//send buffer appended with paragraph number
			byte[] buff = (i+paragraph[i]).getBytes();
			//datagram packet to send
			DatagramPacket dp1 = new DatagramPacket(buff, buff.length,ia,dp.getPort());
			ds.send(dp1);
		}
        //}
        //int i=0;
        //System.out.println("Hello");
        while(true){
            //System.out.println("Hello");
            //taking status from client.
            byte[] clientConfirmation = new byte[1024];
            DatagramPacket dp2 = new DatagramPacket(clientConfirmation, clientConfirmation.length);
            ds.receive(dp2);
            //copying the status to another string.            
            //System.out.println("Hello");

            String status = new String(dp2.getData(),0,dp2.getLength());
            //cheking all paragraphs received or not.
            if(status.equals("done")){
                System.out.println(receivedMessage + " sent" );
                break;
            }//if not received. re-transmit the paragraph.
            else {
                byte[] buff = paragraph[Integer.parseInt(String.valueOf(status.charAt(0)))].getBytes();
                //datagram packet to send
                DatagramPacket dp3 = new DatagramPacket(buff, buff.length,ia,dp.getPort());
                ds.send(dp3);  
            } 
            //System.out.println(i++);
        }
    }
}