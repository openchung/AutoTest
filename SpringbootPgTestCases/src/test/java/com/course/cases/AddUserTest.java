package com.course.cases;

import com.course.util.DatabaseUtil;
import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",6);
        System.out.printf(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //發請求，取得結果
        String result = getResult(addUserCase);
        //驗證返回結果
        Thread.sleep(5000);
        User user = session.selectOne("addUser", addUserCase);

//        System.out.println(user.toString());
        Assert.assertEquals(addUserCase.getExpected(), result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //取得頭訊息
        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //設置cookies
        CloseableHttpResponse response = TestConfig.defaultHttpClient.execute(post, TestConfig.context);
        TestConfig.context.setCookieStore(TestConfig.store);
        String result;//存放返回结果

        result = EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println(result);
        return result;
    }
}
