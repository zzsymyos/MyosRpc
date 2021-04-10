package com.myos.spi.service;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface IShout {
    void shout();
}
