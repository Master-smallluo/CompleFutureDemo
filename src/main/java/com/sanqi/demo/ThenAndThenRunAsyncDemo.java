package com.sanqi.demo;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: JenSen
 * @Date: 2024/4/29 16:47
 * @Description: ThenAndThenRunAsyncDemo  CompletableFuture的异步任务回调
 */
public class ThenAndThenRunAsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 执行第一个任务后 可以继续执行第二个任务 两个任务之间无传参 无返回值
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> amountCompletableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("amountCompletableFuture执行完毕，耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
            }
        });
        //如果你执行第一个任务的时候，传入了一个自定义线程池：
        //调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
        //调用thenRunAsync(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
        //调用thenRunAsync(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
        CompletableFuture<Void> thenCompletableFuture = amountCompletableFuture.thenRunAsync(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thenCompletableFuture执行完毕，耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
            }
        });
        thenCompletableFuture.get();
        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
    }
}
