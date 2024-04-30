package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/29 17:43
 * @Description: ThenAcceptAndThenAcceptAsync  CompletableFuture的异步任务回调
 */
public class ThenAcceptAndThenAcceptAsync {
    //CompletableFuture的thenAccept方法表示，第一个任务执行完成后
    // ，执行第二个回调方法任务，会将该任务的执行结果，作为入参
    // ，传递到回调方法中，但是回调方法是没有返回值的。

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        // 执行第一个任务后 可以继续执行第二个任务 并携带第一个任务的返回值 第二个任务执行完没有返回值
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
                amountMap.put("amount",9000);
                System.out.println("第一个任务执行完成，耗时："+(System.currentTimeMillis()-startTime) / 1000+"ms");
                return amountMap;
            }
        });
        //如果你执行第一个任务的时候，传入了一个自定义线程池：
        //调用thenAccept方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
        //调用thenAcceptAsync(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
        //调用thenAcceptAsync(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
        CompletableFuture<Void> thenCompletableFuture = amountCompletableFuture.thenAccept(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> stringObjectMap) {
                System.out.println("第一个任务执行完成，结果：");
                
                for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
                    System.out.println(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue());
                }
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("第二个任务执行完成，耗时："+(System.currentTimeMillis()-startTime) / 1000+"ms");
            }
        });

        thenCompletableFuture.get();
        System.out.println("总耗时："+(System.currentTimeMillis()-startTime) / 1000+"ms");
    }
}
