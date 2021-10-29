package com.app.sync;

class Counter implements Runnable{
    private int count;

    @Override
    public void run() {
        increment();
        decrement();
    }
    public synchronized void increment() {
        for (int i=0; i<10000; i++)
            count++;
    }

    public synchronized void decrement() {
        for (int i=0; i<10000; i++)
            count--;
    }

    public int getCount() {
        return count;
    }

}


public class SyncDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter);
        Thread t2 = new Thread(counter);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count " + counter.getCount());
    }
}
