package com.rx.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RuiXiang
 * 2020-04-16-15:33
 *
 * @author: RuiXiang
 */

public class Test {

    public static ExecutorService pool =
            new ThreadPoolExecutor(5, 10, 0L,
                    TimeUnit.MICROSECONDS,
                    new LinkedBlockingQueue<>(100),
                    new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        pool.execute(() ->{
            try {
                Thread.sleep(2000);
                System.out.println("step 3");
                System.out.println("test3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.execute(() ->{
            try {
                Thread.sleep(2000);
                System.out.println("step 2");
                System.out.println("test2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("step 1");


    }


}
