package com.esb.server;

import com.esb.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "這是我全部的Post請求")
@RequestMapping("/v1")
public class MyPostMethod {

    //這個變量是用來給我們cookie訊息的
    private static Cookie cookie;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登入接口，成功後獲取cookie訊息", httpMethod = "POST")
    @ResponseBody
    public String login(HttpServletResponse response,
                            @RequestBody User user
//                        @RequestParam(value = "username", required = true) String username,
//                        @RequestParam(value = "password",required = true) String password
                        ){
//        if(username.equals("danniel") && password.equals("123456")) {
        if(user.getUsername().equals("danniel") && user.getPassword().equals("123456")) {

                cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "true";
        }
        return "使用者或者密碼錯誤!";
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "取得使用者列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestBody User user) {
        User ruser = null;
        System.out.println(user.toString());
        //取得Cookies
        Cookie[] cookies = request.getCookies();
        //驗證Cookies是否合法
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && user.getUsername().equals("danniel"))
            {
                ruser = new User();
                ruser.setName("lisi");
                ruser.setAge("18");
                ruser.setSex("man");
                return ruser.toString();
            }
        }

        return "參數不合法";
    }
}
