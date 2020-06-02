package com.example.demoexecutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFilter, btnMap, btnDistinct, btnMerge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        bindEvent();

    }

    private void findView() {
        btnFilter = findViewById(R.id.btn_filter);
        btnMap = findViewById(R.id.btn_map);
        btnDistinct = findViewById(R.id.btn_distinct);
        btnMerge = findViewById(R.id.btn_merge);
    }

    private void bindEvent() {
        btnFilter.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnDistinct.setOnClickListener(this);
        btnMerge.setOnClickListener(this);
    }

    private List<String> getListString() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        return list;
    }

    private void onClickFilter() {
        // Tạo observable phát ra các item là số nguyên chẵn trong khoảng từ 1 đến 100.
        Observable<Integer> observable = Observable.range(1, 100)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        if (integer % 2 == 0) {
                            return true;
                        }
                        return false;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("abba", "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("abba", "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("abba", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("abba", "onComplete: ");
            }
        };

        observable.subscribe(observer);

    }

    private void onClickMap() {
        // Tạo observable phát ra từng item của list string dưới dạng chữ in hoa.
        Observable<String> observable = Observable.fromIterable(getListString()).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s.toUpperCase();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("abba", "onSubscribe: ");
            }

            @Override
            public void onNext(String string) {
                Log.d("abba", "onNext: " + string);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("abba", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("abba", "onComplete: ");
            }
        };

        observable.subscribe(observer);

    }

    private void onClickDistinct() {
        // tạo observable phát ra từng item và loại bỏ các item trùng lặp
        Observable<Integer> observable = Observable.just(1, 2, 3, 1, 2, 4, 5, 6, 3, 5).distinct();
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("abba", "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("abba", "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("abba", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("abba", "onComplete: ");
            }
        };
        observable.subscribe(observer);
    }

    private void onClickMerge() {
        String[] aStringArray = {"a1", "a2", "a3", "a4"};
        String[] bStringArray = {"b1", "b2", "b3"};

        Observable<String> aObservable = Observable.fromArray(aStringArray);
        Observable<String> bObservable = Observable.fromArray(bStringArray);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("abba", "onSubscribe: ");
            }

            @Override
            public void onNext(String string) {
                Log.d("abba", "onNext: " + string);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("abba", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("abba", "onComplete: ");
            }
        };

        Observable.merge(aObservable, bObservable)
                .subscribe(observer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                onClickFilter();
                break;
            case R.id.btn_map:
                onClickMap();
                break;
            case R.id.btn_distinct:
                onClickDistinct();
                break;
            case R.id.btn_merge:
                onClickMerge();
                break;
        }
    }
}
