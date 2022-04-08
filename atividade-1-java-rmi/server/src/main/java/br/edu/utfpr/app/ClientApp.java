package br.edu.utfpr.app;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class ClientApp {

    public static final String SERVER_NAME = "localhost";
    public static final int SERVER_PORT = 33600;

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

    
    /**
     * Menu com as opções disponíveis para teste
     * @param options
     */    
    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
    
    
    
    

    public static void main( String[] args ) {

        String[] options = {
            "1 - Cadastro de Enquete",
            "2 - Cadastro de Voto em uma Enquete ",
            "3 - Consulta de Enquete",
            "9- Exit",
        };

        try {                             
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);                        
            String url = "//" + SERVER_NAME+ ":" + SERVER_PORT + "/sd";                                                                                   
                        
            // find the remote object and cast it to an  interface object
            Server server = (Server) Naming.lookup(url);
                        
            Client client = new ClientImpl();
            // register for callback
            server.register(client);            
            System.out.println(TEXT_GREEN + "[+] " + TEXT_RESET + "Registered for callback");

            Scanner scanner = new Scanner(System.in);
            int option = 1;
            while (option != 9) {
                printMenu(options);
                try {
                    option = scanner.nextInt();

                    if (option == 8) {
                        server.sendPing();
                    }
                } catch (InputMismatchException ex){
                    System.out.println(TEXT_RED + "[+] " + TEXT_RESET + "Please enter an integer value between 1 and " + options.length);
                    scanner.next();
                } catch (Exception ex) {
                    System.out.println(TEXT_RED + "[+] " + TEXT_RESET + "An unexpected error happened. Please try again");
                    scanner.next();
                }
            }
                    
            server.unregister(client);
            System.out.println(TEXT_GREEN + "[+] " + TEXT_RESET + "Unregistered for callback");            
          } catch (Exception e) {            
            System.out.println(TEXT_RED + "[+] " + TEXT_RESET + "Exception in CallbackClient: " + e);
          } 
        } //end main
}
