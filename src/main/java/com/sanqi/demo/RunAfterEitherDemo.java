package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 13:51
 * @Description: RunAfterEitherDemo
 */
public class RunAfterEitherDemo {
    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //开启异步任务1
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                long startTime = System.currentTimeMillis();
                System.out.println("异步任务1，当前线程是:" + Thread.currentThread().getId());
                int result = 1 + 1;
                System.out.println("异步任务1，执行结果是:" + result + ",耗时:" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
                return result;
            }
        },executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                long startTime = System.currentTimeMillis();
                System.out.println("异步任务2，当前线程是:" + Thread.currentThread().getId());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int result = 2 + 2;
                System.out.println("异步任务2，执行结果是:" + result + ",耗时:" + (System.currentTimeMillis() -startTime) / 1000 + "ms");
                return result;
            }
        },executorService);

        //不会把执行结果当做方法入参，且没有返回值。
        task1.runAfterEitherAsync(task2, new Runnable() {
            @Override
            public void run() {
                System.out.println("异步任务3，当前线程是:" + Thread.currentThread().getId());
                System.out.println("无法获取任意任务得结果");
            }
        },executorService);
        //关闭线程
        executorService.shutdown();
    }
}
