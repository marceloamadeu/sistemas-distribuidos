package br.edu.utfpr.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Hello world!
 *
 */
public class App implements ClientInterface {

    public static final String SERVER_NAME = "survey_server";
    public static final int SERVER_PORT = 33600;

    public static void main( String[] args ) throws RemoteException {

        App client = new App();
        UnicastRemoteObject.exportObject(client, SERVER_PORT);
        
        System.out.println("Cliente Ok...");    
      
    }

    @Override
    public void update(int survey) throws RemoteException {        
        System.out.println("Update");
    }

    @Override
    public void notifyChanges() throws RemoteException {
        System.out.println("notify");        
    }
}
