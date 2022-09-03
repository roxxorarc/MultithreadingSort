package view;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.MyRunnable;
import util.Enums;
import util.Observable;
import util.Observer;

import java.io.IOException;

public class View implements Observer {


    private final sortController sortController;


    public View(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sort.fxml"));
        Parent root = loader.load();
        sortController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("Sorting threads");
        stage.show();


    }


    @Override
    public synchronized void update(Observable observable, Object arg) {
        MyRunnable myRunnable = (MyRunnable) observable;
        sortController.update(myRunnable);
    }

    public void clear() {
        sortController.clear();
    }

    public void setButtons(boolean state) {
        sortController.setButtons(state);
    }

    public void resetProgress() {
        sortController.resetProgress();
    }

    public Enums.Sorts getSort() {
        return sortController.getSort();
    }

    public long getConfiguration() {
        return sortController.getConfiguration();
    }

    public int getNbThreads() {
        return sortController.getNbThreads();
    }

    public void setLeftStatus(String string) {
        sortController.setLeftStatus(string);
    }

    public void setRightStatus(String string) {
        sortController.setRightStatus(string);
    }

    public void handler(Controller controller) {
        sortController.handler(controller);
    }


}
