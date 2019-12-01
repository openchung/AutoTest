package com.esb.testng.extend;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodsDemo {
    @Test
    public void test1(){
        Assert.assertEquals(1, 2);
    }

    @Test
    public void test2(){
        Assert.assertEquals(1, 1);
    }

    @Test
    public void test3(){
        Assert.assertEquals("aaa", "aaa");
    }

    @Test
    public void logDemo() {
        Reporter.log("這是我們自己寫的日誌");
        throw new RuntimeException("這是我自己的運行時異常");
    }
}
