package ru.itis.server;

import ru.itis.protocol.exceptions.MessageTypeException;
import ru.itis.protocol.exceptions.ProtocolHeaderException;
import ru.itis.protocol.message.Message;
import ru.itis.protocol.message.MessageInputStream;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerChatProcessor implements Runnable{

    private final Socket socket;
    private final MessageInputStream inputStream;
    private final List<Socket> chatClients;

    public ServerChatProcessor(Socket socket, List<Socket> chatClients) throws IOException {
        this.socket = socket;
        this.inputStream = new MessageInputStream(socket.getInputStream());
        chatClients.add(socket);
        this.chatClients = chatClients;
    }

    @Override
    public void run() {
        startChat();
    }

    public void startChat(){
        try {
            Message message;
            while ( (message = inputStream.getMessage()) != null){
                System.out.println(socket.getPort() + ": " + message);
            }

//            System.out.println(socket.getPort() + ": " + message);
//            System.out.println("type: " + message.getType());
//            for (Socket client : chatClients){
//                PrintWriter toClientWriter = new PrintWriter(client.getOutputStream());
//                toClientWriter.println("Пользователь " + socket.getPort() + " написал сообщение: " + message);
//                toClientWriter.flush();
//            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        } catch (ProtocolHeaderException e) {
            throw new IllegalArgumentException();
        } catch (MessageTypeException e) {
            throw new IllegalArgumentException();
        }

    }
}
