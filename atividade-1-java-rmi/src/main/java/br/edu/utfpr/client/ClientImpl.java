package br.edu.utfpr.client;

import br.edu.utfpr.entity.Enquete;
import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;

import java.rmi.*;
import java.rmi.server.*;


/**
 * A classe servente do cliente que é usada para implementar todos os
 * métodos definidos na interface.
 *
 * extends UnicastRemoteObject - Used for exporting a remote object with
 * JRMP and obtaining a stub that communicates to the remote object.
 *
 * implements - ClientInterface
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
  
   public ClientImpl(ServerInterface server) throws RemoteException {
      super();
   }

   public ClientImpl() throws RemoteException {
      super();
   }

   public String notifyMe(String message){
      String returnMessage = "\n" + message;
      System.out.println(returnMessage);
      return returnMessage;
   }

   @Override
   public void notify(ClientInterface client) throws RemoteException {
      System.out.println("aeeeeeeeeee");
   }

   @Override
   public void subscribe(Enquete enquete) throws RemoteException {

   }

   @Override
   public void notify(Enquete enquete) throws RemoteException {

   }
}
