package br.edu.utfpr.server;

import br.edu.utfpr.entity.Enquete;
import br.edu.utfpr.entity.Usuario;
import br.edu.utfpr.interfaces.ClientInterface;
import br.edu.utfpr.interfaces.ServerInterface;
import br.edu.utfpr.util.EnqueteContainer;
import br.edu.utfpr.util.Util;

import java.awt.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

   protected List<EnqueteContainer> containerList;
   protected List<ClientInterface> clientList;
   protected List<Usuario> userList;
    protected List<Enquete> enqueteList;
   protected int userId;
   protected Util util = null;
   protected Enquete enquete = null;
   protected EnqueteContainer container = null;

   public ServerImpl() throws RemoteException {
       super();
       containerList = new ArrayList<>();
       clientList = new ArrayList<>();
       userList = new ArrayList<>();
       enqueteList = new ArrayList<>();
       util = new Util();
       userId = 0;
   }

  @Override
  public String sayHello() throws RemoteException {
    return "hello";
  }

    /**
     * Método usado pelos clients para registro / callback
     *
     * É feita a verificação se o cliente que fez a solicitação
     * já não está na lista.
     * Se não estiver é feita a inclusão e envia notificação para
     * os outros clientes.
     *
     * @param client
     * @throws RemoteException
     */
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

    /**
     * Método remoto que permite o cancelamento do cliente no callback
     *
     * @param client
     * @throws RemoteException
     */
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

    /**
     * Adicionar usuário no servidor.
     * O cliente precisa informar o nome para ser feito o vinculo
     * usuario x cliente
     *
     * A chave publica é gerada por um Randon
     *
     * @param name
     * @param client
     * @throws RemoteException
     */
    @Override
    public Usuario addUser(String name, ClientInterface client) throws RemoteException {
        Usuario user = new Usuario(userId++, name, (int) Math.random(), "Enquete", client);
        synchronized (userList) {
            userList.add(user);
            logServer();
        }
        return user;
    }

    /**
     * Remover um usuário do servidor
     *
     * @param user
     * @throws RemoteException
     */
    @Override
    public void removeUser(Usuario user) throws RemoteException {
        synchronized (userList) {
            userList.remove(user);
        }
    }

    /**
     * Método usado pelo usuário para adicionar uma enquete
     *
     * @param nome
     * @param titulo
     * @param local
     * @param tempo
     * @param dataFinalEnquete
     * @throws RemoteException
     */
    @Override
    public void addEnquete(String nome, String titulo, String local,
                           String tempo, String dataFinalEnquete, Usuario user) throws RemoteException {
        synchronized (enqueteList) {
            enquete = new Enquete(nome, titulo, local, tempo, dataFinalEnquete);
            enqueteList.add(enquete);

            container = new EnqueteContainer();
            container.setEnquete(enquete);
            container.getUsersList().add(user);
            containerList.add(container);
            
            logServer();
        }
    }



    private synchronized void doCallbacks( ) throws RemoteException {
       for (ClientInterface client : clientList) {
           client.notifyMe("Cliente Registrado. Usuário: " +  clientList.size());
       }
       System.out.println("********************************\n" + "Server completed callbacks ---");
    }

    /**
     * Atualizar o log do Servidor
     */
    private void logServer() {
        synchronized (userList) {
            util.cleanRefreshPrintServerHeader();
            for (Usuario user : userList) {
                System.out.println(util.TEXT_GREEN + "Id: " + util.TEXT_RESET + user.getId() +
                        util.TEXT_GREEN + "   Usuário: " + util.TEXT_RESET + user.getNome() +
                        util.TEXT_GREEN + " Chave Pública: " + util.TEXT_RESET +  user.getChavePublica() +
                        util.TEXT_GREEN + " Status: " +  util.TEXT_RESET + "Registrado/Online");
            }

            if (enqueteList.size() > 0) {
                System.out.println(" ");
                System.out.println(util.TEXT_GREEN + "Lista de Enquetes Registradas" + util.TEXT_RESET);
                System.out.println(" ");
                for (Enquete enquete : enqueteList) {
                    System.out.println(util.TEXT_GREEN + "Enquete: " + util.TEXT_RESET + enquete.getNome());
                }
            }
        }
    }
}
