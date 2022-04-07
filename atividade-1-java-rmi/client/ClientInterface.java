package br.edu.utfpr.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    String ping(String message) throws RemoteException;    
    String notifyChanges(String message) throws RemoteException;
}
