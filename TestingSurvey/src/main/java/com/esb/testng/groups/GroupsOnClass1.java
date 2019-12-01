package com.esb.testng.groups;

import org.junit.jupiter.api.Test;

@org.testng.annotations.Test(groups = "stu")
public class GroupsOnClass1 {

    @Test
    public void stu1() {
        System.out.println("GroupsOnClass1中的stu1運行");
    }

    public void stu2() {
        System.out.println("GroupsOnClass1中的stu2運行");
    }
}
