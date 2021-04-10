package com.myos.writescene;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @Author: wu sir
 * @Date: 2020/5/14 2:51 下午
 * 最近最少使用的缓存算法
 */
public class LRUCache {

    public void g(String s) {
        System.out.println("2");
    }
    public void f(List<String> object) {
        System.out.println("2");
    }

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }
        public DLinkedNode(int key,int value) {
            this.key = key;
            this.value = value;
        }
    }


    private Map<Integer,DLinkedNode> cache = new Hashtable<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head,tail;

    private void addNode(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.value;
    }

    public void put(int key,int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            DLinkedNode newNode = new DLinkedNode(key,value);
            addNode(newNode);
            cache.put(key,newNode);
            size++;
            if (size > capacity) {
                DLinkedNode rem = popTail();
                cache.remove(rem.key);
                size--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }


    public static void main(String[] args) {
        LRUCache obj = new LRUCache(3);
        obj.put(1,11);
        obj.put(2,12);
        obj.get(1);
        obj.put(3,13);
        obj.put(4,14);

        System.out.println(obj);
    }
}
