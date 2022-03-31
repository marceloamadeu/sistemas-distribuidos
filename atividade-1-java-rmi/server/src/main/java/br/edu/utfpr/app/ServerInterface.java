package br.edu.utfpr.app;

import java.rmi.Remote;
import java.rmi.RemoteException;


import br.edu.utfpr.app.entity.Survey;
import br.edu.utfpr.app.entity.User;

public interface ServerInterface extends Remote {


    //Somente os metodos disponiveis para o cliente.
    public void addUser(User user) throws RemoteException;    
    public void addSurvey(Survey survey) throws RemoteException;  
    public void addSurveyVote(int user, int survey, int vote, boolean subscriber) throws RemoteException;   
    public Survey getSurvey(String name, String title) throws RemoteException;   
    public String notifyUsers(String msg) throws RemoteException;
    public void registerForCallback(ClientInterface clientObject) throws java.rmi.RemoteException;
    public void unregisterForCallback(ClientInterface clientObject) throws java.rmi.RemoteException;
    public String sayHello() throws java.rmi.RemoteException;


}
