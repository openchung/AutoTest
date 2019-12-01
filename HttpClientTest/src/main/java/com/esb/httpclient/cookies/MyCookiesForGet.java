package com.esb.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

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
    private void testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");
        String testUrl = this.url + uri;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet(testUrl);

        //設置cookie訊息
        context.setCookieStore(this.cookieStore);
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try {
            //取得響應狀態碼
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("StatusCode = " + statusCode);
            if(statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            } else {

            }
        } finally {
            response.close();
        }
    }

}
