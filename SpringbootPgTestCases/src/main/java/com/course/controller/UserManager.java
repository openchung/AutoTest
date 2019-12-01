package com.course.controller;

import com.course.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@Api(value = "v1", description = "使用者管理系統")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登入接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(HttpServletResponse response, @RequestBody User user) {
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        log.info("查詢到的結果是" + i );
        if(i==1){
            log.info("登入的使用者是: " + user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "取得使用者(列表)訊息接口",httpMethod = "POST")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request, @RequestBody User user) {
        Boolean isVerified = verfyCookies(request);
        if(isVerified == true){
            List<User> users = template.selectList("getUserInfo", user);
            log.info("getUserInfo取得到的使用者數量是" + users.size());
            return users;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "添加使用者接口", httpMethod = "POST")
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request, @RequestBody User user) {
        System.out.println(user.toString());
        Boolean isVerified = verfyCookies(request);
        int result = 0;
        if(isVerified != null) {
            result = template.insert("addUser", user);
        }
        if(result > 0) {
            log.info("增加使用者的數量是: " + result);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "更新/刪除使用者接口", httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request, @RequestBody User user) {
        System.out.println(user.toString());
        Boolean isVerified = verfyCookies(request);
        int result = 0;
        if(isVerified != null) {
            result = template.update("updateUserInfo", user);
        }
        log.info("更新數據的數量為: " + result);
        return result;
    }

    private Boolean verfyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)) {
            log.info("cookies 為空");
            return false;
        }
        for (Cookie cookie: cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                log.info("Cookie驗證通過!");
                return true;
            }
        }
        return false;
    }


}
