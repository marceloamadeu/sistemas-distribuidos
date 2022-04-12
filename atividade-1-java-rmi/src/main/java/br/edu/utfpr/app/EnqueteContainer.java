package br.edu.utfpr.app;
//******************************************************************************
//File:    EnqueteContainer.java
//Package: pubsub
//Unit:    Distributed Programming Individual Project
//******************************************************************************
import java.util.Iterator;
import java.util.LinkedHashSet;

import br.edu.utfpr.app.entity.Enquete;
/**
 * 
 * This is a class for the server to hold a Enquete in along with the subscribers to that enquete.
 * This way, the server can give the individual Enquetes to other clients that ask to see what is 
 * available without giving away who is subscribed to each Enquete. The Container knows how to add,
 * remove, and iterate over its subscribers, to the server uses these methods to manage Enquete-based
 * subscriptions.
 * 
 * @author rob mccartney
 *
 */
public class EnqueteContainer {

	//two parts to a container, the enquete and the subscribers
	private Enquete enquete;
	//the Integer is the Unique Id of the client subscriber
	//Use a LinkedHashSet to allow O(1) lookup and efficient
	//iteration
	private LinkedHashSet<Integer> enqueteSubscribers;
	
	/**
	 * Constructor
	 * @param _enquete that will be held in this container
	 */
	public EnqueteContainer(Enquete _enquete) {
		this.enquete = _enquete;
		enqueteSubscribers = new LinkedHashSet<>();
	}
	/**
	 * 
	 * @return the underlying enquete
	 */
	public Enquete getEnquete() {
		return enquete;
	}
	
	/**
	 * Synchronously add a new subscriber to the set.  Needs to be synchronous to prevent a 
	 * concurrent modification during iterating
	 * 
	 * @param subId the subscriber Id to add to this container
	 * @return boolean on success or failure. Fails when the user is already subscribed
	 */
	public synchronized boolean addSubscriber(Integer subId) {
		return enqueteSubscribers.add(subId);
	}
	
	/**
	 * Synchronously removed a subscriber from the set.  Needs to be synchronous to prevent a 
	 * concurrent modification during iterating
	 * 
	 * @param subId the subscriber Id to remove from this container
	 * @return boolean on success or failure. Fails when the user being removed is not subscribed
	 */
	public synchronized boolean removeSubscriber(Integer subId) {
		return enqueteSubscribers.remove(subId);
	}
	
	/**
	 * Synchronously get the size of the underlying data structure
	 * 
	 * @return int size of the underlying hashset of subscribers
	 */
	public synchronized int getSubscriberSize() {
		return enqueteSubscribers.size();
	}
	
	/**
	 * Synchronously return the underlying hashset so that it can be added to another list (inside event)
	 * 
	 * @return LinkedHashSet<Integer> the Id's of the subscribers
	 */
	public synchronized LinkedHashSet<Integer> getSubscribers() {
		return enqueteSubscribers;
	}
	
	/**
	 * Use the iterator to iterate through the hash and allow removal during iteration
	 * 
	 * @return Iterator<Integer> to iterate through the subscriber Id's
	 */
	public synchronized Iterator<Integer> iterator() {
		return enqueteSubscribers.iterator();
	}
	
	/**
	 * Overrides the Object equals so that we will not allow two equivalent EnqueteContainers into a HashSet
	 * as determined by their underlying Enquete
	 */
	public boolean equals(Object obj) {
		return enquete.equals( ((EnqueteContainer)obj).getEnquete() );
	}
	
	/**
	 * This makes sure equivalent containers actually collide when they are hashed, otherwise we could 
	 * not find duplicates
	 */
	public int hashCode() {
		return enquete.hashCode();
	}
	
	/**
	 * Synchronously iterate through the list of subscribers to this enquete to print them out
	 * 
	 * @return String of the subscribers currently subscribed to the underlying enquete
	 */
	public synchronized String printSubscribers() {
		String formatted = "";
		int i = enqueteSubscribers.size();
		if (i == 0)
			formatted += "None\n";
		for ( Integer subId : enqueteSubscribers )
			formatted += "Agent_" + subId + ((--i > 0)?",":"\n");
		return formatted;
	}
	
	/**
	 * Synchronously overrides the Object toString 
	 */
	public synchronized String toString() {
		return enquete.toString() + "\n\tSubscribers: " + this.printSubscribers();
	}
}
