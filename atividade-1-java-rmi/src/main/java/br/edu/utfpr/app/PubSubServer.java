package br.edu.utfpr.app;
//******************************************************************************
//File:    PubSubServer.java
//Package: pubsub
//Unit:    Distributed Programming Individual Project
//******************************************************************************
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.utfpr.app.interfaces.ServerInterface;
import br.edu.utfpr.app.entity.Enquete;
import br.edu.utfpr.app.interfaces.Publisher;
import br.edu.utfpr.app.interfaces.Subscriber;

/**
 * This agent serves as both the Publisher and Subscriber for the system.  Note you can have as many of these
 * instantiated as you want, serving in either capacity
 * 
 * @author rob mccartney
 *
 */
public class PubSubServer extends UnicastRemoteObject implements Publisher, Subscriber, Serializable {

	//Used by the agent to try to contact the server repeatedly before giving up
	public static final int MAX_TRIES = 100;
	//Used to make a thread sleep rather than repeated trying to contact the server
	public static final int TIMEOUT = 1000;
	
	private static final long serialVersionUID = 1L;
	protected ServerInterface server;
	//Used by the subscriber
	protected ArrayList<Enquete> subscrEnquetes;
	protected ArrayList<String> subscrKeywords;
	protected ArrayList<Enquete> recvdEnquetes;
	//Used by the publisher
	protected ArrayList<Enquete> myPubEnquetes;
	
