package br.edu.utfpr.server;

import br.edu.utfpr.client.ClientImpl;
import br.edu.utfpr.entity.Usuario;
import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;
import br.edu.utfpr.util.Util;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

   protected List<ClientInterface> clientList;
   protected List<Usuario> userList;
   protected Util util = null;

   public ServerImpl() throws RemoteException {
       super();
       clientList = new ArrayList<>();
       userList = new ArrayList<>();
       util = new Util();
   }

  @Override
  public String sayHello() throws RemoteException {
    return "hello";
  }

  @Override
  public void register(ClientInterface client) throws RemoteException {
    synchronized(clientList) {
        if (!(clientList.contains(client))) {
            clientList.add(client);
            System.out.println("Registered new client OK");
            doCallbacks();
        }
    }
  }

    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        synchronized(clientList) {
            if (clientList.remove(client)) {
                System.out.println("Unregistered client OK");
            } else {
                System.out.println("unregister: client wasn't registered. OK");
            }
        }
    }

    @Override
    public void addUser(String name, ClientInterface client) throws RemoteException {
        synchronized (userList) {
            userList.add(new Usuario(1, name, "12345678", "Enquete", client));
        }
    }

    @Override
    public void removeUser(Usuario user) throws RemoteException {
        synchronized (userList) {
            userList.remove(user);
        }
    }

    private synchronized void doCallbacks( ) throws java.rmi.RemoteException {
       util.cleanRefreshPrintServerHeader();
       for (ClientInterface client : clientList) {
           client.notifyMe("Cliente Registrado. Usuário: " +  clientList.size());
       }
       System.out.println("********************************\n" + "Server completed callbacks ---");
    }


}
