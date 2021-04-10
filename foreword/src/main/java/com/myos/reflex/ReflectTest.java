package com.myos.reflex;

import com.myos.reflex.dto.Hero;
import com.myos.reflex.dto.HeroPlus;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectTest {


    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        test3();
    }


    private static void test3() throws Exception  {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();

        File file = new File(courseFile + "/foreword/src/main/resources/reflect.txt");
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        String classname = properties.getProperty("class");
        String methodname = properties.getProperty("method");
        Class pc1 = Class.forName(classname);
        Constructor constructor = pc1.getConstructor();
        Object object = constructor.newInstance();
        Method method = pc1.getMethod(methodname);
        method.invoke(object);
    }

    private static void test2()  throws Exception{
        Class pc1 = Class.forName("com.myos.reflex.dto.HeroPlus");
        Method method = pc1.getMethod("main",String[].class);
        method.invoke(null,(Object)new String[]{"a","b","c"});

        Constructor c = pc1.getConstructor();
        Object ts = c.newInstance();
        Method method2 = pc1.getMethod("setName",String.class);
        method2.invoke(ts,"123");


        Method method1 = pc1.getMethod("toString");

        System.out.println(method1.invoke(ts));
    }

    /*
	 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
	 *
	 * 1.获取构造方法：
	 * 		1).批量的方法：
	 * 			public Constructor[] getConstructors()：所有"公有的"构造方法
	            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

	 * 		2).获取单个的方法，并调用：
	 * 			public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
	 * 			public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
	 *
	 * 2.创建对象
	 * 		Constructor对象调用newInstance(Object... initargs)
	 */
    private static void test1() throws Exception {
        String classname = "com.myos.reflex.dto.Hero";
        Class pc1 = Class.forName(classname);
        Class pc2 = Hero.class;
        Class pc3 = new Hero().getClass();
        System.out.println("**********************所有公有构造方法*********************************");
        Constructor[] constructors = pc1.getConstructors();
        for(Constructor c:constructors) {
            System.out.println(c.getName() + c);
        }

        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");

        constructors = pc1.getDeclaredConstructors();
        for (Constructor c : constructors) {
            System.out.println(c);
        }

        System.out.println("*****************获取公有、无参的构造方法*******************************");

        Constructor con = pc1.getConstructor(null);
        System.out.println(con);

        Object obj = con.newInstance();

        System.out.println("******************获取私有构造方法，并调用*******************************");

        con = pc1.getDeclaredConstructor(boolean.class);
        System.out.println(con);
        con.setAccessible(true);
        obj = con.newInstance(true);
    }
}
