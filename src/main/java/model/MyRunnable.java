package model;

import util.Algorithms;
import util.Enums;
import util.Observable;

import java.time.Duration;
import java.time.LocalDateTime;

public class MyRunnable extends Observable implements Runnable {


    private final Enums.Sorts usedSort;
    private final int[] array;
    private final int size;
    private long operations = 0;
    private long duration;


    public MyRunnable(int[] array, Enums.Sorts usedSort) {
        this.array = array;
        this.size = array.length;
        this.usedSort = usedSort;
    }

    @Override
    public void run() {
        LocalDateTime beginning = LocalDateTime.now();
        operations = switch (usedSort) {
            case Merge -> Algorithms.mergeSort(array, size);
            case Bubble -> Algorithms.bubbleSort(array);
            case Insert -> Algorithms.insertSort(array);
        };
        LocalDateTime end = LocalDateTime.now();
        duration = Duration.between(beginning, end).toMillis();
        notifyObservers();
    }


    public Enums.Sorts getUsedSort() {
        return usedSort;
    }


    public int getSize() {
        return size;
    }

    public long getOperations() {
        return operations;
    }

    public long getDuration() {
        return duration;
    }

}
