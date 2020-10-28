package Server;

import java.net.Socket;

public interface Server {
    void startServer();
    void closeServer();
    void removeClientFromList(ClientManager client);
    void newBroadcastMessage(ClientManager sender,String s);
}
