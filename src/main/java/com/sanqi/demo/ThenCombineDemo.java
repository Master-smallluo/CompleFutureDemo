package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * @author: JenSen
 * @Date: 2024/4/30 11:13
 * @Description: ThenCombineDemo  CompletableFuture 多任务组合处理
 */
public class ThenCombineDemo {
    //将两个CompletableFuture组合起来，只有这两个都正常执行完了，才会执行某个任务。
    public static void main(String[] args) {
        //会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值。
        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> 7);
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> 2)
                //如果你执行第一个任务的时候，传入了一个自定义线程池：
                //调用thenCombine方法执行第二个任务时，则第二个任务和第一个任务是同一个线程池。
                //调用thenCombineAsync(Runnable action)执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池。
                //调用thenCombineAsync(Runnable action,Executor executor)执行第二任务时，则第二个任务和第一个任务是同一个自定义线程池
                .thenCombine(first, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) {
                        System.out.println("第二个方法的结果:" + integer);
                        System.out.println("第一个方法的结果:" + integer2);
                        return integer * integer2 ;
                    }
                });
        System.out.println("两个任务结果为：" + second.join());
    }
}
