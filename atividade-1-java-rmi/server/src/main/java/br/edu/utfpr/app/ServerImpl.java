package br.edu.utfpr.app;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ServerImpl extends UnicastRemoteObject implements Server {

    // Define color constants
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    private List<String> serverInfoList = new ArrayList<>();
    private Vector clientList;

    public ServerImpl() throws RemoteException {
        super();
        clientList = new Vector();
        cleanRefreshPrintHeader();
        serverInfoList.add(TEXT_GREEN + "[+] " + TEXT_RESET + "java RMI registry created");
        serverInfoList.add(TEXT_GREEN + "[+] " + TEXT_RESET + "Server is ready");
    }

    /**
     * Clean, refresh and print the console
     */
    public void cleanRefreshPrintHeader() {
        // Limpa o console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Header
        System.out.println("#####################################################");
        System.out.println("####                  SERVIDOR                   ####");
        System.out.println("#####################################################");
        System.out.println("");
    }

    /**
    * Print Info
    */
    public void printInfoList() {                                        
        for (String info : serverInfoList) {
            System.out.println(info);                            
        }        
    }
    
    

    @Override
    public String sendPing() throws RemoteException {
        return ("ping");
    }

    @Override
    public void register(Client client) throws RemoteException {        
        
        // store the callback object into the vector
        if (!(clientList.contains(client))) {
            clientList.addElement(client);
            System.out.println("Registered new client");
            doCallbacks();
        }        
    }

    @Override
    public void unregister(Client client) throws RemoteException {                
        if (clientList.removeElement(client)) {
            System.out.println("Unregistered client."); 
        } else {
            System.out.println("unregister: clientwasn't registered.");
        }               
    }

    private synchronized void doCallbacks() throws RemoteException{                     
        for (int i = 0; i < clientList.size(); i++){        
            // convert the vector object to a callback object
            Client nextClient = (Client) clientList.elementAt(i);
            // invoke the callback method
            //nextClient.notifyChanges("Number of registered clients=" +  clientList.size());
            serverInfoList.add(TEXT_GREEN + "[+] " + TEXT_RESET + "Teste");
        }
        cleanRefreshPrintHeader();
        printInfoList();                
    } 
}