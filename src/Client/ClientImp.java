package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Constant.Constant.*;

public class ClientImp {
    private static int DEFAULT_PORT=8000;
    private Scanner scannerConsole=new Scanner(System.in);
    private Socket client;
    private PrintWriter outputToServer;
    private Scanner inputFromServer;
    private InputStream inputStreamFromServer;
    private String host;
    private int port;

    public ClientImp(){
        this(DEFAULT_HOST,DEFAULT_PORT);
    }
    public ClientImp(int port){
        this(DEFAULT_HOST,port);
    }
    public ClientImp(String host,int port){
        this.host=host;
        this.port=port;
    }


    public void startClient() throws IOException {
        initClientParameter();
        runServerCommunication();
    }
    private void initClientParameter() throws IOException {
        client=new Socket(host,port);
        inputStreamFromServer=client.getInputStream();
        this.inputFromServer=new Scanner(inputStreamFromServer);
        this.outputToServer=new PrintWriter(client.getOutputStream());
    }
    private void runServerCommunication() throws IOException {
        ExecutorService executors= Executors.newFixedThreadPool(2);
        while(true){
            readInputFromServer();
            writeInputToServer();
        }
    }
    private void readInputFromServer() throws IOException {
        if(inputStreamFromServer.available()>0){
            String line = inputFromServer.nextLine();
            System.out.println(line);
        }


    }
    private void writeInputToServer() throws IOException {
        if(System.in.available()>0) {
            outputToServer.println(scannerConsole.nextLine());
            outputToServer.flush();
        }

    }
}
