package Server;

public class Main {
    public static void main(String[] args){
        System.out.println("Opening server");
        new ServerImp().startServer();
        System.out.println("Server closed");

    }
}
