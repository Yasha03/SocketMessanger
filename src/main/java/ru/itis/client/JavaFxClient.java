package ru.itis.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;

public class JavaFxClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL url = new File("src/main/resources/chat-fxml/main.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Advanced telegram");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
