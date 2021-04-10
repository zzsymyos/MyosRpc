package com.myos.spi.service.impl;

import com.myos.spi.service.IShout;

public class Cat implements IShout {
    public void shout() {
        System.out.println("miao miao");
    }
}
