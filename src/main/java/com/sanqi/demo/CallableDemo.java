package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: JenSen
 * @Date: 2024/4/29 15:00
 * @Description: CallableDemo   Callable测试类，无自定义线程池
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Callable<Map<String, Object>> amountCall = () -> {
            long startTime1 = System.currentTimeMillis();
            Thread.sleep(6000);
            HashMap<String, Object> amountMap = new HashMap<>();
            amountMap.put("amount", 1000);
            long endTime = System.currentTimeMillis();
            System.out.println("amount耗时：" + (endTime - startTime1) / 1000 + "ms");
            return amountMap;
        };
        FutureTask<Map<String, Object>> mapFutureTask = new FutureTask<>(amountCall);
        new Thread(mapFutureTask).start();

        Callable<Map<String, Object>> roleCall = () -> {
            long startTime1 = System.currentTimeMillis();
            Thread.sleep(5000);
            HashMap<String, Object> roleMap = new HashMap<>();
            roleMap.put("role", "管理员");
            long endTime = System.currentTimeMillis();
            System.out.println("role耗时：" + (endTime - startTime1) / 1000 + "ms");
            return roleMap;
        };
        FutureTask<Map<String, Object>> roleFutureTask = new FutureTask<>(roleCall);
        new Thread(roleFutureTask).start();
        //get() 要造成线程堵塞，所以要放到最后
        System.out.println("金额查询结果为:" + mapFutureTask.get());
        System.out.println("角色查询结果为:" + roleFutureTask.get());
        System.out.println("总耗时:"+ (System.currentTimeMillis() - startTime) / 1000 + "ms");
    }
}
