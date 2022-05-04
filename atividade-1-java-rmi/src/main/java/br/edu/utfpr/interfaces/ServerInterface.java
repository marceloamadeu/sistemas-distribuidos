package br.edu.utfpr.interfaces;

import br.edu.utfpr.entity.Enquete;
import br.edu.utfpr.entity.Usuario;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Server interface RMI - extends Remote
 *
 * EventManInterface
 */
public interface ServerInterface extends Remote {

  public void registrarInteresse(String texto, ClientInterface client) throws RemoteException;

  public String sayHello() throws RemoteException;
  public void register(ClientInterface client) throws RemoteException;
  public void unregister(ClientInterface client) throws RemoteException;
  public Usuario addUser(String userName, ClientInterface client) throws RemoteException;
  public void removeUser(Usuario user) throws RemoteException;
  public void addEnquete(String nome, String titulo, String local, String tempo, String dataFinalEnquete, Usuario user) throws RemoteException;

  public List<Enquete> getEnquetes() throws RemoteException;
}
