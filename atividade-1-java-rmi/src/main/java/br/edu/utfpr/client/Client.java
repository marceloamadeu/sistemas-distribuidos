package br.edu.utfpr.client;

import br.edu.utfpr.entity.Usuario;
import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;
import br.edu.utfpr.util.Util;

import java.io.*;
import java.rmi.*;
import java.util.Scanner;

public class Client {

  private static final int PORT = 33600;
  private static final String HOSTNAME = "localhost";
  private static Util util = new Util();
  private static Usuario user = null;

  public static void main(String args[]) {
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(System.in);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      user = new Usuario();


      ServerInterface server = (ServerInterface) Naming.lookup("rmi://localhost:" + PORT + "/callback");
      ClientInterface client = new ClientImpl();

      server.register(client);

      nameMenu(user, server, client);

      System.out.println("Unregistered for callback.");
    } // end try 
    catch (Exception e) {
      System.out.println("Exception in CallbackClient: " + e);
    } // end catch
  } //end main





  //=====================================================
  //                  MENU - CLIENTE
  //=====================================================

  public static void clientMainMenu(Usuario user, ServerInterface server, ClientInterface client) throws RemoteException {
    Scanner in = new Scanner(System.in);
    do {
      // Limpar o console e mostrar as opções do menu
      util.cleanRefreshPrintClientHeader();
      System.out.println(" ");
      System.out.println("Menu - Selecione uma das opções:");
      System.out.println(" ");
      System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Listar Enquetes");
      System.out.println(util.TEXT_GREEN + " [ 2 ] " + util.TEXT_RESET + "Cadastrar Enquete");
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
        case 2:
          System.out.print("Digite o nome da enquete: ");
          String nome = in.nextLine();
          System.out.print("Digite o titulo: ");
          String titulo = in.nextLine();
          System.out.print("Digite o local: ");
          String local = in.nextLine();
          System.out.print("Tempo: ");
          String tempo = in.nextLine();
          System.out.print("Data de fim da enquete: ");
          String dataFinalEnquete = in.nextLine();

          server.addEnquete(nome, titulo, local, tempo, dataFinalEnquete, user);
          clientMainMenu(user, server, client);
          break;
        case 9: in.close(); System.exit(0);
        default: System.out.println("Input not recognized");
      }
    } while (true);
  }

  /***
   * 1.
   * 2.
   * 9. Sair*
   *
   * @throws RemoteException
   */
  public static void nameMenu(Usuario user, ServerInterface server, ClientInterface client) throws RemoteException {
    Scanner in = new Scanner(System.in);
    do {
      // Limpar o console e mostrar as opções do menu
      util.cleanRefreshPrintClientHeader();
      // Mensagem de bem-vindo
      System.out.println(" ");
      System.out.println(util.TEXT_YELLOW + "Bem-Vindo ao sistema de Agendamento de Reuniões (Enquetes)!!!" + util.TEXT_RESET);
      System.out.println(" ");
      System.out.println(util.TEXT_RESET + HOSTNAME + ":" + PORT + " - " + util.TEXT_GREEN + "online" + util.TEXT_RESET );
      // Menu
      System.out.println(" ");
      System.out.println("Menu - Selecione uma das opções:");
      System.out.println(" ");
      System.out.println(util.TEXT_GREEN + " [ 1 ] " + util.TEXT_RESET + "Cadastro Usuário");
      System.out.println(util.TEXT_GREEN + " [ 9 ] " + util.TEXT_RESET + "Sair");
      System.out.println(" ");
      System.out.print("Digite o número [1 - 9]: ");
      int choice = -1;
      try {
        choice = in.nextInt(); in.nextLine();
      } catch (Exception e) {
        in.nextLine();
      }
      switch (choice) {
        case 1:
          System.out.print("Digite seu nome: ");
          String nome = in.nextLine();
          user = server.addUser(nome, client);
          clientMainMenu(server, client);
          break;
        case 9:
          in.close();
          System.exit(0);
          server.unregister(client);
        default: System.out.println("Opção não encontrada!!!");
      }
    } while (true);
  }







}//end class
