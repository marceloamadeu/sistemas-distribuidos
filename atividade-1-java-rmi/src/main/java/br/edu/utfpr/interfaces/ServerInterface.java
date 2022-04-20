package br.edu.utfpr.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the methods the client (both Publisher and Subscriber) can call on the server.
 * Since the interface extends Remote, each method must throw RemoteException
 * 
 */
public interface ServerInterface extends Remote {

	public String sayHello() throws RemoteException;
	public void registerForCallback(ClientInterface callbackClientObject) throws RemoteException;
	public void unregisterForCallback(ClientInterface callbackClientObject) throws RemoteException;
	
	/**
	 * This method establishes the relationship between server and client
	 * 
	 * @param subscriber Subscriber registering with the server
	 * @return unique int Id of this client for the Server to track
	 * @throws RemoteException if server is offline
	 */
	//public int sayHello(SubscriberInterface subscriber) throws RemoteException;
	
	/**
	 * This method re-establishes the relationship between server and client
	 * 
	 * @param subscriber Subscriber re-registering with the server after being away
	 * @param Id Integer that the client received when first registering
	 * @return same Id this client had previously
	 * @throws RemoteException if server is offline
	 */
	//public int sayHello(Integer Id, SubscriberInterface subscriber) throws RemoteException;

	/**
	 * Publishes advertises a new topic
	 * 
	 * @param topic the topic the Publisher wants to advertise
	 * @return unique Id of this Topic
	 * @throws RemoteException if server is offline
	 */
	//public int addEnquete(Enquete Enquete) throws RemoteException;

	/**
	 * User subscribes to given topic
	 * 
	 * @param subId unique subscriber Id of the client 
	 * @param t topic to subscribe to 
	 * @return boolean on whether subscription was successful
	 * @throws RemoteException
	 */
	//public boolean addSubscriber(Integer subscriber, Enquete enquete) throws RemoteException;

	/**
	 * User unsubscribes from all topics and keywords
	 * 
	 * @param subId unique subscriber Id of the client 
	 * @return  boolean if user was successfully removed from all topics and keywords
	 * @throws RemoteException
	 */
	//public boolean removeSubscriber(Integer subscriber) throws RemoteException;
		
	/**
	 * User unsubscribes from a given topic 
	 * 
	 * @param subId unique subscriber Id of the client 
	 * @param t topic to unsubscribe from
	 * @return  boolean if user was successfully removed from all topics and keywords
	 * @throws RemoteException
	 */
	//public boolean removeSubscriber(Integer subscriber, Enquete enquete) throws RemoteException;
	
	/**
	 * Publisher publishes an event to those clients that are subscribed to given topic or keywords
	 * 
	 * @param event to be published
	 * @return the unique Id of this event
	 * @throws RemoteException
	 */
	//public int publish(Enquete enquete) throws RemoteException;
	
	/**
	 * The server will return a list of all available topics for a subscriber to choose from
	 * 
	 * @return ArrayList of Topics on server 
	 * @throws RemoteException
	 */
	//public ArrayList<Enquete> getEnquetes() throws RemoteException;
	
	/**
	 * Subscriber sets Id to null while he is offline, saysHello once he comes back online to re-establish
	 * relationship
	 * 
	 * @param Id unique subscriberId that is set to null
	 * @throws RemoteException
	 */
	//public void unbind(Integer subscriber) throws RemoteException;
	
	/**
	 * This user is quitting and will not be returning
	 * 
	 * @param Id unique subscriberId that is removed altogether
	 * @throws RemoteException
	 */
	//public void unbindPermanent(Integer subscriber) throws RemoteException;

	/**
	 * Find a Subscriber by his unique Id
	 * 
	 * @param Id of Subscriber to return
	 * @return Subscriber object
	 * @throws RemoteException
	 */
	//public SubscriberInterface getSubscriber(Integer subscriber) throws RemoteException;
}
