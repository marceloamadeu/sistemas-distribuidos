package br.edu.utfpr.interfaces;

import br.edu.utfpr.entity.Usuario;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is a remote interface for illustrating RMI 
 * client callback.
 * @author M. L. Liu
 */

public interface ServerInterface extends Remote {

  //public String sayHello() throws java.rmi.RemoteException;

  // This remote method allows an object client to
  // register for callback
  // @param callbackClientObject is a reference to the
  //        object of the client; to be used by the server
  //        to make its callbacks.
  //public void register(ClientInterface callbackClientObject) throws RemoteException;

  // This remote method allows an object client to
  // cancel its registration for callback
  //public void unregister(ClientInterface callbackClientObject) throws RemoteException;


  public String sayHello() throws java.rmi.RemoteException;
  public void register(ClientInterface client) throws RemoteException;
  public void unregister(ClientInterface client) throws RemoteException;
  public void addUser(String userName, ClientInterface client) throws RemoteException;
  public void removeUser(Usuario user) throws RemoteException;
}
