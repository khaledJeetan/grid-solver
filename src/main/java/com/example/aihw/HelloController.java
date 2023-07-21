package com.example.aihw;

import javafx.fxml.FXML;


public class HelloController {
    private HelloApplication app = new HelloApplication();

    public void initialize(){
        app.music("C:\\Users\\user\\IdeaProjects\\AI-HW\\src\\main\\resources\\start-music.mp3");

    }

    @FXML
    protected void onSolverButtonClick() {
        app.changeScene("solve-grid.fxml");
    }

    @FXML
    protected void onGenerateButtonClick() {
       app.changeScene("generate-grid.fxml");
    }

    @FXML
    protected void onExitButtonClick() {
        app.getStage().close();
    }


}