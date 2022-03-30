import java.net.*;
import java.io.*;
public class TCPClient {
	public static void main (String args[]) {
		// arguments supply message and hostname
		Socket socket = null;
		try{
			int serverPort = 7896;

			/**
			 * Cliente: cria um socket para o fluxo de dados e o associa a 
			 * uma porta qualquer, então envia um connect request ao 
			 * servidor indicando a porta.
			 */
			socket = new Socket(args[1], serverPort);    
			
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutStream =new DataOutputStream(socket.getOutputStream());
			dataOutStream.writeUTF(args[0]);      	// UTF is a string encoding see Sn. 4.4
			String data = dataInputStream.readUTF();	    // read a line of data from the stream
			System.out.println("Received: "+ data) ; 

		} catch (UnknownHostException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline: " + e.getMessage());
		} finally {
			if(socket != null) try {
				socket.close();
			} catch (IOException e) {
				System.out.println("close: " + e.getMessage());
			}
		}
     }
}