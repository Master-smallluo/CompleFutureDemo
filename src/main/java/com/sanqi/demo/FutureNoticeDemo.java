package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 15:03
 * @Description: FutureNoticeDemo   若不调用future的get()或CompletableFuture的join()方法，则不会看见异常抛出
 */
public class FutureNoticeDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> doubleCompletableFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 2000 / 0;
            }
        });

        // 若不调用future的get()或CompletableFuture的join()方法，则不会看见异常抛出
        System.out.println(doubleCompletableFuture.join());
    }
}
