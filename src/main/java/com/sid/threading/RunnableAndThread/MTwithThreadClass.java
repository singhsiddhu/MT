package com.sid.threading.RunnableAndThread;

/**
 * Hello world!
 *
 */

class DBHandle {
    private String myName;

    public String getMyName() {
        return myName;
    }

    public DBHandle(String name) {
        this.myName = name;
    }
}

public class MTwithThreadClass extends Thread {
    // All threads should create on 1 DBHandle
    private static volatile DBHandle dbHandle = null;

    public MTwithThreadClass(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        System.out.println("Thread " + this.getName());
        // Only 1 thread should set the Name of DBHandle
        dbHandle = getDBHandle();
        System.out.println("dbHanle's name is : " + dbHandle.getMyName());
    }

    public static DBHandle getDBHandle() {
        if (dbHandle == null) {
            synchronized (MTwithThreadClass.class) {
                if (dbHandle == null) {
                    // What does this 'new' operator does : i) operator new call ( malloc , memory
                    // allocation), ii ) constructor call
                    dbHandle = new DBHandle(Thread.currentThread().getName());
                }
            }
        }
        return dbHandle;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Starts...");
        final int SIZE = 10;
        // If DBHandle is instantiated here. it will be Single object...
        MTwithThreadClass arrayOfThread[] = new MTwithThreadClass[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            arrayOfThread[i] = new MTwithThreadClass("#" + i);
            arrayOfThread[i].start();
        }
        for (int i = 0; i < SIZE; ++i) {
            arrayOfThread[i].join();
        }
        System.out.println("Main ends...");
    }
}
