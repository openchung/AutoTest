package com.esb.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {

    private String url;
    private ResourceBundle bundle;
    //用來存儲cookies訊息的變量
    private CookieStore cookieStore;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINESE);
        url = bundle.getString("test.url");
    }

    @Test
    private void testGetCookies() throws IOException {
        String result;
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet(testUrl);

        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try {
            //4.處理结果
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);

            //取得Cookies訊息
            this.cookieStore = context.getCookieStore();
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie cookie : cookieList) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println("cookie name = " + name + "; cookie value = " + value);
            }

        } finally {
            response.close();
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException {
        HttpClientContext context = HttpClientContext.create();

        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = this.url + uri;

        //聲明一個client對象，用來進行方法的執行
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //聲明一個方法，這個方法就是post方法
        HttpPost httpPost = new HttpPost(testUrl);

        //添加參數
        JSONObject param = new JSONObject();
        param.put("name", "danniel");
        param.put("age", "18");

        //設置請求頭訊息、設置Header
        httpPost.setHeader("content-type","application/json");

        //將參數訊息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        httpPost.setEntity(entity);

        //聲明一個對象來進行響應結果的存儲
        String result;

        //設置Cookies訊息、執行Post方法
        //將返回的響應結果字符串轉化成json對象
        //具體的判斷返回結果的值
        //或取道結果值
        CloseableHttpResponse response = httpclient.execute(httpPost, context);
        context.setCookieStore(this.cookieStore);
        try {
            //取得響應狀態碼
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("StatusCode = " + statusCode);
            if(statusCode == 200) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
                JSONObject resultJson = new JSONObject(result);
                String success = (String) resultJson.get("danniel");
                String status = (String) resultJson.get("status");
                Assert.assertEquals("success", success);
                Assert.assertEquals("1", status);
            } else {

            }


        } finally {
            response.close();
        }



        //獲取響應結果

        //處理結果，就是判斷返回結果是否符合預期

    }
}
