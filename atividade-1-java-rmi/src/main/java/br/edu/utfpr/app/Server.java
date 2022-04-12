package br.edu.utfpr.app;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 * This class parses command-line input in order to set up the server in the client-server architecture.
 * Can be used to changed the hostname and port from default. Uses the RMI registry to register the Server object 
 * for clients to grab
 *  
 * @author rob mccartney
 *
 */
public class Server {

	private int port = 33600;
	private String hostName = "localhost";
	private ServerImpl manager = null;
	
	/**
	 * Constructor that makes a new ServerImpl and loads pre-built Topics
	 * @param args command-line arguments for hostname and port
	 */
	public Server(String[] args) {		
    	try {    		
			// Bind the remote object's stub in the registry
            LocateRegistry.createRegistry(port);  
    		manager = new ServerImpl(true);
    		Naming.rebind("//" + hostName + ":" + port + "/Enquete", manager);
            System.out.println("ServerImpl bound in registry at " + hostName + ":" + port);
            manager.startService();
		} catch (Exception e) {
			System.out.println( "ServerImpl error");
			System.out.println( "Did you run 'rmiregistry [port] &' first then 'java EventServer [-p <port>]'?" );
			System.exit(1);
		}
	}
	
	/**
	 * @param args port number and hostname to use
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		Server server = new Server(args);
		server.manager.commandLineInterface();
	}
}
