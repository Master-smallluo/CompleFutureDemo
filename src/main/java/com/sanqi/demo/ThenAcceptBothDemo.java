package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author: JenSen
 * @Date: 2024/4/30 11:23
 * @Description: ThenAcceptBothDemo CompletableFuture 多任务组合处理
 */
public class ThenAcceptBothDemo {
    public static void main(String[] args) {
        //会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值。
        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() ->9);
        CompletableFuture<Void> second = CompletableFuture.supplyAsync(() -> 9)
                //如果你执行第一个任务的时候，传入了一个自定义线程池：
                //调用thenAcceptBoth方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
                //调用thenAcceptBothAsync(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
                //调用thenAcceptBothAsync(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
                .thenAcceptBoth( first, new BiConsumer<Integer, Integer>() {
                    @Override
                    public void accept(Integer integer, Integer integer2) {
                        System.out.println("无法将两个任务结果返回:" + integer2 * integer);
                    }
                });
        System.out.println("无返回值"+second.join());
    }
}
