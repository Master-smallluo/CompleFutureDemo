package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/30 10:37
 * @Description: WhenCompleteDemo  CompletableFuture 异步任务调用
 */
public class WhenCompleteDemo {
    public static void main(String[] args) {
        // 某个任务执行完成后，执行的回调方法，无返回值；并且whenComplete方法返回的CompletableFuture的result是上个任务的结果。
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello world!";
            }
        });

        //如果你执行第一个任务的时候，传入了一个自定义线程池：
        //调用whenComplete方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
        //调用whenComplete(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
        //调用whenComplete(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
        CompletableFuture<String> stringCompletableFuture1 = stringCompletableFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(s  + "1");
            }
        });
        System.out.println("输出结果为第一个任务：" +stringCompletableFuture1.join());
    }
}
