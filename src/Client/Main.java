package Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Connection to the server");
        new ClientImp().startClient();
    }
}
