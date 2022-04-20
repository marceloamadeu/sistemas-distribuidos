package br.edu.utfpr.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;

public class ClientMain {

    public static void main(String[] args) {
        try {
            
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);                    
            System.out.println(
              "Enter how many seconds to stay registered:");
            String timeDuration = br.readLine();
            int time = Integer.parseInt(timeDuration);
            String registryURL = "rmi://localhost:" + 33600 + "/callback";  
            // find the remote object and cast it to an 
            //   interface object
            ServerInterface h = (ServerInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed " );
            System.out.println("Server said " + h.sayHello());
            ClientInterface callbackObj = new ClientImpl();
            // register for callback
            h.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");
            try {
              Thread.sleep(time * 1000);
            } catch (InterruptedException ex){ // sleep over
                System.out.println("Exception in InterruptedException: " + ex);
            }
            h.unregisterForCallback(callbackObj);
            System.out.println("Unregistered for callback.");
          } // end try 
          catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
          } // end catch
    }
}
