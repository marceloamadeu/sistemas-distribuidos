package br.edu.utfpr.app;


/**
 * Hello world!
 *
 */
public class MainClientApp implements RemoteClient {


    public static final String SERVER_NAME = "localhost";
    public static final int SERVER_PORT = 33600;

    
    

    

    
    
    

    

    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    

    
    

    
    
    
            /**
     * Menu com as opções disponíveis para teste
     * @param options
     */
    /*
    public static void printMenu(String[] options){
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
    */


    /**
     * Cadastro usuário:
     * o Ao acessar o sistema pela primeira vez, cada cliente deve
     * informar seu nome, chave pública e sua referência de objeto
     * remoto. Nesse cadastro, o cliente automaticamente atuará
     * como subscriber, registrando interesse em receber
     * notificações do servidor quando uma nova enquete for
     * cadastrada.
     * @param user
     */
    /*
    public static void addUser(ServerInterface server, User user) {
        try {
            //server.addUser(user);
        } catch (RemoteException e) {
            System.out.println("User registration error: " + e.toString());
        }
    }
    */


    /**
     * 
     * @param server
     * @param survey
     */
    /*
    public static void addSurvey(ServerInterface server, Survey survey) {
        try {
            //server.addSurvey(survey);
        } catch (RemoteException e) {
            System.out.println("Survey registration error: " + e.toString());
        }
    }

    public static Survey findSurvey(ServerInterface server, String name, String title) {
        try {
            //return server.getSurvey(name, title);
        } catch (RemoteException e) {
            System.out.println("Survey registration error: " + e.toString());
        }
        return null;
    }

    public static void main(String args[]) {
        try {          

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);



            System.out.println("...................................................................." );
            System.out.println("........................ CallBack Client ..........................." );
            System.out.println("...................................................................." );
            System.out.println(" " );
            
            Registry registry = LocateRegistry.getRegistry(SERVER_NAME);
            String url = "rmi://" + SERVER_NAME + ":" + SERVER_PORT + "/callback";
            ServerInterface server = (ServerInterface) Naming.lookup(url);
            System.out.println("Lookup...............................: Completed " );
            Naming.rebind(url, server);
            
            String response = server.sayHello();
            System.out.println("Server response......................: " + response);

            ClientInterface callbackClientObj = new ClientImpl();
            System.out.println("Callback Client Obj..................: Created");

            server.registerForCallback(callbackClientObj);
            System.out.println("Client Registered for callback.......: Ready");
*/
            
            /**
             * Cadastro do Usuário
             */

             /*
            System.out.println(" ");
            User user = new User();

            System.out.println("Nome: ");
            user.setName(bufferedReader.readLine());

            System.out.println("Public Key: ");
            user.setPublicKey(bufferedReader.readLine());

            System.out.println("Remote Object Reference: ");
            user.setRemoteObjectReference(bufferedReader.readLine());           

            addUser(server, user);
            */
            //***************************************/


            /*

            String[] options = {
                    "1 - Cadastro de Enquete",
                    "2 - Cadastro de Voto em uma Enquete ",
                    "3 - Consulta de Enquete",
                    "9- Exit",
            };


            Scanner scanner = new Scanner(System.in);
            int option = 1;
            while (option != 9) {
                printMenu(options);
                try {
                    option = scanner.nextInt();

                    if (option == 1) {
                        Survey survey = new Survey();

                        System.out.println("Nome: ");
                        survey.setName(bufferedReader.readLine());
                            
                        System.out.println("Titulo: ");
                        survey.setTitle(bufferedReader.readLine());

                        System.out.println("Local do Evento: ");
                        survey.setLocal(bufferedReader.readLine());

                        System.out.println("Proposta de Tempo (data e hora - 99/99/9999 12:00): ");
                        survey.setDateTime(bufferedReader.readLine());

                        System.out.println("Data Limite Respostas (data e hora - 99/99/9999 12:00): ");
                        survey.setFinalDate(bufferedReader.readLine());

                        addSurvey(server, survey);
                        
                    }



                    if (option == 3) {
                        
                        System.out.println("Nome: ");
                        String name = bufferedReader.readLine();
                            
                        System.out.println("Titulo: ");
                        String title = bufferedReader.readLine();

                        
                        Survey find = findSurvey(server, name, title);

                        if (find == null) {
                            System.out.println("Enquete não encontrada!!!");
                        } else {
                            System.out.println("Nome: " + find.getName());
                            System.out.println("Titulo: " + find.getTitle());
                            System.out.println("Local do Evento: " + find.getLocal());
                            System.out.println("Proposta de Tempo: " + find.getDateTime());
                            System.out.println("Data Limite Respostas: " + find.getFinalDate());
                        }
                        
                    }
                } catch (InputMismatchException ex){
                      System.out.println("Please enter an integer value between 1 and " + options.length);
                      scanner.next();
                } catch (Exception ex) {
                    System.out.println("An unexpected error happened. Please try again");
                    scanner.next();
                }
            }

            server.unregisterForCallback(callbackClientObj);
            System.out.println("Client Unregistered for callback");
            scanner.close();
                        
        } catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);            
        } 
      } 
      */
      
}
