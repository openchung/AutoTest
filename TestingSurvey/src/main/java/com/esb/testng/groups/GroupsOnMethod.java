package com.esb.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {

    @Test(groups = "server")
    public void test1() {
        System.out.println("這是服務端組的測試方法1");
    }

    @Test(groups = "server")
    public void test2() {
        System.out.println("這是服務端組的測試方法2");
    }

    @Test(groups = "client")
    public void test3() {
        System.out.println("這是客戶端組的測試方法3");
    }

    @Test(groups = "client")
    public void test4() {
        System.out.println("這是客戶端組的測試方法4");
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer() {
        System.out.println("這是服務端組運行之前運行的方法!!!");
    }

    @AfterGroups("server")
    public void afterGroupsOnServer() {
        System.out.println("這是服務端組運行之後運行的方法!!!");
    }


    @BeforeGroups("client")
    public void beforeGroupsOnClient() {
        System.out.println("這是客戶端組運行之前運行的方法!!!");
    }

    @AfterGroups("client")
    public void afterGroupsOnClient() {
        System.out.println("這是客戶端組運行之後運行的方法!!!");
    }
}
