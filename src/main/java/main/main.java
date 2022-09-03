package main;

import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class main extends Application {


    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View(primaryStage);
        Controller controller = new Controller(view);
        view.handler(controller);


    }
}
