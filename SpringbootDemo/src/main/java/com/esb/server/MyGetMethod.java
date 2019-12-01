package com.esb.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/", description = "這是我全部的Get方法")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    @ApiOperation(value = "通過這個方法可以獲取cookie值",httpMethod = "GET")
    public String getCookies(HttpServletRequest request, HttpServletResponse response) {
        //HttpServletRequest 裝請求訊息的類
        //HttpServletResponse 裝響應訊息的類
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "Congraduations! Get the cookies successfully";
    }

    /*
    * 要求客戶端攜帶cookies訪問
    * 這是一個需要攜帶cookies訊息才能訪問的get請求
    * */
    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "必須攜帶cookies的訪問",httpMethod = "GET")
    public String  getWithCookies(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "你必須攜帶cookies訊息來";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return "Congraduation visit successfully!";
            }
        }
        return "你必須攜帶cookies訊息來";
    }

    /*
     * 開發一個需要攜帶參數才能訪問的get請求
     * 第一種實現方式url: key=value&key=value
     * 我們來模擬獲取商品列表
     * */
    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value = "需要攜帶參數才能訪問的get請求方法一",httpMethod = "GET")
    public Map<String, Integer> getList(@RequestParam Integer start, @RequestParam Integer end) {
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋",400);
        myList.put("泡麵", 1);
        myList.put("汗衫", 300);

        return myList;
    }

    /*
     * 第二種需要攜帶參數訪問的get請求
     * url:ip:port/get/with/param/10/20
     * */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "需要攜帶參數才能訪問的get請求方法二",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,
                         @PathVariable Integer end) {
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋",400);
        myList.put("泡麵", 1);
        myList.put("汗衫", 300);

        return myList;
    }

}
