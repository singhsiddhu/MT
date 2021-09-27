package com.sid.threading.RunnableAndThread;

import java.io.Console;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class MTwithRunnable implements Runnable {
    private int age;

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " and age : " + this.age);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Starts...");
        final int SIZE = 10;
        Thread arrayOfThread[] = new Thread[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            MTwithRunnable obj = new MTwithRunnable();
            obj.age = i*10;
            if ((i % 2) == 0)
                arrayOfThread[i] = new Thread(obj, "Ruchi" + i);
            else
                arrayOfThread[i] = new Thread(obj, "Mridul" + i);
            arrayOfThread[i].start();
        }
        for (int i = 0; i < SIZE; ++i) {
            arrayOfThread[i].join();
        }
        System.out.println("Main ends...");
    }
}
