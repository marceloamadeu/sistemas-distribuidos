package br.edu.utfpr.server;



import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;


public class ServerImpl  extends UnicastRemoteObject implements ServerInterface {
 
    private Vector clientList;

    protected ServerImpl() throws RemoteException {
        super();      
        clientList = new Vector();  
    }

    public String sayHello() throws RemoteException {
      return("hello");
    }

    public synchronized void registerForCallback(ClientInterface callbackClientObject) throws RemoteException {
      // store the callback object into the vector
      if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("Registered new client ");
            doCallbacks();
        } 
    }  

    public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws RemoteException{
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println("unregister: clientwasn't registered.");
        }
    } 

    private synchronized void doCallbacks() throws RemoteException{
        // make callback to each registered client
        System.out.println("**************************************\n" + "Callbacks initiated ---");
        for (int i = 0; i < clientList.size(); i++){
            System.out.println("doing "+ i +"-th callback\n");    
            // convert the vector object to a callback object
            ClientInterface nextClient = (ClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " +  clientList.size());
        }// end for
        System.out.println("********************************\n" + "Server completed callbacks ---");
    } // doCallbacks

}
