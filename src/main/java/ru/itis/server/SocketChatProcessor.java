package ru.itis.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketChatProcessor implements Runnable{

    private Socket socket;
    private InputStream inputStream;
    private List<Socket> chatClients;

    public SocketChatProcessor(Socket socket, List<Socket> chatClients) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        chatClients.add(socket);
        this.chatClients = chatClients;
    }

    @Override
    public void run() {
        startChat();
//        try {
//            readMessage();
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
    }

    public void startChat() {
        try(BufferedReader clientReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        )){
            String clientMessage;
            while ( (clientMessage = clientReader.readLine()) != null ){
                System.out.println(socket.getPort() + ": " + clientMessage);
                for (Socket client : chatClients){
                    PrintWriter toClientWriter = new PrintWriter(client.getOutputStream());
                    toClientWriter.println("Пользователь " + socket.getPort() + " написал сообщение: " + clientMessage);
                    toClientWriter.flush();
                }
            }
        }catch (IOException e){
            throw new IllegalArgumentException();
        }
    }


    private void readMessage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while(true) {
            String s = br.readLine();
            System.out.println(": " + s);
            if(s == null || s.trim().length() == 0) {
                break;
            }
        }
    }
}
