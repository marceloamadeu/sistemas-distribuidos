package br.edu.utfpr.app;

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
import br.edu.utfpr.app.interfaces.PublisherInterface;
import br.edu.utfpr.app.interfaces.SubscriberInterface;

/**
 * This agent serves as both the Publisher and Subscriber for the system.  Note you can have as many of these
 * instantiated as you want, serving in either capacity
 * 
 * @author rob mccartney
 *
 */
public class PublisherSubscriberServer extends UnicastRemoteObject implements PublisherInterface, SubscriberInterface, Serializable {

	//Used by the agent to try to contact the server repeatedly before giving up
	public static final int MAX_TRIES = 100;
	//Used to make a thread sleep rather than repeated trying to contact the server
	public static final int TIMEOUT = 1000;
	
	private static final long serialVersionUId = 1L;
	protected ServerInterface server;
	//Used by the subscriber
	protected ArrayList<Enquete> subscrEnquetes;
	protected ArrayList<String> subscrKeywords;
	protected ArrayList<Enquete> recvdEnquetes;
	//Used by the publisher
	protected ArrayList<Enquete> myPubEnquetes;
	
	//Unique identifier assigned by the server
	protected Integer Id;
	
	public Util util;

	/**
	 * Constructor 
	 * 
	 * @param _server taken from the rmi registry 
	 * @throws RemoteException
	 */
	public PublisherSubscriberServer(ServerInterface _server) throws RemoteException {
		this.server = _server;
		if (_server != null)
			this.Id = server.sayHello(this);
		subscrEnquetes = new ArrayList<>();
		subscrKeywords = new ArrayList<>();
		recvdEnquetes = new ArrayList<>();		
		myPubEnquetes = new ArrayList<>();

		util = new Util();
	}
	/**
	 * Overwrite Obj equals for hashing purposes, and since an Id must be a unique identifier
	 */
	public boolean equals(Object obj) {
		return this.Id.equals(((PublisherSubscriberServer)obj).Id);
	}
	/**
	 * Overwrite Obj hashing to ensure collisions for equal Agent Id's
	 */
	public int hashCode() {
		return this.Id.hashCode();
	}
	/**
	 * This method allows the server to be set after construction of this object
	 * 
	 * @param server to bind with
	 * @throws RemoteException if server is unavailable
	 */
	public void setServer(ServerInterface server) throws RemoteException {
		this.server = server;
		this.Id = server.sayHello(this);
	}
	/**
	 * This agent has come back onto the network and now must re-establish communication with the server
	 *  
	 * @throws RemoteException
	 */
	public void rebindToServer() throws RemoteException {
		server.sayHello(this.Id, this);
	}
	/**
	 * Subscriber
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
						if (server.addSubscriber(PublisherSubscriberServer.this.Id, enquete))
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
						if (server.removeSubscriber(PublisherSubscriberServer.this.Id, enquete))
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
		Enquete enquete = null;
		
		//if ( (enquete = findEnquete(in)) != null) {
			System.out.println("Nome:");
			String nome = in.nextLine().trim();
			System.out.println("Titulo:");
			String titulo = in.nextLine().trim();
			System.out.println("Local:");
			String local = in.nextLine().trim();						
			System.out.println("Tempo:");
			String tempo = in.nextLine().trim();						
			System.out.println("Data fim da Enquete:");
			String dataFim = in.nextLine().trim();												
			return new Enquete(nome, titulo, local, tempo, dataFim);
		//}
		//return null;
	}

	/**
	 * The Publisher advertises a new enquete on the server
	 * 
	 * @param newEnquete Enquete to be advertised
	 */
	public void advertise(final Enquete enquete) {

		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						int uniqueId = server.addEnquete(enquete);
						if (uniqueId != 0) {
							enquete.setId(uniqueId);
							myPubEnquetes.add(enquete);
						} else {
							System.err.println("Enquete already exists on server");
						}
						return;
					} catch(RemoteException e) {
						if (tries == 1) 
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { Thread.sleep(TIMEOUT); } catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server for Enquete " + enquete.getNome() + " creation. Please try again later.");
			}
		}).start(); 
	}
		
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
			// Limpar o console e mostrar as opções do menu
			//util.cleanRefreshPrintClientHeader();	
			System.out.println("Menu - Selecione uma das opções:");	
			System.out.println(" ");
			System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Publisher");
			System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Subscriber");						
			System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Sair");
			System.out.println(" ");
			System.out.print("Digite o número [1 - 9]: ");
										
			int choice = -1;
			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { in.nextLine(); }
			switch (choice) {
				case 1: publisherChoices(in); break;
				case 2: subscriberChoices(in); break;				
				case 9: in.close(); fullExit(); break;
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
			// Limpar o console e mostrar as opções do menu
			//util.cleanRefreshPrintClientHeader();	
			System.out.println("Menu - Opções de Publisher:");	
			System.out.println(" ");
			System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Incluir a enquete");
			System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Publish an event");
			System.out.println(util.TEXT_GREEN + " [ 3 ] " + util.TEXT_RESET + "View your advertised enquetes");
			System.out.println(util.TEXT_GREEN + " [ 4 ] " + util.TEXT_RESET + "View your created events");
			System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Voltar");
			System.out.print("> ");
			int choice = -1;

			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { 
				in.nextLine(); 
			}

			switch (choice) {
				case 1: advertise(makeEnquete(in)); break;
				case 2: publish(makeEnquete(in)); break;
				case 3: viewMyEnquetes(); break;
				case 4: viewMyEnquetes(); break;
				case 9: continueExec = false; break;
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
			// Limpar o console e mostrar as opções do menu
			//util.cleanRefreshPrintClientHeader();	
			System.out.println("Menu - Opções de Publisher:");	
			System.out.println(" ");
			System.out.println("Subscriber actions [1-10]:");
			System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Subscribe to a enquete");			
			System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Unsubscribe from a enquete");			
			System.out.println(util.TEXT_GREEN + " [ 3 ] " + util.TEXT_RESET + "Unsubscribe from all");
			System.out.println(util.TEXT_GREEN + " [ 4 ] " + util.TEXT_RESET + "Show currently subscribed enquetes");			
			System.out.println(util.TEXT_GREEN + " [ 5 ] " + util.TEXT_RESET + "View all available enquetes on server");
			System.out.println(util.TEXT_GREEN + " [ 6 ] " + util.TEXT_RESET + "View all received events");
			System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Voltar");
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
					if ( (t = findEnquete(in)) != null)
						unsubscribe(t);
					break;
				//case 3: unsubscribe(); break;
				case 4: listSubscribedEnquetes(); break;				
				case 5: 
					try {
						for( Enquete aT: server.getEnquetes() ) 
							System.out.print(aT);
					} catch( RemoteException e) { System.out.println("Cannot contact server. Try again later"); }
					break;
				case 6: listReceivedEnquetes(); break;
				case 9: continueExec = false; break;
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
		System.out.println("Enter the name or Id number of the Enquete:"); 
		String input = in.nextLine().trim();

		System.out.println("findEnquete 1"); 
		List<Enquete> fromServer;
		try {
			System.out.println("findEnquete 2"); 
			fromServer = server.getEnquetes();
			System.out.println("findEnquete 3"); 
			System.out.println(fromServer);
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		try {
			int findId = Integer.parseInt(input);
			for (Enquete enquete : fromServer ) 
				if (enquete.getId() == findId)
					return enquete;
			System.out.println("findEnquete 4");
			System.out.println("Enquete not found.");
			return null;
		} catch (NumberFormatException e) {
			for (Enquete enquete : fromServer ) 
				if (enquete.getNome().equalsIgnoreCase( input ))
					return enquete;
			System.out.println("findEnquete 5");
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
	protected Enquete findEnquete(String nome) {
		List<Enquete> fromServer;
		try {
			fromServer = server.getEnquetes();
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		
		for (Enquete enquete : fromServer ) 
			if (enquete.getNome().equalsIgnoreCase( nome ))
				return enquete;
		
		System.out.println("Enquete not found.");
		return null; 
	}
	/**
	 * Finds an already existing Enquete on the server through it's Id
	 * 
	 * @param Id Integer Id of Enquete to find
	 * @return the Enquete, or null
	 */
	protected Enquete findEnquete(Integer Id) {
		List<Enquete> fromServer;
		try {
			fromServer = server.getEnquetes();
		} catch (RemoteException f) { 
			System.out.println("Cannot contact server. Try again later"); 
			return null;
		}
		
		for (Enquete enquete : fromServer ) 
			if (enquete.getId() == Id)
				return enquete;
		
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
		server.unbind(this.Id);
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
		server.unbindPermanent(this.Id);
		this.unsubscribe();
		// Don't remove your created Enquetes, other Publishers can still publish to it
		System.exit(0);
	}
				
	@Override
	public void unsubscribe() throws RemoteException {
		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						if (server.removeSubscriber(PublisherSubscriberServer.this.Id)) {							
							subscrEnquetes.clear();
						}
						return;
					} catch(RemoteException e) {
						if (tries == 1)
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { Thread.sleep(TIMEOUT); } catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server for full unsubscribe request. Please try again later.");
			}
		}).start(); 	
	}

	@Override
	public void publish(final Enquete enquete) {
		if (enquete == null)
			return;
		
		new Thread(new Runnable() {
			public void run() {
				int tries = 0;
				while(++tries < MAX_TRIES) {
					try {
						int uniqueId = server.publish(enquete);
						System.err.println("Nome: " + enquete.getNome());
						System.err.println("Titulo: " + enquete.getTitulo());
						System.err.println("Id:" + uniqueId);

						if (uniqueId != 0) {
							enquete.setId(uniqueId);
							myPubEnquetes.add(enquete);
						}

						System.err.println("Enquetes:" + myPubEnquetes);
						return;
					} catch(RemoteException e) {
						if (tries == 1) 
							System.err.println("Server currently unavailable. Will continue to process request in background.");
						try { 
							Thread.sleep(TIMEOUT); 
						} catch(Exception f) {}
					}
				}
				System.err.println("Could not contact server for Event " + enquete.getTitulo() + " creation. Please try again later.");
			}
		}).start();
	}
	
}