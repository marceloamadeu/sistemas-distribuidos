package br.edu.utfpr.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public String notifyMe(String message) throws RemoteException;
}
