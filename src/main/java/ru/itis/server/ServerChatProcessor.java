package ru.itis.server;

import ru.itis.protocol.exceptions.MessageTypeException;
import ru.itis.protocol.exceptions.ProtocolHeaderException;
import ru.itis.protocol.message.Message;
import ru.itis.protocol.message.MessageInputStream;
import ru.itis.protocol.message.MessageOutputStream;
import ru.itis.protocol.message.MessageTypes;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServerChatProcessor implements Runnable {

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

    public void startChat() {
        try {
            Message message;
            while ((message = inputStream.getMessage()) != null) {
                System.out.println(socket.getPort() + ": " + message);
                for (Socket client : chatClients) {
                    MessageOutputStream toClientWriter = new MessageOutputStream(client.getOutputStream());
                    if (message.getType() == MessageTypes.DEFAULT_MESSAGE) {
                        toClientWriter.writeMessage(Message.builder()
                                .type(message.getType())
                                .data((socket.getPort() + ": "
                                        + message.toString()).getBytes(StandardCharsets.UTF_16))
                                .build());
                    } else {
                        toClientWriter.writeMessage(message);
                    }
                    toClientWriter.flush();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        } catch (ProtocolHeaderException e) {
            throw new IllegalArgumentException();
        } catch (MessageTypeException e) {
            throw new IllegalArgumentException();
        }

    }
}
