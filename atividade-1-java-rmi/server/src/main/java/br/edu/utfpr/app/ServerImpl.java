package br.edu.utfpr.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import br.edu.utfpr.app.entity.Survey;
import br.edu.utfpr.app.entity.SurveyVote;
import br.edu.utfpr.app.entity.User;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private Vector userList;
    private List<User> users = new ArrayList<>();
    private List<Survey> surveys = new ArrayList<>();
    private List<SurveyVote> surveyVotes = new ArrayList<>();


    protected ServerImpl() throws RemoteException {
        super();
        userList = new Vector();
    }

    @Override
    public String sayHello() throws RemoteException {
      return("hello");
    }

    @Override
    public void addUser(User user) throws RemoteException {
        getUsers().add(user);
        System.out.println("Registered new user: " + user.getName());
    }

    @Override
    public void addSurvey(Survey survey) throws RemoteException {        
        getSurveys().add(survey);
        System.out.println("Registered new survey: " + survey.getName());
    }

    @Override
    public void addSurveyVote(int user, int survey, int vote, boolean subscriber) throws RemoteException {
        getSurveyVotes().add(new SurveyVote(user, survey, vote, subscriber));        
        System.out.println("Vote Registered successfully!!!");
    }

    @Override
    public Survey getSurvey(int survey) throws RemoteException {
        Optional<Survey> findSurvey = getSurveys().stream().filter(s -> s.getId() == survey).findFirst();
        if (findSurvey == null)
            return null;
        return findSurvey.get();
    }

    @Override
    public String notifyUsers(String msg) throws RemoteException {
        String returnMessage = "Call back received: " + msg;
        System.out.println(returnMessage);
        return returnMessage;        
    }
 

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Survey> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public List<SurveyVote> getSurveyVotes() {
        return this.surveyVotes;
    }

    public void setSurveyVotes(List<SurveyVote> surveyVotes) {
        this.surveyVotes = surveyVotes;
    }

    @Override
    public void registerForCallback(ClientInterface clientObject) throws RemoteException {
              
        if (!(userList.contains(clientObject))) {
            userList.addElement(clientObject);
            System.out.println("Registered new client ");
            doCallbacks();
        } // end if
        
    }

    @Override
    public void unregisterForCallback(ClientInterface clientObject) throws RemoteException {
        if (userList.removeElement(clientObject)) {
            System.out.println("Unregistered client ");
          } else {
             System.out.println(
               "unregister: clientwasn't registered.");
          }
        
    }

    private synchronized void doCallbacks( ) throws java.rmi.RemoteException{
        // make callback to each registered client
        System.out.println(
           "**************************************\n"
            + "Callbacks initiated ---");
        for (int i = 0; i < userList.size(); i++){
          System.out.println("doing "+ i +"-th callback\n");    
          // convert the vector object to a callback object
          ClientInterface nextClient = (ClientInterface)userList.elementAt(i);
          // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " +  userList.size());
        }// end for
        System.out.println("********************************\n" +
                           "Server completed callbacks ---");
      } // doCallbacks

    
}


