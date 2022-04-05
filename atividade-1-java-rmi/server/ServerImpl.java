import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {



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
    //private Vector userList;   
    
    
    //private List<Survey> surveys = new ArrayList<>();
    //private List<SurveyVote> surveyVotes = new ArrayList<>();

    private List<User> users = new ArrayList<>();
    private List<String> serverInfoList = new ArrayList<>();


    protected ServerImpl() throws RemoteException {
        super();
        //userList = new Vector();
        cleanRefreshPrintHeader();
        serverInfoList.add(TEXT_GREEN + "[+] " + TEXT_RESET + "java RMI registry created");
        serverInfoList.add(TEXT_GREEN + "[+] " + TEXT_RESET + "Server is ready");
    }

    @Override
    public String sendPing() throws RemoteException {
      return("ping");
    }

    @Override
    public void register(User user) throws RemoteException {        
        users.add(user);      
        doCallbacks();
        System.out.println("Registered new client. User name: " + user.getName());
    }

    @Override
    public void unregister(User user) throws RemoteException {        
        users.remove(user);              
        doCallbacks();
        System.out.println("Unregistered client. User name: " + user.getName());                
    }

    private synchronized void doCallbacks() throws java.rmi.RemoteException{
        // make callback to each registered client
        
        for (User user : users) {
                        
            //.notifyMe("Number of registered clients = " +  userList.size());                            
        }                                  
      } 

    /**
     * Clean, refresh and print the console
     */
    public void cleanRefreshPrintHeader() {
        // Limpa o console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Header
        System.out.println("#####################################################");
        System.out.println("####                  SERVIDOR                   ####");
        System.out.println("#####################################################");
        System.out.println("");
    }

    /*
    @Override
    public void registerForCallback(ClientInterface clientObject) throws RemoteException {              
        if (!(userList.contains(clientObject))) {
            userList.addElement(clientObject);            
            System.out.println("Registered new client for callback...: Ready");
            doCallbacks();
        }        
    }

    @Override
    public void unregisterForCallback(ClientInterface clientObject) throws RemoteException {
        if (userList.removeElement(clientObject)) {
            System.out.println("Unregistered client ");
          } else {
             System.out.println(
               "unregister: client wasn't registered.");
          }
        
    }

    private synchronized void doCallbacks( ) throws java.rmi.RemoteException{
        // make callback to each registered client
        if (userList.size() > 0)
            System.out.println("**************************************");        
        
        for (int i = 0; i < userList.size(); i++){
                        
            // convert the vector object to a callback object
            ClientInterface nextClient = (ClientInterface) userList.elementAt(i);        
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " +  userList.size());                            
        }
                                
        if (userList.size() > 0)
            System.out.println("**************************************");                           
      } 
      */

    /*

    @Override
    public void addUser(User user) throws RemoteException {
        userList.addElement(user);        
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
    public Survey getSurvey(String name, String title) throws RemoteException {
        List<Survey> findSurvey = 
            getSurveys().stream().filter(s -> s.getTitle().contains(title) 
                || s.getName().contains(name) 
            ).collect(Collectors.toList());
        if (findSurvey == null)
            return null;
        return findSurvey.get(0);
    }

    @Override
    public String notifyUsers(String msg) throws RemoteException {
        String returnMessage = "Call back received: " + msg;
        System.out.println(returnMessage);
        return returnMessage;        
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

    

    private synchronized void doCallbacks( ) throws java.rmi.RemoteException{
        // make callback to each registered client
        if (userList.size() > 0)
            System.out.println("**************************************");        
        
        for (int i = 0; i < userList.size(); i++){
                        
            // convert the vector object to a callback object
            ClientInterface nextClient = (ClientInterface) userList.elementAt(i);        
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " +  userList.size());                            
        }
                                
        if (userList.size() > 0)
            System.out.println("**************************************");                           
      } 

      */
}


