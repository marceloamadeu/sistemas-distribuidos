package br.edu.utfpr.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.edu.utfpr.app.entity.User;


/**
 * Hello world!
 *
 */
public class AppClient {


    public static final String SERVER_NAME = "localhost";
    public static final int SERVER_PORT = 33600;
        
    
    
    /**
     * Menu com as opções disponíveis para teste
     * @param options
     */
    public static void printMenu(String[] options){
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }


    /**
     * Cadastro usuário:
     * o Ao acessar o sistema pela primeira vez, cada cliente deve
     * informar seu nome, chave pública e sua referência de objeto
     * remoto. Nesse cadastro, o cliente automaticamente atuará
     * como subscriber, registrando interesse em receber
     * notificações do servidor quando uma nova enquete for
     * cadastrada.
     * @param user
     */
    public static void addUser(ServerInterface server, User user) {
        try {
            server.addUser(user);
        } catch (RemoteException e) {
            System.out.println("User registration error: " + e.toString());
        }
    }

    public static void main(String args[]) {
        try {          

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);



            System.out.println("...................................................................." );
            System.out.println("........................ CallBack Client ..........................." );
            System.out.println("...................................................................." );
            System.out.println(" " );
            
            Registry registry = LocateRegistry.getRegistry(SERVER_NAME);
            String url = "rmi://" + SERVER_NAME + ":" + SERVER_PORT + "/callback";
            ServerInterface server = (ServerInterface) Naming.lookup(url);
            System.out.println("Lookup...............................: Completed " );
            Naming.rebind(url, server);
            
            String response = server.sayHello();
            System.out.println("Server response......................: " + response);

            ClientInterface callbackClientObj = new ClientImpl();
            System.out.println("Callback Client Obj..................: Created");

            server.registerForCallback(callbackClientObj);
            System.out.println("Client Registered for callback.......: Ready");

            
            /**
             * Cadastro do Usuário
             */
            System.out.println(" ");
            User user = new User();

            System.out.println("Name: ");
            user.setName(bufferedReader.readLine());

            System.out.println("Public Key: ");
            user.setPublicKey(bufferedReader.readLine());

            System.out.println("Remote Object Reference: ");
            user.setRemoteObjectReference(bufferedReader.readLine());           

            addUser(server, user);
            //***************************************/



            String[] options = {"1- Option 1",
                    "2- Option 2",
                    "3- Option 3",
                    "9- Exit",
            };


            Scanner scanner = new Scanner(System.in);
            int option = 1;
            while (option != 9) {
                printMenu(options);
                try {
                    option = scanner.nextInt();

                    if (option == 5) {
                        
                        
                    }
                } catch (InputMismatchException ex){
                      System.out.println("Please enter an integer value between 1 and " + options.length);
                      scanner.next();
                } catch (Exception ex) {
                    System.out.println("An unexpected error happened. Please try again");
                    scanner.next();
                }
            }

            server.unregisterForCallback(callbackClientObj);
            System.out.println("Client Unregistered for callback");
            scanner.close();
                        

            //InputStreamReader is = new InputStreamReader(System.in);
            //BufferedReader br = new BufferedReader(is);
            //System.out.println("Enter how many seconds to stay registered:");
            //String timeDuration = br.readLine();
            //int time = Integer.parseInt(timeDuration);

            //String registryURL = "rmi://localhost:" + SERVER_PORT + "/callback";  
            //ServerInterface server = (ServerInterface) Naming.lookup(registryURL);
            //System.out.println("Lookup completed " );
            //System.out.println("Server said " + server.sayHello());
            //ClientInterface callbackClientObj = new ClientImpl();
            
            // register for callback
            //server.registerForCallback(callbackClientObj);
            //System.out.println("Registered for callback.");
            //try {
            //    Thread.sleep(time * 1000);
            //  } catch (InterruptedException e) { 
            //    System.out.println("InterruptedException in AppClient.main: " + e);
            //}
            //server.unregisterForCallback(callbackClientObj);
            //System.out.println("Unregistered for callback.");
        } catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
        } 
      } 
}
