package ru.itis.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChatProcessor implements Runnable {

    private Socket socket;
    private PrintWriter toServerWriter;
    private BufferedReader fromServerReader;

    public ClientChatProcessor(Socket socket) {
        this.socket = socket;
        try {
            this.toServerWriter = new PrintWriter(socket.getOutputStream());
            this.fromServerReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void run() {
        try(BufferedReader clientReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        )){
            String clientMessage;
            while ( (clientMessage = clientReader.readLine()) != null ){
                System.out.println("\n\r" + clientMessage);
            }
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }
        /*while (true) {
            try {
                String messageServer = fromServerReader.readLine();
                if(messageServer != null){
                    System.out.println("Пришло сообщение: " + messageServer);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }*/
    }

    public void sendMessage(String message) throws IOException {
        toServerWriter.println(message);
        toServerWriter.flush();
    }
}
