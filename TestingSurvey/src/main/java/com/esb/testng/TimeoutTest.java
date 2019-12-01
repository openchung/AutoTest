package com.esb.testng;

import org.testng.annotations.Test;

public class TimeoutTest {

    @Test(timeOut = 3000) //單位為毫秒值
    public void testSuccess() throws  InterruptedException {
        Thread.sleep(2000);
    }

    @Test(timeOut = 2000)
    public void testFailed() throws  InterruptedException {
        Thread.sleep(3000);
    }
    
}
