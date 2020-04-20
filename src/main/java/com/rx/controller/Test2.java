package com.rx.controller;

import java.util.concurrent.*;

/**
 * RuiXiang
 * 2020-04-16-15:54
 *
 * @author: RuiXiang
 */

public class Test2 {

    public static ExecutorService pool =
            new ThreadPoolExecutor(5, 10, 0L,
                    TimeUnit.MICROSECONDS,
                    new LinkedBlockingQueue<>(100),
                    new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {

        //创建Callable对象任务
        Callable<Integer> callable = new Callable<Integer>() {
            private int sum;

            @Override
            public Integer call() throws Exception {
                System.out.println("Callable子线程开始计算啦！");
                Thread.sleep(2000);
                for (int i = 0; i < 5000; i++) {
                    sum += i;
                }
                System.out.println("Callable子线程计算结束！");
                return sum;
            }
        };

        //future模式
        // Future<Integer> submit = pool.submit(callable);

        //futureTask模式
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        // 由于FutureTask继承于Runable，所以也可以直接调用run方法执行
        // future.run();
        //执行任务效果同上面直接调用run方法
        pool.submit(futureTask);
        //关闭线程池
        pool.shutdown();

        try {
            System.out.println("主线程在执行其他任务");
            if (futureTask.get() != null) {
                //输出获取到的结果
                System.out.println("future.get()-->" + futureTask.get());
            } else {
                //输出获取到的结果
                System.out.println("future.get()未获取到结果");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("主线程在执行完成");

    }


}
