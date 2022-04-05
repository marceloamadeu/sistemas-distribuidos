import java.rmi.Remote;

/**
 * First of all, let's create the interface for the remote object. 
 * This interface extends the java.rmi.Remote marker interface. 
 * In addition, each method declared in the interface throws the 
 * java.rmi.RemoteException
 */
public interface Server extends Remote {


    //Somente os metodos disponiveis para o cliente.
    // public void addUser(User user) throws RemoteException;    
    // public void addSurvey(Survey survey) throws RemoteException;  
    // public void addSurveyVote(int user, int survey, int vote, boolean subscriber) throws RemoteException;   
    // public Survey getSurvey(String name, String title) throws RemoteException;   
    // public String notifyUsers(String msg) throws RemoteException;
 
    
    public String sendPing() throws java.rmi.RemoteException;
    public void register(User user) throws java.rmi.RemoteException;
    public void unregister(User user) throws java.rmi.RemoteException;

}
