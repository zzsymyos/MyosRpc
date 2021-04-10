package com.myos.design;

/**
 * @Author: wu sir
 * @Date: 2020/8/16 6:53 下午
 */
public class Singleton {

    // 双重加锁模式
    private static volatile Singleton singleton;

    private Singleton() {

    }

    public Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    //new Singleton 可以分成三步
                    // 1、分配内存空间
                    // 2、初始化对象
                    // 3、将对象指向刚分配的内存空间
                    //但是有些编译器为了性能的原因，可能会将第二步和第三步进行重排序，顺序就成了：
                    // 分配内存空间
                    //将对象指向刚分配的内存空间
                    //初始化对象
                    //so 需要在singleton前加入关键字volatile。使用了volatile关键字后，
                    // 重排序被禁止，所有的写（write）操作都将发生在读（read）操作之前。
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
