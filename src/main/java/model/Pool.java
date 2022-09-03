package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Pool {


    private boolean started;

    private final List<Runnable> tasks;

    private final BlockingQueue<Runnable> tasksQueue;

    private final List<MyThread> myThreads = new ArrayList<>();

    public Pool(int nb, List<Runnable> tasks) {
        started = false;
        this.tasks = tasks;
        this.tasksQueue = new ArrayBlockingQueue<>(tasks.size());
        IntStream.range(0, nb).mapToObj(i -> new MyThread(tasksQueue, String.valueOf(i))).forEach(myThreads::add);
    }


    public synchronized void start() {
        if (started) throw new IllegalStateException("Already started");
        this.started = true;
        myThreads.forEach(Thread::start);
    }


    public synchronized void startTasks() {
        if (!started) throw new IllegalStateException("Not started");
        tasks.forEach(tasksQueue::offer);

    }


    public synchronized void stop() {
        if (!started) throw new IllegalStateException("Not started");
        while (!tasksQueue.isEmpty()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.started = false;
        myThreads.forEach(MyThread::stopThread);
    }


}
