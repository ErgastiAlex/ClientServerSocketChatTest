package Server;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Constant.Constant.*;
public class ServerImp implements Server{

    private ExecutorService clientThreadPool;
    private ArrayList<ClientManager> clientList;
    private ServerSocket server;
    private int port;

    public ServerImp(){
        this(DEFAULT_PORT);
    }
    public ServerImp(int port){
        this.port=port;
        clientList=new ArrayList<>();
        clientThreadPool= Executors.newCachedThreadPool();
    }

    public void startServer(){
        try{
            createServer();
            acceptClientToServer();
        }
        catch (Exception e){
            closeServer();
        }
    }

    public void closeServer(){
        clientThreadPool.shutdown();
        try{
            server.close();
        }
        catch (IOException e){
        }

    }

    private void createServer() throws IOException {
        server=new ServerSocket(port);
    }

    private void acceptClientToServer()throws IOException{
        while(true){
            Socket newClient=server.accept();

            ClientManager newClientManager=new ClientManagerImp(this,newClient);
            addClientToList(newClientManager);
            clientThreadPool.submit(newClientManager);
        }
    }

    private synchronized void addClientToList(ClientManager client){
        clientList.add(client);
    }

    @Override
    public synchronized void removeClientFromList(ClientManager client) {
        try{
            clientList.remove(client);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public synchronized void newBroadcastMessage(ClientManager sender,String s) {
        System.out.println("Received new message "+s);
        for(ClientManager c:clientList){
            if(sender!=c){
                c.addBroadcastMessage(s);
            }

        }
    }
}
