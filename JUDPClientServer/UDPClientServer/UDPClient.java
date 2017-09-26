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
import java.util.Scanner;


class UDPClient {
    static public void main(String args[]) throws Exception {
    	
    	//create socket
    	//client doesn't know which socket to connect with. create socket 
    	DatagramSocket ds = new DatagramSocket();
		
		//get ip address
    	InetAddress ia = InetAddress.getLocalHost();
    	//port number
    	int port = 8001;

    	//Number of paragraphs wants to retrive.
    	System.out.println(" Client\n Enter the number of paragraphs wants to retrieve (max-5)");
        //how many paragraphs to see.
    	Scanner sc = new Scanner(System.in);
    	int n = sc.nextInt();
    	//send request message
    	String requestMessage = new String(n + " Paragraphs");
    	//string to byte conversion for send buffer
    	byte[] buff = requestMessage.getBytes();
        //datagram packet to send
    	DatagramPacket dp = new DatagramPacket(buff, buff.length,ia,port);
        //send
    	ds.send(dp);

        //response from server
        System.out.println("Paragraph(s)");
        //receve the paragraph
        for(int i=0;i<n;i++){
            // empty buffer to get the paragraph
            byte[] receivedMessageLoop = new byte[1024];
            //datapacket receive.
            DatagramPacket dpLoop = new DatagramPacket(receivedMessageLoop, receivedMessageLoop.length);
            ds.receive(dpLoop);
            //store message to string 
            String strLoop = new String(dpLoop.getData(),0,dpLoop.getLength());
            //display the outcome
            System.out.println(strLoop);    
        }
    }
}
