package com.example.aihw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class HelloApplication extends Application {
    private static Stage stage;
    private static  MediaPlayer mediaPlayer;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = primaryStage;
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public Stage getStage(){
        return stage;
    }

    public void music(String mp3File){
        try {
            Media media = new Media(Paths.get(mp3File).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
        catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR,"Error cannot Play Music", ButtonType.CLOSE);
            error.setHeaderText("Error cannot Play Music");
            error.setContentText(e.getMessage());
            error.show();
            e.printStackTrace();
        }
    }

    public void stopMusic(){
        try {
            mediaPlayer.stop();
        }
        catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR,"Error cannot Play Music", ButtonType.CLOSE);
            error.setHeaderText("Error cannot Stop Music");
            error.setContentText(e.getMessage());
            error.show();
            e.printStackTrace();
        }
    }

    public void changeScene(String fxml){

        try {
            Parent node = FXMLLoader.load(getClass().getResource(fxml));
            stage.getScene().setRoot(node);
            stage.sizeToScene();

        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,"Error cannot Change Scene", ButtonType.CLOSE);
            error.setHeaderText("Error cannot Change Scene");
            error.setContentText(e.getMessage());
            error.show();
//            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}