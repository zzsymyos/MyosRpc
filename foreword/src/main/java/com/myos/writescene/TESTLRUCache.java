package com.myos.writescene;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @Author: wu sir
 * @Date: 2020/5/14 2:51 下午
 * 最近最少使用的缓存算法
 */
public class TESTLRUCache extends LRUCache {

    public TESTLRUCache(int capacity) {
        super(capacity);
    }

    public void g(Object o) {
        System.out.println("1");
    }

    @Override
    public void f(List<String> object) {
        System.out.println("1");
    }

    public static void main(String[] args) {
        TESTLRUCache t = new TESTLRUCache(2);
        t.f(new ArrayList());
        t.g(new Object());
    }
}
