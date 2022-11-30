package ru.itis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        List<Socket> chatClients = new ArrayList<>();
        while(true) {
            Socket s = serverSocket.accept();
            System.out.println("поймал сокет");
            new Thread(new ServerChatProcessor(s, chatClients)).start();
        }
    }
}
