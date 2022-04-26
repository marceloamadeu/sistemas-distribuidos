package br.edu.utfpr.interfaces;

import br.edu.utfpr.entity.Enquete;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is a remote interface for illustrating RMI 
 * client callback.
 * @author M. L. Liu
 */

public interface ClientInterface extends Remote {


    public String notifyMe(String message)  throws java.rmi.RemoteException;

    /**
     * Se inscrever / acompanhar Enquete
     *
     * @param enquete
     * @throws RemoteException
     */
    public void subscribe(Enquete enquete) throws RemoteException;

    /**
     * Enviar notificação os usuários inscritos na enquete
     *
     * @param enquete
     * @throws RemoteException
     */
    public void notify(Enquete enquete) throws RemoteException;

}
