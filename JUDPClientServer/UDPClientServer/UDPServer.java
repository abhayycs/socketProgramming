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

class UDPServer {

    public static void main(String args[]) throws Exception{
        //opens a socket
        int port = 8001;
        DatagramSocket ds = new DatagramSocket(port);
        //owner of paragraph
		String message = new String(" Winter is Coming\n The biggest gaming expo ever\n Solar Power plantation in India\n Games you want to play\n The END");
		
        System.out.println("Server\nWaiting for client Request...");
        // the data buffer. byte array for the data transmission.
        byte[] clientRequest = new byte[1024];
        //constructed DatagramPacket for receiving packets
        DatagramPacket dp = new DatagramPacket(clientRequest, clientRequest.length);
        //message received.
        ds.receive(dp);
        //converted message into string
        String receivedMessage = new String(dp.getData(),0,dp.getLength());
        //number of paragraph asked.
        int n = Integer.parseInt(String.valueOf(receivedMessage.charAt(0)));
        System.out.println("Request received");

        //splitting message into paragraphs
    	String paragraph[] = message.split("\\r?\\n");
        //address of the local host
		InetAddress ia = InetAddress.getLocalHost();

        //send 'n' paragraphs asked
		for(int i=0;i<n;i++){
			//ith paragraph string to byte conversion for the buffer
			byte[] buff = (paragraph[i]).getBytes();
			//datagram packet to send 
			DatagramPacket ith_dp = new DatagramPacket(buff, buff.length,ia,dp.getPort());
            //send the data packet
			ds.send(ith_dp);
		}
        //display the outcome
        System.out.println(receivedMessage + " sent" );
        
    }
}