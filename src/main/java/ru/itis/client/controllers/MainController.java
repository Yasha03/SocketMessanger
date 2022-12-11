package ru.itis.client.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ru.itis.client.ClientChatProcessor;
import ru.itis.protocol.message.Message;
import ru.itis.protocol.message.MessageTypes;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea TextAreaMessage;

    @FXML
    private ScrollPane panel_chat;

    @FXML
    private Button sendMessageButton;

    @FXML
    private ImageView stickerImage_1;

    @FXML
    private ImageView stickerImage_2;

    @FXML
    private ImageView stickerImage_3;

    private VBox chatContent;

    private ClientChatProcessor chatProcessor;

    @FXML
    void initialize() throws IOException {
        stickerImage_1.setOnMouseClicked(event -> {
            try {
                sendSticker((byte) 1);
            } catch (IOException e) {
                throw new IllegalArgumentException("can't send sticker");
            }
        });

        stickerImage_2.setOnMouseClicked(event -> {
            try {
                sendSticker((byte) 2);
            } catch (IOException e) {
                throw new IllegalArgumentException("can't send sticker");
            }
        });

        stickerImage_3.setOnMouseClicked(event -> {
            try {
                sendSticker((byte) 3);
            } catch (IOException e) {
                throw new IllegalArgumentException("can't send sticker");
            }
        });

        chatContent = new VBox();

        panel_chat.setContent(chatContent);


        Socket socket = new Socket("localhost", 8082);
        chatProcessor = new ClientChatProcessor(socket, this);
        new Thread(chatProcessor).start();

        sendMessageButton.setOnAction(event -> {
            try {
                chatProcessor.sendMessage(Message.builder()
                        .type(MessageTypes.DEFAULT_MESSAGE)
                        .data(TextAreaMessage.getText().trim().getBytes(StandardCharsets.UTF_16))
                        .build());
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't send message.");
            }
            TextAreaMessage.clear();
        });

    }

    private void sendSticker(byte stickerCode) throws IOException {
        chatProcessor.sendMessage(Message.builder()
                        .type(MessageTypes.STICKER_MESSAGE)
                        .data(new byte[]{stickerCode})
                .build());
    }

    public void onGetMessageListener(Message message){
        Platform.runLater(() -> {
            if(message.getType() == MessageTypes.DEFAULT_MESSAGE){
                Label labelMessage = new Label(message.toString());
                chatContent.getChildren().add(labelMessage);
            }

            if(message.getType() == MessageTypes.STICKER_MESSAGE){
                Image image1 = null;
                try {
                    image1 = new Image(new FileInputStream("src/main/resources/img/stickers/s1-" + message.getData()[0] + ".png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageView imageView1 = new ImageView(image1);
                imageView1.setFitHeight(200);
                imageView1.setFitWidth(200);

                chatContent.getChildren().add(imageView1);
            }
        });

    }

}

