package br.edu.utfpr.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServer {

    public static final String SERVER_NAME = "survey_server";
    public static final int SERVER_PORT = 33600;

    public static void main( String[] args ) {
        
        try {
            //InputStreamReader is = new InputStreamReader(System.in);
            //BufferedReader br = new BufferedReader(is);            
    
            startRegistry(SERVER_PORT);
            ServerImpl serverObj = new ServerImpl();
            String url = "rmi://localhost:" + SERVER_PORT + "/callback";
            Naming.rebind(url, serverObj);
            System.out.println("Callback Server ready.");
        } catch (Exception e) {
            System.out.println("Exception in AppServer.main: " + e);
        } 
    } // end main
        
    private static void startRegistry(int serverPort) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry(serverPort);
            registry.list( );        
        } catch (RemoteException e) {             
            Registry registry = LocateRegistry.createRegistry(serverPort);
        }
    }
}

