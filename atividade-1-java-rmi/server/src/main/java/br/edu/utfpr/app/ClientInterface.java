package br.edu.utfpr.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    //void update(int survey) throws RemoteException;
    //void notifyChanges() throws RemoteException;
    public String notifyMe(String message) throws java.rmi.RemoteException;
}
