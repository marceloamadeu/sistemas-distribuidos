import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {

    protected ClientImpl() throws RemoteException {
        super();        
    }

    @Override
    public String ping(String message) throws RemoteException {
        return "ping";
    }    

    @Override
    public String notifyChanges(String message){
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
     }      
    
}