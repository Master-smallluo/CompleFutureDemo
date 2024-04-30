package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 13:15
 * @Description: ApplyToEitherDemo
 */
public class ApplyToEitherDemo {
    //将两个CompletableFuture组合起来，只要其中一个执行完了,就会执行某个任务。
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

        //任务组合
        //会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值。
        task1.applyToEitherAsync(task2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                System.out.println("异步任务3，当前线程是:" + Thread.currentThread().getId());
                System.out.println("上一个任务结果为:" + integer);
                return integer;
            }
        });
        //关闭线程池
        executorService.shutdown();
    }
}
