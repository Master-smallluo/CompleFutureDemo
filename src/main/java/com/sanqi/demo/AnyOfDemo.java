package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 14:47
 * @Description: AnyOfDemo  CompletableFuture 多任务组合处理
 */
public class AnyOfDemo {
    //任意一个任务执行完，就执行anyOf返回的CompletableFuture。
    // 如果执行的任务异常，anyOf的CompletableFuture，执行get方法，会抛出异常。
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建自定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //开启异步任务1
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
                int result = 2000 / 22;
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
                int result = 1 + 2;
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

        //组合任务
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(task1, task2, task3);
        //只要有任意一个任务完成，则获取其任务结果
        Object o = anyOf.get();
        System.out.println("完成任务的结果:" + o);

        //关闭线程池
        executorService.shutdown();
    }
}
