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

    public DBHandle getDBHandle() {
        if (dbHandle == null) { 
            synchronized (MTwithThreadClass.class) {
                if (dbHandle == null) {
                    dbHandle = new DBHandle(Thread.currentThread().getName());
                }
            }
        }
        return dbHandle;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Starts...");
        final int SIZE = 10;
        
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
