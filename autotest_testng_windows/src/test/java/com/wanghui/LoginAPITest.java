package com.wanghui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginAPITest {

    // ✅ 日志对象
    private static final Logger log =
            LoggerFactory.getLogger(LoginAPITest.class);

    @Test  // TestNG 注解
    public void testLoginAPI() throws IOException {

        System.out.println("========================================================================");
        log.info("开始执行登录接口测试");

        String baseUrl = "http://192.168.19.136/thinksns/index.php?app=public&mod=Passport&act=doLogin";
        String referer = "http://192.168.19.136/thinksns/";
        String requestBody = "login_email=test000&login_password=123456&login_remember=1";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl);

        // 设置请求头
        httpPost.setHeader("Referer", referer);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 设置请求体
        httpPost.setEntity(new StringEntity(requestBody));

        log.info("发送登录请求，请求体：{}", requestBody);

        // 执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        // 读取响应
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(entity.getContent()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }
        reader.close();

        // 关闭资源
        response.close();
        httpClient.close();

        // 解析 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody.toString());

        log.info("接口响应内容：{}", jsonNode.toPrettyString());

        // 断言
        int status = jsonNode.get("status").asInt();
        assertEquals(status, 1, "登录不成功");

        assertTrue(
                jsonNode.get("info").asText().contains("登录成功"),
                "返回信息不包含“登录成功”"
        );

        log.info("登录接口断言通过");
    }
}
