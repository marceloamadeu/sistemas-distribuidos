package br.edu.utfpr.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public String sendPing() throws RemoteException;
    public void register(Client client) throws RemoteException;
    public void unregister(Client client) throws RemoteException;
}


https://github.com/rcmccartney/pubsub