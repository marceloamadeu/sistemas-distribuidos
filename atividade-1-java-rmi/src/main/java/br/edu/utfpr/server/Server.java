package br.edu.utfpr.server;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.*;

/**
 * This class represents the object server for a distributed
 * object of class Callback, which implements the remote 
 * interface CallbackInterface.
 * @author M. L. Liu
 */

public class Server {

  private static final int PORT = 33600;
  private static final String HOSTNAME = "localhost";

  public static void main(String args[]) {
    InputStreamReader is = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(is);

    try{
      startRegistry(PORT);
      ServerImpl exportedObj = new ServerImpl();
      Naming.rebind("rmi://localhost:" + PORT + "/callback", exportedObj);
      System.out.println("Callback Server ready.");
    }
    catch (Exception re) {
      System.out.println("Exception in HelloServer.main: " + re);
    }
  }

  private static void startRegistry(int RMIPortNum)
    throws RemoteException{
    try {
      Registry registry = LocateRegistry.getRegistry(RMIPortNum);
      registry.list( );
    } catch (RemoteException e) {
      Registry registry = LocateRegistry.createRegistry(RMIPortNum);
    }
  }

}
