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
    Registry referenciaServicoNomes = null;

    try{
      //Referência do serviço de nomes
      referenciaServicoNomes = startRegistry(PORT);
      //startRegistry(PORT);
      ServerImpl exportedObj = new ServerImpl();

      Naming.rebind("rmi://" + HOSTNAME + ":" + PORT + "/callback", exportedObj);
      //referenciaServicoNomes.rebind("rmi://" + HOSTNAME + ":" + PORT + "/callback", exportedObj);

      System.out.println("Callback Server ready.");
    }
    catch (Exception re) {
      System.out.println("Exception in HelloServer.main: " + re);
    }
  }

  /**
   * Método usado para registrar/acessar o serviço de nomes Java RMI
   *
   * Para usar a classe LocalRegistry foi criado duas classes distintas.
   * Uma como servidor e a outra cliente.
   * Também está sendo usado um pacote com o mesmo nome: br.edu.utfpr
   *
   * @param port
   * @throws RemoteException
   */
  private static Registry startRegistry(int port) throws RemoteException{
    Registry registry = null;
    try {
      registry = LocateRegistry.getRegistry(port);
      registry.list();
    } catch (RemoteException e) {
      registry = LocateRegistry.createRegistry(port);
    }
    return registry;
  }

}
