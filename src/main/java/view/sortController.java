package view;

import Controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.MyRunnable;
import util.Enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class sortController {

    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    private MenuItem quitItem;
    @FXML
    private TableView<MyRunnable> table;
    @FXML
    private TableColumn<MyRunnable, String> nameCol;
    @FXML
    private TableColumn<MyRunnable, Integer> sizeCol;
    @FXML
    private TableColumn<MyRunnable, Long> durationCol, swapCol;
    @FXML
    private LineChart<Number, Number> chart;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ChoiceBox<Enums.Sorts> sortChoice;
    @FXML
    private ChoiceBox<Long> configurationChoice;
    @FXML
    private Spinner<Integer> threadSpinner;
    @FXML
    private Button startButton, clearButton;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;

    private List<XYChart.Series<Number, Number>> series;

    public void initialize() {
        sortChoice.getItems().addAll(Enums.Sorts.values());

        Arrays.stream(Enums.Configuration.values()).toList().forEach(e -> configurationChoice.getItems().add(e.getNumber()));

        threadSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("usedSort"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        swapCol.setCellValueFactory(new PropertyValueFactory<>("operations"));

        series = Stream
                .generate(XYChart.Series<Number, Number>::new)
                .limit(Enums.Sorts.values().length)
                .collect(Collectors.toList());

        for (int i = 0; i < Enums.Sorts.values().length; i++) {
            series.get(i).setName(Enums.Sorts.values()[i].name());
        }


        chart.getData().addAll(series);



        configurationChoice.getSelectionModel().selectFirst();
        sortChoice.getSelectionModel().selectFirst();

        leftStatus.setText("Active threads : " + Thread.activeCount());
        rightStatus.setText("Last run | Never");

    }

    public void quit(ActionEvent e) {
        Platform.exit();
    }


    public Enums.Sorts getSort() {
        return sortChoice.getValue();
    }


    public long getConfiguration() {
        return configurationChoice.getValue();
    }

    public int getNbThreads() {
        return threadSpinner.getValue();
    }

    public void setLeftStatus(String string) {
        leftStatus.setText(string);
    }

    public void setRightStatus(String string) {
        rightStatus.setText(string);
    }


    public void setButtons(boolean state) {
        startButton.setDisable(state);
        clearButton.setDisable(state);
    }

    public void clear() {
        table.getItems().clear();
        leftStatus.setText("Active threads : " + Thread.activeCount());
        series.forEach(seriesElem -> seriesElem.getData().clear());
        resetProgress();

    }

    public void setData(MyRunnable runnable) {
        switch (runnable.getUsedSort()) {
            case Bubble -> series.get(0).getData().add(new XYChart.Data<>(runnable.getSize(), runnable.getOperations()));
            case Merge -> series.get(1).getData().add(new XYChart.Data<>(runnable.getSize(), runnable.getOperations()));
            case Insert -> series.get(2).getData().add(new XYChart.Data<>(runnable.getSize(), runnable.getOperations()));

        }

    }

    public void resetProgress() {
        progressBar.setProgress(0);
    }

    public void update(MyRunnable myRunnable) {
        Platform.runLater(() -> {
            System.out.println(myRunnable.getUsedSort() + " "
                    + myRunnable.getSize() + " "
                    + myRunnable.getOperations() + " "
                    + myRunnable.getDuration());
            table.getItems().add(myRunnable);
            setData(myRunnable);
            progressBar.setProgress(progressBar.getProgress() + 0.11);
        });
    }

    public void handler(Controller controller) {
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.start());
    }

}
