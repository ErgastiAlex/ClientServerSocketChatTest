package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientManagerImp implements ClientManager {

    private Server serverManager;
    private PrintWriter outputToClient;
    private Scanner inputFromClient;
    private InputStream inputStreamFromClient;

    private LinkedList<String> broadcastOutputFromOtherClient;

    public ClientManagerImp(Server ser, Socket client) throws IOException {
        serverManager=ser;
        broadcastOutputFromOtherClient=new LinkedList<>();

        initClientParameter(client);
    }

    private void initClientParameter(Socket client) throws IOException{
        inputStreamFromClient=client.getInputStream();
        this.inputFromClient=new Scanner(inputStreamFromClient);
        this.outputToClient =new PrintWriter(client.getOutputStream());
    }

    public synchronized void addBroadcastMessage(String s){
        broadcastOutputFromOtherClient.addLast(s);
    }

    @Override
    public void run() {
        try{
            while(true){
                sendBroadcastMessage();
                readInputFromClient();
            }
        }
        catch(Exception e){

        }
    }
    private void readInputFromClient() throws IOException {
        if(inputStreamFromClient.available()>0) {
            String line = inputFromClient.nextLine();
            serverManager.newBroadcastMessage(this, line);
        }
    }
    private synchronized void sendBroadcastMessage()
    {
        if(thereIsBroadcastMessage()) {
            outputToClient.println(broadcastOutputFromOtherClient.remove(0));
            outputToClient.flush();
        }
    }
    private synchronized boolean thereIsBroadcastMessage(){
        return broadcastOutputFromOtherClient.size()>0;
    }


}
