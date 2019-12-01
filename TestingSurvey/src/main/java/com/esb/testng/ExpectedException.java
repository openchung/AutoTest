package com.esb.testng;

//import org.junit.jupiter.api.Test;


import org.testng.annotations.Test;

public class ExpectedException {

    /*　
    * 什麼時候會用到異常測試？
    * 在我們期望結果為某一個異常的時候
    * 比如： 我們傳入了某些不合法的參數，程序拋出了異常
    * 也就是說我的語氣結果就是這個異常
    * */

    //　這是一個測試結果會失敗的異常測試
    @Test(expectedExceptions = RuntimeException.class)
    public void runtimeExceptionFailed() {
        System.out.println("這是一個失敗的異常測試");
    }

    //這是一個成功的異常測試
    @Test(expectedExceptions = RuntimeException.class)
    public void runtimeExceptionSuccess() {
        System.out.println("這是我的異常測試");
        throw new RuntimeException("這是異常");
    }
}
