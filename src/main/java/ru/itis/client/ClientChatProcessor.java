package ru.itis.client;

import ru.itis.protocol.exceptions.MessageTypeException;
import ru.itis.protocol.exceptions.ProtocolHeaderException;
import ru.itis.protocol.message.Message;
import ru.itis.protocol.message.MessageInputStream;
import ru.itis.protocol.message.MessageOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChatProcessor implements Runnable {

    private Socket socket;
    private MessageOutputStream outputStream;
    private MessageInputStream inputStream;

    public ClientChatProcessor(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
            this.inputStream = new MessageInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void run() {
//            try {
//                Message clientMessage;
//                while (inputStream.available() != 0){
//                    System.out.println("поймал сообщение");
//                    clientMessage = inputStream.getMessage();
//                    System.out.println("Пришло сообщение: " + clientMessage);
//                }
//            } catch (IOException | ProtocolHeaderException | MessageTypeException e) {
//                e.printStackTrace();
//            }
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeMessage(message);
        outputStream.flush();
    }
}
