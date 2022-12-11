package ru.itis.client;

import ru.itis.client.controllers.MainController;
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
    private MainController controller;

    public ClientChatProcessor(Socket socket, MainController controller) {
        this.socket = socket;
        try {
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
            this.inputStream = new MessageInputStream(socket.getInputStream());
            this.controller = controller;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void run() {
        try {
            Message message;
            while ((message = inputStream.getMessage()) != null) {
                controller.onGetMessageListener(message);
            }
        } catch (IOException | ProtocolHeaderException | MessageTypeException e) {
                e.printStackTrace();
            }
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeMessage(message);
        outputStream.flush();
    }
}
