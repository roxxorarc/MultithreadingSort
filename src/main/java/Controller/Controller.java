package Controller;

import model.MyRunnable;
import model.Pool;
import util.Algorithms;
import util.Enums;
import view.View;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final View view;


    public Controller(View view) {
        this.view = view;
    }


    public void start() {
        view.resetProgress();
        LocalDateTime starting = LocalDateTime.now();

        Enums.Sorts sort = view.getSort();
        long nb = view.getConfiguration();
        int nbThreads = view.getNbThreads();

        List<int[]> arrays = Algorithms.generateArrays(nb);

        List<Runnable> tasksRunnable = new ArrayList<>();
        arrays.forEach((array) -> {
            MyRunnable runnable = new MyRunnable(array, sort);
            runnable.addObserver(view);
            tasksRunnable.add(runnable);
        });

        Pool pool = new Pool(nbThreads, tasksRunnable);
        pool.start();
        pool.startTasks();
        view.setLeftStatus("Active Threads : " + Thread.activeCount());
        pool.stop();


        LocalDateTime ending = LocalDateTime.now();
        long duration = Duration.between(starting, ending).toMillis();
        view.setRightStatus("Last run | "
                + "Start : " + starting.format(DateTimeFormatter.ISO_LOCAL_TIME)
                + " - End : " + ending.format(DateTimeFormatter.ISO_LOCAL_TIME)
                + " - duration : " + duration + " ms"
        );

    }
}
