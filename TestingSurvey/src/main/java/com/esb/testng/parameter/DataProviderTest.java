package com.esb.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age) {
        System.out.println("name = " + name + "; age = " + age);
    }

    @DataProvider(name="data")
    public Object[][] providerData() {
        Object[][] o = new Object[][] {
                {"Danniel",10},
                {"Bob", 20},
                {"Yelena", 30}
        };

        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name, int age) {
        System.out.println("test1方法: name -> " + name + ", age -> " + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age) {
        System.out.println("test2方法: name -> " + name + ", age -> " + age);
    }

    @DataProvider(name="methodData")
    public Object[][] methodData(Method method) {
        Object[][] result = null;
        if(method.getName().equals("test1")){
            result = new Object[][] {
                    {"Mark",60},
                    {"John", 20},
                    {"Mary", 30}
            };
        } else if(method.getName().equals("test2")){
            result = new Object[][] {
                    {"Irene",22},
                    {"Catch", 9},
                    {"Ben", 90}
            };
        }

        return result;
    }

}
