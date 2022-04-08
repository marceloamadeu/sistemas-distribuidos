import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    public String ping(String message) throws RemoteException;    
    public String notifyChanges(String message) throws RemoteException;
}