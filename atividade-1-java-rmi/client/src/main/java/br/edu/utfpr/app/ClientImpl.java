package br.edu.utfpr.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

    protected ClientImpl() throws RemoteException {
        super();        
    }

    @Override
    public void update(int survey) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notifyChanges() throws RemoteException {
        // TODO Auto-generated method stub
        
    }
    
}
