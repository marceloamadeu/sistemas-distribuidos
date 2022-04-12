package br.edu.utfpr.app;

//******************************************************************************
//File:    PubSubClient.java
//Package: None
//Unit:    Distributed Programming Individual Project
//******************************************************************************

import java.rmi.Naming;
import java.rmi.RemoteException;

import br.edu.utfpr.app.interfaces.ServerInterface;

/**
 * This class parses command-line input in order to set up the client in the client-server architecture.
 * Can be used to changed the hostname and port from default, as well as load a previously saved agent
 * Uses the RMI registry to get the Server object 
 * 
 * @author rob mccartney
 *
 */
public class PublisherSubscriberClient {

	private String hostName = "localhost"; 
    private int port = 33600;
    private PublisherSubscriberServer agent = null;
    
    /**
     * Constructor that makes a new agent or loads a previously saved one
     * 
     * @param args String[] from the command line
     */
    public PublisherSubscriberClient() {    	
    	try {    		
    		if (agent == null) {
    			ServerInterface server = (ServerInterface) Naming.lookup("//" + hostName + ":" + port + "/Enquete");
    			System.out.println("Connected to server at " + hostName + ":" + port );
    			agent = new PublisherSubscriberServer(server);
    		}
		} catch (Exception e) {
			System.out.println("Cannot connect to the Event Manager server at this time.  Please try again later.");
			System.out.println("Did you specify the correct hostname and port of the server?");
			System.out.println("Please try again later.");
			System.exit(1);
		}
    }
    
    /**
     * Constructor that is passed an agent that was created outside the class.  Uses setServer method to 
     * set up the relationship
     * 
     * @param args command-line arguments for port and hostname
     * @param agent that will be communicating with the Event server
     */
    public PublisherSubscriberClient(PublisherSubscriberServer agent) {
    	
    	try {
			ServerInterface server = (ServerInterface) Naming.lookup("//" + hostName + ":" + port + "/EventManager");
    		System.out.println("Connected to server at " + hostName + ":" + port );
    		agent.setServer(server);
		} catch (Exception e) {
			System.out.println("Cannot connect to the Event Manager server at this time.  Please try again later.");
			System.out.println("Did you specify the correct hostname and port of the server?");
			System.out.println("Please try again later.");
			System.exit(1);
		}
    }		
	
	/**
	 * @param args for hostname or port to not be default
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		PublisherSubscriberClient client = new PublisherSubscriberClient();
		client.agent.commandLineInterface();
	}
}
