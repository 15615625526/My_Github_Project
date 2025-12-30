package com.wanghui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAPITest {

    // ✅ ① 日志对象放在这里（类成员位置）
    private static final Logger log =
            LoggerFactory.getLogger(LoginAPITest.class);

    @Test  //Junit框架
    public void testLoginAPI() throws IOException {

        System.out.println("========================================================================");
        log.info("开始执行登录接口测试");

        String baseUrl = "http://192.168.19.136/thinksns/index.php?app=public&mod=Passport&act=doLogin";
        String referer = "http://192.168.19.136/thinksns/";
        String requestBody = "login_email=test000&login_password=123456&login_remember=1";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl);

        // Set request headers
        httpPost.setHeader("Referer", referer);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // Set request body
        httpPost.setEntity(new StringEntity(requestBody));

        log.info("发送登录请求，请求体：{}", requestBody);

        // Execute the request and get the response
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        // Read the response body
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }
        reader.close();

        // Close resources
        response.close();
        httpClient.close();

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody.toString());

        log.info("接口响应内容：{}", jsonNode.toPrettyString());

        // Check if login status is successful
        int status = jsonNode.get("status").asInt();
        assertEquals(1, status, "登录不成功");

        log.info("登录接口断言通过");
        assertTrue(jsonNode.get("info").asText().contains("登录成功"));

    }

}

