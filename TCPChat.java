/**
*	TCP Server Program
*	Listens on a TCP port and waits for two TCP clients
*	Receives a line of input from one TCP client and sends it to the other
*
*	@author: Cole Wright
@	version: 2.0
*/
//
import java.io.*;
import java.net.*;

class TCPChat {

  public static void main(String argv[]) throws Exception
    {
		String sentence;

		ServerSocket welcomeSocket = null;
	  
		try
		{
			welcomeSocket = new ServerSocket(6789);
		}
		
		catch(Exception e)
		{
			System.out.println("Failed to open socket connection");
			System.exit(0);
		}
	  
	  	while (true)
	  	{
		  	// wait for green to connect and assign input and output streams
		  	Socket greenSocket = welcomeSocket.accept();
		  	BufferedReader inFromGreen = new BufferedReader(new InputStreamReader(greenSocket.getInputStream()));
			DataOutputStream  outToGreen = new DataOutputStream(greenSocket.getOutputStream());
			outToGreen.writeBytes("100\n");
			System.out.println("First user connected");

			// wait for yellow to connect and assign input and output streams
		  	Socket yellowSocket = welcomeSocket.accept();
		  	BufferedReader inFromYellow = new BufferedReader(new InputStreamReader(yellowSocket.getInputStream()));
			DataOutputStream  outToYellow = new DataOutputStream(yellowSocket.getOutputStream());
			outToYellow.writeBytes("200\n");
			System.out.println("Second user connected");

			// chat until one client says Goodbye
		  	while(true) 
		  	{
			   sentence = inFromGreen.readLine();
			   System.out.println(sentence);
			   if (sentence.equals("Goodbye")){break;}
			   outToYellow.writeBytes(sentence + '\n');

			   sentence = inFromYellow.readLine();
			   System.out.println(sentence);
			   if (sentence.equals("Goodbye")){break;}
			   outToGreen.writeBytes(sentence + '\n');
	        }

	        outToYellow.writeBytes("300\n");
	        outToGreen.writeBytes("300\n");
			System.out.println("Users disconnected");
	    }
    }
}