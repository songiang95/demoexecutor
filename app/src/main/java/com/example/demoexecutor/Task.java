package com.example.demoexecutor;

import android.util.Log;

public class Task implements Runnable {

    private String mName;

    public Task(String name) {
        mName = name;
    }

    @Override
    public void run() {
        try {
            Log.d("abba", Thread.currentThread().getName() + " start task " + mName);

            Thread.sleep(500);

            Log.d("abba", Thread.currentThread().getName() + " finish task " + mName);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
