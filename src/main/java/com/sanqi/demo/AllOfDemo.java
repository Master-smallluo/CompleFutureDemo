package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 14:18
 * @Description: AllOfDemo  CompletableFuture 多任务组合处理
 */
public class AllOfDemo {
    //所有任务都执行完成后，才执行 allOf返回的CompletableFuture。
    // 如果任意一个任务异常，allOf的CompletableFuture，执行get方法，会抛出异常。

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建自定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //开启异步任务1
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
                int result = 1 + 1;
                System.out.println("异步任务1结束");
                return result;
            }
        },executorService);


        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
                //睡眠3秒
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int result = 1 / 0;
                System.out.println("异步任务2结束");
                return result;
            }
        },executorService);

        //开启异步任务3
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("异步任务3，当前线程是：" + Thread.currentThread().getId());
                //睡眠4秒
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int result = 13 * 92;
                System.out.println("异步任务3结束");
                return result;
            }
        },executorService);

        //任务组合
        // 所有任务都执行完成后，才执行 allOf返回的CompletableFuture。如果任意一个任务异常，allOf的CompletableFuture，执行get方法，会抛出异常。
        // 这里第一次执行没有睡眠的话，是可以直接执行第三个任务的。如果有睡眠，则需要手动join启动。
        CompletableFuture<Void> allOf = CompletableFuture.allOf(task1, task2, task3);
        allOf.join();//无任务结果，但是会抛出异常信息，不执行该方法将不会抛出异常
        //获取第三个异步任务结果
//        System.out.println(task3.join());
        //关闭线程池
        executorService.shutdown();

    }
}
