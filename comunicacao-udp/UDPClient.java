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
		DatagramSocket aSocket = null;
		try {
			/** 
			 Suporta socket para enviar e receber datagramas
			 Construtores com argumentos e sem argumentos.
			 É possível escolher a porta ou permitir que o sistema
			 escolha qualquer porta disponivel
			 Sempre maior que 1024
			 */
			aSocket = new DatagramSocket();    
			byte [] m = args[0].getBytes();
			
			
			InetAddress aHost = InetAddress.getByName(args[1]);
			//InetAddress aHost = InetAddress.getByName("localhost");
			
			int serverPort = 33600;	
			
			/**
			 RECEBIMENTO
			 Um argumento é um arreay onde a mensagem será armazenada e o
			 seu tamanho
			*/
			DatagramPacket request = new DatagramPacket(m,  args[0].length(), aHost, serverPort);
			aSocket.send(request);			                        
			byte[] buffer = new byte[1000];
			
			/** 
			 ENVIO
			 Fornece dois construtores, um para envio e outro para recebimento 
			 de datagrams
			 No Envio ele cria uma instancia de um array de bytes contendo a mensagem,
			 comprimento da mensagem, endereço IP e numero da porta de destino socket
			 */
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
			aSocket.receive(reply);
			System.out.println("Reply: " + new String(reply.getData()));	
		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		} finally {
			if(aSocket != null) aSocket.close();
		}
	}		      	
}