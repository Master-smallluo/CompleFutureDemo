package com.sanqi.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author: JenSen
 * @Date: 2024/4/29 14:21
 * @Description: CompletableFutureDemo  completableFuture 测试类
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("开始查询用户角色信息");
        CompletableFuture<Map<String,Object>> roleFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            HashMap<String, Object> roleMap = new HashMap<>();
            roleMap.put("role", "管理员");
            long endTime = System.currentTimeMillis();
            System.out.println("查询用户角色信息结束，耗时：" + (endTime - startTime) + "ms");
            return roleMap;
        });


        System.out.println("开始查询用户用户积分信息");
        CompletableFuture<Map<String,Object>> integralFuture  = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            HashMap<String, Object> integralMap = new HashMap<>();
            integralMap.put("integral", 1015);
            long endTime = System.currentTimeMillis();
            System.out.println("查询用户积分信息结束，耗时：" + (endTime - startTime) + "ms");
            return integralMap;
        });

        roleFuture.join();
        integralFuture.join();
        System.out.println("总耗时:" + (System.currentTimeMillis() - startTime));
    }
}

