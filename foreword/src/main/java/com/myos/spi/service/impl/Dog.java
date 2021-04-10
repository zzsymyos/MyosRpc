package com.myos.spi.service.impl;

import com.myos.spi.service.IShout;

public class Dog implements IShout {
    public void shout() {
        System.out.println("wang wang");
    }
}