	//Unique identifier assigned by the server
	protected Integer ID;
	/**
	 * Constructor 
	 * 
	 * @param _server taken from the rmi registry 
	 * @throws RemoteException
	 */
	public PubSubServer(ServerInterface _server) throws RemoteException {
		this.server = _server;
		if (_server != null)
			this.ID = server.sayHello(this);
		subscrEnquetes = new ArrayList<>();
		subscrKeywords = new ArrayList<>();
		recvdEnquetes = new ArrayList<>();		
		myPubEnquetes = new ArrayList<>();
	}
	/**
	 * Overwrite Obj equals for hashing purposes, and since an ID must be a unique identifier
	 */
	public boolean equals(Object obj) {
		return this.ID.equals(((PubSubServer)obj).ID);
	}
	/**
	 * Overwrite Obj hashing to ensure collisions for equal Agent ID's
	 */
	public int hashCode() {
		return this.ID.hashCode();
	}
	/**
	 * This method allows the server to be set after construction of this object
	 * 
	 * @param server to bind with
	 * @throws RemoteException if server is unavailable
	 */
	public void setServer(ServerInterface server) throws RemoteException {
		this.server = server;
		this.ID = server.sayHello(this);
	}
	/**
	 * This agent has come back onto the network and now must re-establish communication with the server
	 *  
	 * @throws RemoteException
	 */
	public void rebindToServer() throws RemoteException {
		server.sayHello(this.ID, this);
	}
	/**
	 * 
	 * @return server currently associated with this agent
	 */
	public ServerInterface getServer() {
		return this.server;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//  Subscriber Methods
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Print out the enquetes this agent is subscribed to
	 */
	public void listSubscribedEnquetes() {
		for (Enquete t : subscrEnquetes)
			System.out.print(t);
	}
	/**
	 * Print out the keywords this agent is subscribed to 
	 */
	public void listSubscribedKeywords() {
		for (String k : subscrKeywords)
			System.out.println(k);
	}
	/**
	 * Print all the received events that have come in during the life of this agent
	 */
	public void listReceivedEnquetes() {
		for (Enquete e : recvdEnquetes)
			System.out.print(e);
	}
	/**
	 * Used to notify the remote client
	 * @param event Enquete that the Subscriber will be receiving from the server
	 */
	public void notify(Enquete enquete) throws RemoteException {
		System.out.println("*Notification of received event*");
		System.out.print(enquete);
		System.out.print("> ");
		recvdEnquetes.add(enquete);
	}
	/**
	 * This method contacts the server to subscribe this agent to the given enquete
	 * @param enquete Enquete to subscribe to
	 */
	public void subscribe(final Enquete enquete) {
		
		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						if (server.addSubscriber(PubSubServer.this.ID, enquete))
							subscrEnquetes.add(enquete);
						return;
					} catch(RemoteException e) {
						if (tries == 1)
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { Thread.sleep(TIMEOUT); } catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server to subscribe to Enquete "+enquete.getNome()+". Please try again later.");
			}
		}).start(); 
	}

	
	/**
	 * This method contacts the server to unsubscribe this agent from the given enquete
	 * @param enquete Enquete to unsubscribe from
	 */
	public void unsubscribe(final Enquete enquete) {
		
		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						if (server.removeSubscriber(PubSubServer.this.ID, enquete))
							subscrEnquetes.remove(enquete);
						return;
					} catch(RemoteException e) {
						if (tries == 1)
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { Thread.sleep(TIMEOUT); } catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server to unsubscribe from "+enquete.getNome()+". Please try again later.");
			}
		}).start(); 
	}
	
	
		
	////////////////////////////////////////////////////////////////////////////////////
	//  Publisher Methods
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Prints the events created by the Publisher side of this agent
	 */
	public void viewMyEnquetes() {
		for (Enquete e : myPubEnquetes) 
			System.out.print(e);
	}

	
	/**
	 * Helper method to make an event from a Scanner (takes user input)
	 * 
	 * @param in Scanner taking input from the console
 	 * @return Enquete created
	 */
	public Enquete makeEnquete(Scanner in) {
		Enquete t = null;
		if ( (t = findEnquete(in)) != null) {
			System.out.println("Enter event title:");
			String title = in.nextLine().trim();
			System.out.println("Enter event-specific keywords on a single line (leave blank to use Enquete keywords instead):");
			String[] keywords = in.nextLine().trim().split("\\s+");
			if (keywords.length == 1 && keywords[0].equals(""))
				keywords = null;
			System.out.println("Enter content for this event. Leave a blank line to finish:");
			String content="", next;
			while ( !((next = in.nextLine()).isEmpty()) ) 
				content += next;
			return new Enquete("Primeira", "Enquete", "de", "Teste", "!!!");
		}
		return null;
	}

	/**
	 * The Publisher advertises a new enquete on the server
	 * 
	 * @param newEnquete Enquete to be advertised
	 */
	public void advertise(final Enquete newEnquete) {

		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						int uniqueId = server.addEnquete(newEnquete);
						//if (uniqueId != 0)
							//myPubEnquetes.add(newEnquete.setId(uniqueId));
						//else
						//	System.err.println("Enquete already exists on server");
						return;
					} catch(RemoteException e) {
						if (tries == 1) 
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { Thread.sleep(TIMEOUT); } catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server for Enquete "+newEnquete.getNome()+" creation. Please try again later.");
			}
		}).start(); 
	}
	
	/**
	 * Helper method to create a Enquete from user input
	 * @param in Scanner to take user input from console
	 * @return Enquete created
	 */
	// public Enquete makeEnquete(Scanner in) {
		
	// 	System.out.println("Enter enquete name:");
	// 	String name = in.nextLine().trim();
	// 	System.out.println("Enter enquete keywords on a single line separated by whitespace:");
	// 	String[] keywords = in.nextLine().trim().split("\\s+");
		
	// 	return new Enquete(name, keywords);
	// }
	
	//////////////////////////////////////////////////////////////
	// Main-thread execution of user interface
	//////////////////////////////////////////////////////////////
			
	/**
	 * This is how the user interacts with the system from the command line.  Gives options for:
	 * 1. Acting as a publisher
	 * 2. Acting as a subscriber
	 * 3. Saving the agent to memory using serialization and quitting
	 * 4. Quitting without saving
	 * 
	 * @throws RemoteException
	 */
	public void commandLineInterface() throws RemoteException {
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("What would you like to do? Enter choice [1-4]:");
			System.out.println(" 1: Act as publisher");
			System.out.println(" 2: Act as subscriber");
			System.out.println(" 3: Save agent & quit");
			System.out.println(" 4: Quit without saving");
			System.out.print("> ");
			int choice = -1;
			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { in.nextLine(); }
			switch (choice) {
				case 1: publisherChoices(in); break;
				case 2: subscriberChoices(in); break;
				case 3: in.close(); saveAgent(); break;
				case 4: in.close(); fullExit(); break;
				default: System.out.println("Input not recognized");
			}
		} while (true);
	}
	/**
	 * The submenu of Publisher-options for this agent
	 * 
	 * @param in Scanner to read from console 
	 */
	public void publisherChoices(Scanner in) {
		boolean continueExec = true;
		do {
			System.out.println("Publisher actions [1-5]:");
			System.out.println(" 1: Advertise a enquete");
			System.out.println(" 2: Publish an event");
			System.out.println(" 3: View your advertised enquetes");
			System.out.println(" 4: View your created events");
			System.out.println(" 5: Go back");
			System.out.print("> ");
			int choice = -1;
			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { in.nextLine(); }
			switch (choice) {
				//case 1: advertise(makeEnquete(in)); break;
				//case 2: publish(makeEnquete(in)); break;
				case 3: viewMyEnquetes(); break;
				case 4: viewMyEnquetes(); break;
				case 5: continueExec = false; break;
				default: System.out.println("Input not recognized");
			}
		} while (continueExec);
	}
	/**
	 * The submenu of Subscriber-options for this agent
	 * 
	 * @param in Scanner to read from console 
	 */
	public void subscriberChoices(Scanner in) {
		boolean continueExec = true;
		do {
			System.out.println("Subscriber actions [1-10]:");
			System.out.println(" 1: Subscribe to a enquete");
			System.out.println(" 2: Subscribe to a keyword");
			System.out.println(" 3: Unsubscribe from a enquete");
			System.out.println(" 4: Unsubscribe from a keyword");
			System.out.println(" 5: Unsubscribe from all");
			System.out.println(" 6: Show currently subscribed enquetes");
			System.out.println(" 7: Show currently subscribed keywords");
			System.out.println(" 8: View all available enquetes on server");
			System.out.println(" 9: View all received events");
			System.out.println(" 10: Go back");
			System.out.print("> ");
			int choice = -1;
			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { in.nextLine(); }
			Enquete t = null;
			switch (choice) {
				case 1: 
					if ( (t = findEnquete(in)) != null)
						subscribe(t);
					break;
				case 2: 
					System.out.println("Enter keyword to subscribe to:"); 
					//subscribe( in.nextLine().trim() ); 
					break;
				case 3:
					if ( (t = findEnquete(in)) != null)
						unsubscribe(t);
					break;
				case 4: 
					System.out.println("Enter keyword to unsubscribe from:"); 
					//unsubscribe( in.nextLine().trim() ); 
					break;
				//case 5: unsubscribe(); break;
				case 6: listSubscribedEnquetes(); break;
				case 7: listSubscribedKeywords(); break;
				case 8: 
					try {
						for( Enquete aT: server.getEnquetes() ) 
							System.out.print(aT);
					} catch( RemoteException e) { System.out.println("Cannot contact server. Try again later"); }
					break;
				case 9: listReceivedEnquetes(); break;
				case 10: continueExec = false; break;
				default: System.out.println("Input not recognized");
			}
		} while (continueExec);
	}
	
	//////////////////////////////////////////////////////////////
	// Protected methods
	//////////////////////////////////////////////////////////////
	
	/**
	 * Creates a Enquete from the command line for the Publisher to advertise
	 * 
	 * @param in Scanner
	 * @return Enquete created
	 */
	protected Enquete findEnquete(Scanner in) {
		System.out.println("Enter the name or ID number of the Enquete:"); 
		String input = in.nextLine().trim();
		List<Enquete> fromServer;
		try {
			fromServer = server.getEnquetes();
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		try {
			int findID = Integer.parseInt(input);
			for (Enquete t : fromServer ) 
				if (t.getId() == findID)
					return t;
			System.out.println("Enquete not found.");
			return null;
		} catch (NumberFormatException e) {
			for (Enquete t : fromServer ) 
				if (t.getNome().equalsIgnoreCase( input ))
					return t;
			System.out.println("Enquete not found.");
			return null;
		} 
	}
	/**
	 * Finds an already existing Enquete on the server through it's name
	 * 
	 * @param name String of Enquete to find
	 * @return the Enquete, or null
	 */
	protected Enquete findEnquete(String name) {
		List<Enquete> fromServer;
		try {
			fromServer = server.getEnquetes();
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		
		for (Enquete t : fromServer ) 
			if (t.getNome().equalsIgnoreCase( name ))
				return t;
		
		System.out.println("Enquete not found.");
		return null; 
	}
	/**
	 * Finds an already existing Enquete on the server through it's ID
	 * 
	 * @param ID Integer ID of Enquete to find
	 * @return the Enquete, or null
	 */
	protected Enquete findEnquete(Integer ID) {
		List<Enquete> fromServer;
		try {
			fromServer = server.getEnquetes();
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		
		for (Enquete t : fromServer ) 
			if (t.getId() == ID)
				return t;
		
		System.out.println("Enquete not found.");
		return null; 
	}
	/**
	 * This saves the current object to disk through serialization, letting the server know he is 
	 * currently unavailable.  When he comes back, he will have to sayHello again since he unbinded himself
	 * 
	 * @throws RemoteException
	 */
	protected void saveAgent() throws RemoteException {
		server.unbind(this.ID);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("agent.dat"));
		    oos.writeObject(this);
		    oos.close();
		} catch (Exception e) {
			System.out.println("Object not saved correctly.");
		} finally {
			System.exit(0);
		}
	}
	/**
	 * This agent has no intention of returning.  He is removed from the data structures altogether
	 * @throws RemoteException
	 */
	protected void fullExit() throws RemoteException {
		server.unbindPermanent(this.ID);
		this.unsubscribe();
		// Don't remove your created Enquetes, other Publishers can still publish to it
		System.exit(0);
	}
	@Override
	public void subscribe(String keyword) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsubscribe() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void publish(Enquete enquete) {
		// TODO Auto-generated method stub
		
	}
	
}