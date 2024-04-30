package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 10:17
 * @Description: ExceptionallyDemo  CompletableFuture 异步任务异常处理回调方法
 */
public class ExceptionallyDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime  = System.currentTimeMillis();
        CompletableFuture<Map<String, Object>> amountCompletableFuture = CompletableFuture.supplyAsync(new Supplier<Map<String, Object>>() {
            @Override
            public Map<String, Object> get() {
                long startTime  = System.currentTimeMillis();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                HashMap<String, Object> amountMap = new HashMap<>();
                amountMap.put("amount",1000);
                System.out.println("amount线程执行时间：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
                return amountMap;
            }
        });

        CompletableFuture<Integer> thenCompletableFuture = amountCompletableFuture.thenApply(new Function<Map<String, Object>, Integer>() {
            @Override
            public Integer apply(Map<String, Object> stringObjectMap) {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                    int i = (Integer)stringObjectMap.get("amount") / 0;
                    System.out.println(i);
                } catch (InterruptedException | ArithmeticException e ) {
                    throw new RuntimeException(e);// 这里一定要将异常抛除了，不然exceptionally无效
                }
                System.out.println("thenApply线程执行时间：" + (System.currentTimeMillis() - startTime) / 1000 + "ms");
                return (Integer) stringObjectMap.get("amount");
            }
        });

        CompletableFuture<Integer> exceptionFuture = thenCompletableFuture.exceptionally((e) -> {
            System.out.println(e);
            System.out.println("除数为0，则默认商为0！");
            return 0;
        });
        exceptionFuture.get();
    }
}
