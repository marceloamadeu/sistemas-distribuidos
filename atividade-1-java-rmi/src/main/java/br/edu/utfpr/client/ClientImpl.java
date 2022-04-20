package br.edu.utfpr.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.edu.utfpr.interfaces.ClientInterface;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
    
    public ClientImpl() throws RemoteException {
        super();
    }
  
    public String notifyMe(String message){
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }      
}
