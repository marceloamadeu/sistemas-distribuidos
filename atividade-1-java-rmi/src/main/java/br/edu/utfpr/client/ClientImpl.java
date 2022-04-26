package br.edu.utfpr.client;

import br.edu.utfpr.entity.Enquete;
import br.edu.utfpr.interfaces.ClientInterface;

import java.rmi.*;
import java.rmi.server.*;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
  
   public ClientImpl() throws RemoteException {
      super();
   }

   public String notifyMe(String message){
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }

   @Override
   public void subscribe(Enquete enquete) throws RemoteException {

   }

   @Override
   public void notify(Enquete enquete) throws RemoteException {

   }
}
