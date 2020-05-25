package com.example.demoexecutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSingleThreadPool, btnFixedThreadPool, btnCachedThreadPool, btnThreadPoolExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        btnSingleThreadPool = findViewById(R.id.btn_single_thread);
        btnFixedThreadPool = findViewById(R.id.btn_fixed_thread_pool);
        btnCachedThreadPool = findViewById(R.id.btn_cached_thread_pool);
        btnThreadPoolExecutor = findViewById(R.id.btn_thread_pool_executor);

        btnSingleThreadPool.setOnClickListener(this);
        btnFixedThreadPool.setOnClickListener(this);
        btnCachedThreadPool.setOnClickListener(this);
        btnThreadPoolExecutor.setOnClickListener(this);
    }

    //trong ThreadPool chỉ có 1 Thread và các task (nhiệm vụ) sẽ được xử lý một cách tuần tự.
    private void onClickSingleThreadExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100; i++) {
            executor.execute(new Task(Integer.toString(i)));
        }
        executor.shutdown();
    }

    private void onClickFixedThreadExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executor.execute(new Task(Integer.toString(i)));
        }
        executor.shutdown();
    }

    private void onClickCachedThreadExecutor() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executor.execute(new Task(Integer.toString(i)));
        }
        executor.shutdown();
    }

    private void onClickThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
        for (int i = 0; i < 100; i++) {
            executor.execute(new Task(Integer.toString(i)));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_thread:
                onClickSingleThreadExecutor();
                break;
            case R.id.btn_fixed_thread_pool:
                onClickFixedThreadExecutor();
                break;
            case R.id.btn_cached_thread_pool:
                onClickCachedThreadExecutor();
                break;
            case R.id.btn_thread_pool_executor:
                onClickThreadPoolExecutor();
                break;
        }
    }
}
