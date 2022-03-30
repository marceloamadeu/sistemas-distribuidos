import java.net.*;
import java.io.*;
public class UDPServer{

	
	
	/**
	 * SERVIDOR
	 * - Cria um socket associado a porta de servidor 6789
	 * - Repetidamente espera por requisições de clientes
	 * - Responde enviando de volta a mesma mensagem
	 */	
    public static void main(String args[]){ 

		DatagramSocket datagramSocket = null;
		byte[] buffer = new byte[1000];
		boolean running = true;

		DatagramPacket request;
		DatagramPacket reply;

		InetAddress address;
        int port;


		try {
			/** 
			 Suporta socket para enviar e receber datagramas
			 Construtores com argumentos e sem argumentos.
			 É possível escolher a porta ou permitir que o sistema
			 escolha qualquer porta disponivel
			 Sempre maior que 1024
			 */
	    	datagramSocket = new DatagramSocket(33600);								
			 
			while(running) {
				
				// RECEBIMENTO
				// Um argumento é um arreay onde a mensagem será armazenada e o
				// seu tamanho
 				request = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(request);  
				
				address = request.getAddress();
	            port = request.getPort();

				  
				// ENVIO
				// Fornece dois construtores, um para envio e outro para recebimento 
				// de datagrams
				// No Envio ele cria uma instancia de um array de bytes contendo a mensagem,
				// comprimento da mensagem, endereço IP e numero da porta de destino socket
    			reply = new DatagramPacket(request.getData(), request.getLength(), 
					request.getAddress(), request.getPort());

				String received 
					= new String(request.getData(), 0, request.getLength());
					
				if (received.equals("close")) {
					running = false;
					datagramSocket.send(reply);
					continue;
				}				

				datagramSocket.send(reply);
				
			}
			datagramSocket.close();
		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if(datagramSocket != null) datagramSocket.close();
		}
    }
}