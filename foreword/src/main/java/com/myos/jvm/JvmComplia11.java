package com.myos.jvm;

import java.io.UnsupportedEncodingException;

/**
 * @Author: wu sir
 * @Date: 2020/7/20 6:03 下午
 */
public class JvmComplia11 {

    public static int doubleValue(int i) {
        for (int j = 0;j< 100000;j++) {

        }
        return i*2;
    }

    public static long calcsum() {
        long sum = 0;
        for (int i = 0 ; i < 100;i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 15000;i++) {
            calcsum();
        }



    }
}
