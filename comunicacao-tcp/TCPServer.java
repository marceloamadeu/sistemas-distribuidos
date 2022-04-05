import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main (String args[]) {
		try {
			int serverPort = 7896; // the server port
			Socket clientSocket = null;
			Connection connection = null;

			/**
			 * Servidor: cria um listening socket, o associa a uma porta 
			 * local conhecida e espera pedidos de conexão de clientes.
			 */
			ServerSocket listenSocket = new ServerSocket(serverPort);

			while(true) {
				clientSocket = listenSocket.accept();
				connection = new Connection(clientSocket);
			}
			
		} catch(IOException e) {
			System.out.println("Listen socket: " + e.getMessage());
		}
	}
}

class Connection extends Thread {

	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;
	Socket clientSocket;
	
	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			dataInputStream = new DataInputStream( clientSocket.getInputStream());
			dataOutputStream = new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {
			System.out.println("Connection: " + e.getMessage());
		}
	}

	public void run(){
		try {			                 // an echo server

			String data = dataInputStream.readUTF();	                  // read a line of data from the stream
			dataOutputStream.writeUTF(data);
		} catch (EOFException e){
			System.out.println("EOF: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("readline: " + e.getMessage());
		} finally{ 
			try {
				clientSocket.close();
			} catch (IOException e) {
				/*close failed*/
			}
		}
		

	}
}

202211771507
202211772429

