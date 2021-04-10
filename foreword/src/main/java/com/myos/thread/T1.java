package com.myos.thread;

import io.netty.util.concurrent.CompleteFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: wu sir
 * @Date: 2020/7/29 10:37 上午
 */
public class T1 {

    public static void main(String[] args) throws Exception {
        /*CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        CompletableFuture future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3;
        });
        CompletableFuture result = CompletableFuture.anyOf(future1,future2,future3);
        System.out.println(result.get());

        CompletableFuture result1 = CompletableFuture.allOf(future1,future2,future3);
        List<Object> list = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println(list.size());

*/
        Integer s = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }),(r) -> {
            return r;
        }).join();

        System.out.println(s);


    }
}
