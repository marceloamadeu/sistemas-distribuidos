import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * A Classe AppaServer tem um método main que cria uma instância de ServerImpl
 * (objeto Remoto), e em seguida, vincula essa instância a um Registry.
 * 
 * O Java RMI Registry é um serviço de nomes simples que permite que os clientes
 * obtenham uma referência (stub / skeleton). Em geral, um registro é usado (se 
 * for o caso) apenas para localizar o primeiro objeto remoto que um cliente 
 * precisa usar.
 */
public class MainServerApp implements RemoteLogin {

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




    static Map<String, RemoteClient> clients = new HashMap<>();

    @Override
    public RemoteConnection login(String name, RemoteClient client) {

        Connection connection = new Connection(name, client);
        clients.put(name, client);
        System.out.println(name + " logged in");
        return connection;
    }

    public static void main(String[] args) {

        try {
            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            LoginServer server = new LoginServer();
            UnicastRemoteObject.exportObject(server, Registry.REGISTRY_PORT);
            reg.rebind("LoginServerName", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) {
        try {            

            Server server = new ServerImpl();            
            
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(SERVER_PORT);            
            System.out.println(TEXT_GREEN + "[+] " + TEXT_RESET + "java RMI registry created");

            String url = "rmi://" + SERVER_NAME + ":" + SERVER_PORT + "/call";
*/
            /**
             * Naming.rebind - Este método é usado por um servidor
             * para registrar o identificador de um objeto remoto pelo nome
             */

             /*
            registry.rebind(url, server);

            System.out.println(TEXT_GREEN + "[+] " + TEXT_RESET + "Server is ready at " + SERVER_NAME + ":" + SERVER_PORT);            
        } catch (RemoteException e) {            
            System.out.println(TEXT_RED + "[-] " + TEXT_RESET + "RemoteException in MainServerApp.main: " + e.toString());                   
        }             
    }   
    */
    
}


/*
    private static void doRegistry(int serverPort) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.list();        
        } catch (RemoteException e) {             
            Registry registry = LocateRegistry.createRegistry(serverPort);
        }
    }
*/
