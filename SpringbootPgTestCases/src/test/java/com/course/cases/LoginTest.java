package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.LoginCase;
import com.course.util.ConfigFile;
import com.course.util.DatabaseUtil;
import com.course.model.InterfaceName;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class LoginTest {

    @BeforeTest(groups = "loginTrue", description = "測試準備工作，取得HttpClient對象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl =  ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.defaultHttpClient  = HttpClients.createDefault();
        TestConfig.context = HttpClientContext.create();

    }

    @Test(groups = "loginTrue",description = "用户登陆成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //第一步發送請求
        String result = getResult(loginCase);
        System.out.println("result: " + result);

        //驗證結果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    @Test(groups = "loginFalse",description = "用户登陆失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //第一步發送請求
        String result = getResult(loginCase);
        //驗證結果
        Assert.assertEquals(loginCase.getExpected(),result);
    }


    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        post.setHeader("content-type","application/json");
        System.out.println(param.toString());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        String result;
        CloseableHttpResponse response = TestConfig.defaultHttpClient.execute(post, TestConfig.context);
        try {
            //4.處理结果
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);

            //取得Cookies訊息
            TestConfig.store = TestConfig.context.getCookieStore();
            List<Cookie> cookieList = TestConfig.store.getCookies();
            for (Cookie cookie : cookieList) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println("cookie name = " + name + "; cookie value = " + value);
            }
        } finally {
            response.close();
        }
        TestConfig.store = TestConfig.context.getCookieStore();
        return  result;
    }

}
