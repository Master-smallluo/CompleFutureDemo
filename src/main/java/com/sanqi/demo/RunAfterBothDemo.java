package com.sanqi.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author: JenSen
 * @Date: 2024/4/30 11:31
 * @Description: RunAfterBothDemo CompletableFuture 多任务组合处理
 */
public class RunAfterBothDemo {
    public static void main(String[] args) {
        //不会把执行结果当做方法入参，且没有返回值。
        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> 7);
        CompletableFuture<Void> second = CompletableFuture.supplyAsync(() -> 2)
                .runAfterBoth(first, new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("无法获取两个任务的结果，也无法返回");
                    }
                });
        System.out.println("最后结果为:" + second.join());
    }
}
