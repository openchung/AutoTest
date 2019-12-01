package com.esb.testng;

import org.testng.annotations.*;

public class BasicAnnotation {

    //最基本的註解，用來把方法標記為測試的一部分
    @Test
    public void testCase1() {
        System.out.printf("BasicAnnotation testCase1 Thread Id: %s%n",Thread.currentThread().getId());
        System.out.println("Test這是測試用例1");
    }

    @Test
    public void testCase2() {
        System.out.printf("BasicAnnotation testCase2 Thread Id: %s%n",Thread.currentThread().getId());
        System.out.println("Test這是測試用例2");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod這是在測試方法之前運行的");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod這是在測試方法之後運行的");
    }

    //靜態方法、變量覆值
    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass這是在類運行之前運行的方法");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass這是在類運行之後運行的方法");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite測試套件");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite測試套件");
    }
}
