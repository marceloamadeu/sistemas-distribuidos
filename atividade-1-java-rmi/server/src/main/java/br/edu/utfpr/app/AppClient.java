package br.edu.utfpr.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Hello world!
 *
 */
public class AppClient {


    public static final String SERVER_NAME = "survey_server";
    public static final int SERVER_PORT = 33600;

    public static void main(String args[]) {
        try {          
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("Enter how many seconds to stay registered:");
            String timeDuration = br.readLine();
            int time = Integer.parseInt(timeDuration);

            String registryURL = "rmi://localhost:" + SERVER_PORT + "/callback";  
            ServerInterface server = (ServerInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed " );
            System.out.println("Server said " + server.sayHello());
            ClientInterface callbackClientObj = new ClientImpl();
            // register for callback
            server.registerForCallback(callbackClientObj);
            System.out.println("Registered for callback.");
            try {
                Thread.sleep(time * 1000);
              } catch (InterruptedException e) { 
                System.out.println("InterruptedException in AppClient.main: " + e);
            }
            server.unregisterForCallback(callbackClientObj);
            System.out.println("Unregistered for callback.");
        } catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
        } 
      } 
}
