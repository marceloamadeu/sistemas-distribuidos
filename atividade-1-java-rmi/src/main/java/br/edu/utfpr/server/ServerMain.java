package br.edu.utfpr.server;

import br.edu.utfpr.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerMain {

    private static final int PORT = 33600;
    private static final String HOSTNAME = "localhost";
    private ServerImpl serverImpl = null;
    private Util util = new Util();

    /**
     *
     */
    public ServerMain() {
        try {
            // Bind the remote object's stub in the registry
            LocateRegistry.createRegistry(PORT);
            serverImpl = new ServerImpl();
            Naming.rebind("//" + HOSTNAME + ":" + PORT + "/Enquete", serverImpl);
        } catch (Exception e) {
            System.out.println(util.TEXT_RED + "[-] " + util.TEXT_RESET + "RemoteException in MainServerApp.main: " + e.toString());
            System.exit(1);
        }
    }

    public static void main(String[] args) throws RemoteException {

        ServerMain server = new ServerMain();
        server.serverImpl.commandLineInterface(HOSTNAME, PORT);
/*
        try {                 
            startRegistry(33600);
            ServerImpl exportedObj = new ServerImpl();
            registryURL = "rmi://localhost:" + 33600 + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server ready.");
        } catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        } // end catch
  */
    } // end main

    //This method starts a RMI registry on the local host, if
    //it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );  
            // This call will throw an exception
            // if the registry does not already exist
        } catch (RemoteException e) { 
            // No valid registry at that port.
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
        }
    }
}
