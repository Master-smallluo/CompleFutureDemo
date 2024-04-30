package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: JenSen
 * @Date: 2024/4/29 15:36
 * @Description: CallableDemoCustomThreadPool  Callable测试类，有自定义线程池
 */
public class CallableDemoCustomThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        // 创建自定义线程池，2个线程
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Map<String, Object>> amountCall = () -> {
            long startTime1 = System.currentTimeMillis();
            Thread.sleep(6000);
            HashMap<String, Object> amountMap = new HashMap<>();
            amountMap.put("amount", 1000);
            long endTime = System.currentTimeMillis();
            System.out.println("amount耗时：" + (endTime - startTime1) / 1000 + "ms");
            return amountMap;
        };
        Callable<Map<String, Object>> roleCall = () -> {
            long startTime1 = System.currentTimeMillis();
            Thread.sleep(5000);
            HashMap<String, Object> roleMap = new HashMap<>();
            roleMap.put("role", "管理员");
            long endTime = System.currentTimeMillis();
            System.out.println("role耗时：" + (endTime - startTime1) / 1000 + "ms");
            return roleMap;
        };
        Future<Map<String, Object>> aounmtFuture = executor.submit(amountCall);
        Future<Map<String, Object>> roleFuture = executor.submit(roleCall);

        System.out.println("金额查询结果为：" + aounmtFuture.get());
        System.out.println("角色查询结果为：" + roleFuture.get());
        //关闭线程池，否则线程中任务执行完毕之后会一直处于等待状态
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时:" + (endTime - startTime) / 1000 + "秒");
    }
}

