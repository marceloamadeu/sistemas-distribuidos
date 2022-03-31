package br.edu.utfpr.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * A Classe AppaServer tem um método main que cria uma instância de ServerImpl
 * (objeto Remoto), e em seguida, vincula essa instância a um Registry.
 * 
 * O Java RMI Registry é um serviço de nomes simples que permite que os clientes
 * obtenham uma referência (stub / skeleton). Em geral, um registro é usado (se 
 * for o caso) apenas para localizar o primeiro objeto remoto que um cliente 
 * precisa usar.
 */
public class AppServer {

    public static final String SERVER_NAME = "localhost";
    public static final int SERVER_PORT = 33600;

     public AppServer() {

     }

    public static void main(String[] args) {
        
        try { 
            doRegistry(SERVER_PORT);            
            ServerImpl server = new ServerImpl();

            String url = "rmi://" + SERVER_NAME + ":" + SERVER_PORT + "/callback";
            Naming.rebind(url, server);
            System.out.println("Callback Server ready.");
        } catch (Exception e) {
            System.out.println("Exception in AppServer.main: " + e.toString());
        } 
    } // end main
        
    private static void doRegistry(int serverPort) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.list();        
        } catch (RemoteException e) {             
            Registry registry = LocateRegistry.createRegistry(serverPort);
        }
    }
}

