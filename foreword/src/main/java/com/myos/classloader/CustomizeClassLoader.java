package com.myos.classloader;

import java.io.File;
import java.util.regex.Matcher;

public class CustomizeClassLoader extends ClassLoader{

    private String dir;

    private static final String SUFFIX = ".class";

    static{
//		registerAsParallelCapable();	//TODO:启用并发容器
    }

    public CustomizeClassLoader(String dir) {
        // 默认情况下自定义的ClassLoader双亲是AppClassLoader
        // 由于测试的CacheConfig.class文件在应用中已经存在，为了不让AppClassLoader加载，所以这里设置双亲为null
        // 设置为null，则只会尝试从启动类加载器进行加载，无法加载的情况下就会尝试用本加载器进行加载
        super(null);
        this.dir = dir;
    }


    /**
     * 从源码中可以看出，当load一个类时，默认情况下锁是当前的ClassLoader对象，这意味着”相同的ClassLoader”在并发加载类时（即使是不同类），
     * 同时只会有一个线程在加载，其它线程都是阻塞。想象一下一个的场景：假设程序中有1W个类，1W个用户同时来访问，
     * 每个访问需要加载的类都不同（但由同一ClassLoader加载），这种情况下，以ClassLoader对象本身为锁的方式将会严重影响性能。
     * Debug源码后发现，通过registerAsParallelCapable();  可以让ClassLoader使用并发容器。这种情况下，
     * 当ClassLoader加载类时，如果该类是第一次加载，则会以该类的完全限定名称作为Key，一个new  Object()对象为Value，
     * 存入一个ConcurrentHashMap的中。并以该object对象为锁进行同步控制。同一时间如果有其它线程再次请求加载该类时，
     * 则取出map中的对象object，发现该对象已被占用，则阻塞。也就是说ClassLoader的并发加载通过一个ConcurrentHashMap实现的。
     * @param name
     * @return
     */
    @Override
    protected Class<?> findClass(String name) {
        try {
            // 0、文件对应磁盘路径
            String newName = dir + name.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) + SUFFIX;

            // 1、获取文件字节数组
            byte[] classByte = loadClassData(newName);

            // 2、生成Class对象
            Class<?> cl = defineClass(name, classByte, 0, classByte.length);

            // 打印当前加载线程
            System.out.println("当前加载线程：" + Thread.currentThread().getName() + "，加载器："  + cl.getClassLoader());

            return cl;
        } catch (Exception e) {
            // test...不做任何事
            e.printStackTrace();
            throw new ClassFormatError(e.getMessage());
        }
    }

    /**
     * 读取.class文件，返回字节数组
     *
     * @param name
     *            文件路径
     * @return 字节数组
     * @throws Exception
     */
    private byte[] loadClassData(String name) throws Exception {
        try {
            final java.io.FileInputStream fis = new java.io.FileInputStream(name);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return bytes;
        } finally {

        }
    }

}
