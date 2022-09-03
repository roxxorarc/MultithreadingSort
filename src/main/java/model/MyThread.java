package model;

import java.util.concurrent.BlockingQueue;

public class MyThread extends Thread {

    private boolean isRunning;
    private final BlockingQueue<Runnable> tasks;


    public MyThread(BlockingQueue<Runnable> tasks, String name) {
        super(name);
        this.tasks = tasks;
        this.isRunning = false;
    }


    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                tasks.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void stopThread() {
        isRunning = false;
        interrupt();
    }

}
