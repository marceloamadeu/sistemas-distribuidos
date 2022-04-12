package br.edu.utfpr.app;
//******************************************************************************
//File:    EnqueteManager.java
//Package: pubsub
//Unit:    Distributed Programming Individual Project
//******************************************************************************
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;

import br.edu.utfpr.app.entity.Enquete;
import br.edu.utfpr.app.interfaces.ServerInterface;
import br.edu.utfpr.app.interfaces.SubscriberInterface;
/**
 * This class is the server that all publishers and subscribers work through for asynchronous message passing.
 * 
 * @author rob mccartney
 *
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	
	private static final long serialVersionUID = 1L;
	//Amount of time to wait between attempts to contact a non-responsive agent
	public static final int TIMEOUT = 1000;
	//counters used to assign Unique IDs
	protected Integer enqueteId = 0;
	protected Integer subscriberId = 0;
	//protected Integer enqueteId = 0;
	//Storage for all Topic Containers (topic plus subscribers)
	protected LinkedHashSet<EnqueteContainer> allEnqueteContainers;
	//Enquetes are stored here while they continue to try to contact a missing subscriber
	protected LinkedList<Enquete> pendingEnquetes;
	// Maps from the name of a keyword to the ID of the clients that receive those keyword enquetes
	// in order to allow for efficient content-filtering
	protected HashMap<String, LinkedHashSet<Integer>> contentFilter;
	// Maps from the ID of a client to the actual RMI object of the client 
	// This allows the client to leave and come back later without 
	//changing the unique identifier
	protected HashMap<Integer, SubscriberInterface> clientBinding;

	public Util util;

	/**
	 * Constructor
	 * @param preload whether to load a pre-made selection of topics at startup
	 * @throws RemoteException for RMI errors
	 */
	public ServerImpl(boolean preload) throws RemoteException {
		allEnqueteContainers = new LinkedHashSet<>();
		pendingEnquetes = new LinkedList<>();
		contentFilter = new HashMap<>();
		clientBinding = new HashMap<>();
		if (preload)
			this.loadPrebuiltTopics();

		util = new Util();
	}
	
	/**
	 * Gives pre-made topics to start with rather than creating them all.
	 * The topics are stored in topics.dat as the following format:
	 * topic name:keyword keyword keyword...
	 * where the topic name is to the left of the colon and keywords follow, 
	 * separated from each other by whitespace 
	 */
	private void loadPrebuiltTopics() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File("topics.dat")));
			String line;
			while( (line =in.readLine()) != null) {
				String[] enqueteData = line.split(":");
				this.addEnquete(new Enquete(enqueteData[0].toString(), 
					enqueteData[1].toString(), 
					enqueteData[2].toString(), 
					enqueteData[3].toString(), 
					enqueteData[4].toString()
					) );

				
				
			}
			in.close();
		} catch (Exception e) { System.out.println("Error loading prebuilt topics."); }
	}

	/**
	 * see interface javadoc
	 */
	public int sayHello(SubscriberInterface sub) throws RemoteException {
		synchronized (clientBinding) {
			clientBinding.put(++subscriberId, sub);
			return subscriberId;
		}
	}
	
	/**
	 * see interface javadoc
	 */
	public int sayHello(Integer id, SubscriberInterface subscriber) throws RemoteException {
		synchronized (clientBinding) {
			clientBinding.put(id, subscriber);
			return id;
		}
	}
	
	/**
	 * see interface javadoc
	 */
	public void unbind(Integer id) {
		synchronized (clientBinding) {
			clientBinding.put(id, null);
		}
	}
	/**
	 * see interface javadoc
	 */
	public void unbindPermanent(Integer id) {
		synchronized (clientBinding) {
			clientBinding.remove(id);
		}
	}
	
	public SubscriberInterface getSubscriber(Integer id) {
		return clientBinding.get(id);
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//  Asynchronous notification service
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This is the method that runs in the background to contact all the Subscribers continually when they 
	 * are offline until they return
	 */
	public void startService() {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					try { Thread.sleep(TIMEOUT); } catch (InterruptedException e1) { }
					synchronized(pendingEnquetes) {
						while (pendingEnquetes.isEmpty()) {
							try {  
								pendingEnquetes.wait(); 
							} catch (Exception e) { }
						}
						asynchNotify();
					}
				}
			}
			/**
			 * helper method that iterates through each enquete that is pending compelte 
			 * notification of its subscribers
			 */
			public void asynchNotify() {
				Iterator<Enquete> enquete_iter = pendingEnquetes.iterator();
				while( enquete_iter.hasNext() ) {
					if (notifySubscribers(enquete_iter.next()) == 0) 
						enquete_iter.remove();	
				}
			}
		});
		//Daemon allows this thread not to block program from exiting
		t.setDaemon(true);
		t.start();		
	}

	
	////////////////////////////////////////////////////////////////////////////////////
	//  Publisher services 
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Helper method to notify all subscribers of a given enquete.  Subscribers have been added based on both 
	 * Topic filtering and content filtering at this point.  As subscribers are contacted, they are removed from
	 * the internal list of the enquete.  When the enquete subscriber list is empty, then the enquete is removed from
	 * the lsit of all pending enquetes
	 * 
	 * @param enquete Enquete to notify subscribers of
	 */
	public int notifySubscribers(Enquete enquete) {
		Iterator<Integer> sub_iter = enquete.iterator();
		while (sub_iter.hasNext()) {
			try {
				Integer subID = sub_iter.next();
				if (clientBinding.get(subID) != null) {
					clientBinding.get(subID).notify(enquete);
					sub_iter.remove();
				}
			} catch(RemoteException e) { } //Do nothing on remote exception, try again later 
		}
		//when this returns 0, we know every subscriber has received the message
		return enquete.notifySize();
	}
	

	/**
	 * see interface javadoc
	 */
	public int addEnquete(Enquete enquete) throws RemoteException {
		synchronized (allEnqueteContainers) {
			if (allEnqueteContainers.add( new EnqueteContainer(enquete) )) {
				enquete.setId(++enqueteId);
				return enqueteId;
			}
			return 0;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//  Subscriber services 
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * see interface javadoc
	 */
	public boolean addSubscriber(Integer subscriber, Enquete enquete) throws RemoteException {
		for( EnqueteContainer tc : allEnqueteContainers) {
			if (tc.getEnquete().getId() == enquete.getId() ) {
				return tc.addSubscriber(subscriber);
			}
		}
		return false;
	}
		
	
	/**
	 * see interface javadoc
	 */
	public boolean removeSubscriber(Integer subscriber, Enquete enquete) throws RemoteException {
		for( EnqueteContainer tc : allEnqueteContainers) {
			if (tc.getEnquete().getId() == enquete.getId() ) {
				return tc.removeSubscriber(subscriber);
			}
		}
		return false;
	}

	/**
	 * see interface javadoc
	 */
	public boolean removeSubscriber(Integer subscriber) throws RemoteException {
		for( EnqueteContainer tc : allEnqueteContainers)
			tc.removeSubscriber(subscriber);						
		return true;
	}
	
	/**
	 * see interface javadoc
	 */
	public ArrayList<Enquete> getEnquetes() {
		synchronized (allEnqueteContainers) {
			ArrayList<Enquete> enquetes = new ArrayList<>();
			for (EnqueteContainer enqueteContainer : allEnqueteContainers)
				enquetes.add(enqueteContainer.getEnquete());
			return enquetes;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//  Command-line interface services 
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This is how a user interacts with the server side of the pub-sub system.  Allows for
	 * 1. Showing all topics
	 * 2. Showing all subscribers
	 * 3. Quitting
	 * Note you cannot show all enquetes because they are not stored after being fully delivered
	 * 
	 * @throws RemoteException
	 */
	public void commandLineInterface() throws RemoteException {
		Scanner in = new Scanner(System.in);
		do {
			// Limpar o console e mostrar as opções do menu
			util.cleanRefreshPrintServerHeader();	
			System.out.println("Menu - Selecione uma das opções:");		
			System.out.println(" ");
			System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Listar Enquetes");
			System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Listar Subscribers");
			System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Sair");
			System.out.println(" ");
			System.out.print("Digite o número [1 - 9]: ");
			int choice = -1;
			try {
				choice = in.nextInt(); in.nextLine();
			} catch (Exception e) { in.nextLine(); }
			switch (choice) {
				case 1: 
					for (EnqueteContainer tc : allEnqueteContainers)
						System.out.print( tc.getEnquete() );
					break;
				//case 2: showSubscribers(); break;
				case 9: in.close(); System.exit(0); 
				default: System.out.println("Input not recognized");
			}
		} while (true);
	}

	@Override
	public int publish(Enquete enquete) throws RemoteException {
		 if (enquete.getId() != 0) {
		 	System.err.println("Enquete has already been published.");
		 	return 0;
		 }
		 synchronized (allEnqueteContainers) {
		 	for( EnqueteContainer tc : allEnqueteContainers) {
		 		if (tc.getEnquete().getId() == enquete.getId() ) {		 		
		 			enquete.setId(++enqueteId).addSubscriberList(tc.getSubscribers());
					 
					//for(String key : enquete.getKeywords() )
		 			//	enquete.addSubscriberList(contentFilter.get(key));
					 
					if (notifySubscribers(enquete) > 0) {
		 				synchronized (pendingEnquetes) {
		 					pendingEnquetes.add(enquete);
		 					pendingEnquetes.notifyAll();
		 				}
		 			}
		 			return enqueteId;
		 		}
		 	}
		 }
		 System.err.println("Enquete topic not found.");
		return 0;
	}
	
	/**
	 * show the complete list of subscribers, used by server for command line printing
	 * Prints both all the subscribers to each topic and all the subscribers to each 
	 * keyword
	 */	
	public void showSubscribers() throws RemoteException {
		for( EnqueteContainer tc : allEnqueteContainers) 
			System.out.print("Topic: " +tc.getEnquete().getNome());
		String contentPrint = "";
		for( String key : contentFilter.keySet() ) {
			contentPrint += "Keyword: " + key + "\n\tSubscribers: ";
			int i = contentFilter.get(key).size();
			if (i == 0)
				contentPrint += "None\n";
			for ( Integer subID : contentFilter.get(key) )
				contentPrint += "Agent_" + subID + ((--i > 0)?",":"\n");
		}
		System.out.print(contentPrint);
	}	
}
