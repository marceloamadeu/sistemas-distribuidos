import java.net.*;
import java.io.*;

/** 
 * Programa Cliente
 * - Cria um socket
 * - Envia uma mensagem a um servidor na porta 6789
 * - Espera resposta
 * - Os argumentos de entrada da main são o hostname do servidor e a mensagem.
 *	 - Mensagem convertida em um array de bytes
 *	 - Hostname convertido em endereço IP
*/
public class UDPClient{
    public static void main(String args[]){ 
		// args give message contents and destination hostname
		DatagramSocket clientDatagramSocket = null;
		String serverName = "localhost";
		int serverPort = 33600;	

		try {
			serverName = args[1];

			/** 
			 * Suporta socket para enviar e receber datagramas
			 * Construtores com argumentos e sem argumentos.
			 * É possível escolher a porta ou permitir que o sistema
			 * escolha qualquer porta disponivel
			 * Sempre maior que 1024
			 */
			clientDatagramSocket = new DatagramSocket();			
			byte[] receiveData = args[0].getBytes();	
			
			
			/**
			 * The InetAddress class represents an IP address, both IPv4 
			 * and IPv6. Basically you create instances of this class to 
			 * use with other classes such as Socket, ServerSocket, DatagramPacket 
			 * and DatagramSocket. In the simplest case, you can use this class 
			 * to know the IP address from a hostname, and vice-versa.
			 */			
			InetAddress inetAddress = InetAddress.getByName(serverName);									
			
			/**
			 * RECEBIMENTO
			 * Um argumento é um arreay onde a mensagem será armazenada e o
			 * seu tamanho
			*/
			DatagramPacket request = new DatagramPacket(receiveData,  args[0].length(), inetAddress, serverPort);
			clientDatagramSocket.send(request);	
			System.out.println("Pacote UDP para " + serverName + ":" + serverPort);		                        
			byte[] buffer = new byte[1000];
			
			/** 
			 * ENVIO
			 * Fornece dois construtores, um para envio e outro para recebimento 
			 * de datagrams
			 * No Envio ele cria uma instancia de um array de bytes contendo a mensagem,
			 * comprimento da mensagem, endereço IP e numero da porta de destino socket
			 */
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
			clientDatagramSocket.receive(reply);
			System.out.println("Resposta: " + new String(reply.getData()));	

		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		} finally {
			if(clientDatagramSocket != null) clientDatagramSocket.close();
		}
	}		      	
}