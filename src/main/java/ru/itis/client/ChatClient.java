package ru.itis.client;

import ru.itis.protocol.message.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8082);
        ClientChatProcessor chatProcessor = new ClientChatProcessor(socket);
        new Thread(chatProcessor).start();
        while (true) {
            System.out.print("¬ведите сообщение: ");
            String message = sc.nextLine();
            chatProcessor.sendMessage(Message.builder()
                    .type((byte) 1)
                    .data(message.getBytes(StandardCharsets.UTF_16))
                    .build());
        }

    }
}
