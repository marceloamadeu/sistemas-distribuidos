package br.edu.utfpr.server;



import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;
import br.edu.utfpr.util.Util;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;
import java.util.Vector;


public class ServerImpl  extends UnicastRemoteObject implements ServerInterface {
 
    private Vector clientList;
    private Util util = new Util();

    protected ServerImpl() throws RemoteException {
        super();      
        clientList = new Vector();  
    }

    public String sayHello() throws RemoteException {
      return("hello");
    }

    public synchronized void registerForCallback(ClientInterface callbackClientObject) throws RemoteException {
      // store the callback object into the vector
      if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("Registered new client ");
            doCallbacks();
        } 
    }  

    public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws RemoteException{
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println("unregister: clientwasn't registered.");
        }
    } 

    private synchronized void doCallbacks() throws RemoteException{
        // make callback to each registered client
        System.out.println("**************************************\n" + "Callbacks initiated ---");
        for (int i = 0; i < clientList.size(); i++){
            System.out.println("doing "+ i +"-th callback\n");    
            // convert the vector object to a callback object
            ClientInterface nextClient = (ClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " +  clientList.size());
        }// end for
        System.out.println("********************************\n" + "Server completed callbacks ---");
    } // doCallbacks



    //=====================================================
    //                      MENU
    //=====================================================
    /**
     * This is how a user interacts with the server side of the pub-sub system.  Allows for
     * 1. Showing all topics
     * 2. Showing all subscribers
     * 3. Quitting
     * Note you cannot show all enquetes because they are not stored after being fully delivered
     *
     * @throws RemoteException
     */
    public void commandLineInterface(String hostname, int port) throws RemoteException {
        Scanner in = new Scanner(System.in);
        do {
            // Limpar o console e mostrar as opções do menu
            util.cleanRefreshPrintServerHeader();
            System.out.println(util.TEXT_GREEN + "Server Pronto!!! Disponível em " + util.TEXT_RESET + hostname + ":" + port);
            System.out.println(" ");
            System.out.println("Menu - Selecione uma das opções:");
            System.out.println(" ");
            System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Listar Enquetes");
            System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Listar Subscribers");
            System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Sair");
            System.out.println(" ");
            System.out.print("Digite o número [1 - 9]: ");
            int choice = -1;
            try {
                choice = in.nextInt(); in.nextLine();
            } catch (Exception e) { in.nextLine(); }
            switch (choice) {
                case 1:
                    //for (EnqueteContainer tc : allEnqueteContainers)
                    //    System.out.print( tc.getEnquete() );
                    break;
                //case 2: showSubscribers(); break;
                case 9: in.close(); System.exit(0);
                default: System.out.println("Input not recognized");
            }
        } while (true);
    }

}
