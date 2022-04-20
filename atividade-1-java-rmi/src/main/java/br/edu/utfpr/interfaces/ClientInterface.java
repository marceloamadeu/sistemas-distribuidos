package br.edu.utfpr.interfaces;

import java.rmi.RemoteException;

public interface ClientInterface {
    public String notifyMe(String message) throws RemoteException;

}
