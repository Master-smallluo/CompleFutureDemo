package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 15:23
 * @Description: ThenComposeDemo    多任务组合
 */
public class ThenComposeDemo {
    public static void main(String[] args) {
        //thenCompose方法会在某个任务执行完成后，将该任务的执行结果,作为方法入参,去执行指定的方法。该方法会返回一个新的CompletableFuture实例。
        //如果该CompletableFuture实例的result不为null，则返回一个基于该result新的CompletableFuture实例。
        //如果该CompletableFuture实例为null，然后就执行这个新任务。
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //开启有返回值一个异步任务
//        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                return 2000 / 22;
//            }
//        },executorService);

        //若value不为空，则基于value返回一个全新的CompletableFuture实例
//        CompletableFuture<Integer> integerCompletableFuture1 = integerCompletableFuture.thenCompose(value -> CompletableFuture.supplyAsync(() -> {
//            System.out.println("上一个任务结果是:" + value);
//            return value * 2;
//        }, executorService));


        //开启无返回值一个异步任务
        CompletableFuture<Void> integerCompletableFuture = CompletableFuture.runAsync(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(2000 / 22);
                    }
                }, executorService);
        //若value为空，则直接执行thenCompose中的内容，返回一个无结果的CompletableFuture
        CompletableFuture<Void> integerCompletableFuture1 = integerCompletableFuture.thenCompose(value -> CompletableFuture.supplyAsync(() -> {
            System.out.println("上一个任务结果是:" + value);
            return value;
        }, executorService));

        //获取thenCompose后的任务结果
        System.out.println(integerCompletableFuture1.join());
        //关闭线程池
        executorService.shutdown();
    }
}
