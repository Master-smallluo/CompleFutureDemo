package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 09:18
 * @Description: ThenApplyAndThenApplyAsyncDemo  CompletableFuturei异步任务回调
 */
public class ThenApplyAndThenApplyAsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        //CompletableFuture的thenApply方法表示，第一个任务执行完成后，执行第二个回调方法任务
        // ，会将该任务的执行结果，作为入参，传递到回调方法中，并且回调方法是有返回值的。
        CompletableFuture<Map<String, Object>> amountCompletableFuture = CompletableFuture.supplyAsync(new Supplier<Map<String, Object>>() {
            @Override
            public Map<String, Object> get() {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                HashMap<String, Object> amountMap = new HashMap<>();
                amountMap.put("amount", 1000);
                System.out.println("amount线程执行时间：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
                return amountMap;
            }
        });
        //如果你执行第一个任务的时候，传入了一个自定义线程池：
        //调用thenApply方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
        //调用thenApplyAsync(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
        //调用thenApplyAsync(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
        CompletableFuture<Integer> thenCompletableFuture = amountCompletableFuture.thenApply(new Function<Map<String, Object>, Integer>() {
            @Override
            public Integer apply(Map<String, Object> stringObjectMap) {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thenApply线程执行时间：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
                return (Integer) stringObjectMap.get("amount");
            }
        });
        Integer i = thenCompletableFuture.get();
        System.out.println("金额为:" + i);
        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
    }
}
