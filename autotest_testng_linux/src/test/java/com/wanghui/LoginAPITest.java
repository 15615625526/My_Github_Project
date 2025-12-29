package com.wanghui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

public class LoginAPITest {

    @Test  //Testng框架
    public void testLoginAPI() throws IOException {
        String baseUrl = "http://192.168.126.132/thinksns/index.php?app=public&mod=Passport&act=doLogin";
        String referer = "http://192.168.126.132/thinksns/";
        String requestBody = "login_email=test000&login_password=123456&login_remember=1";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl);

        // Set request headers
        httpPost.setHeader("Referer", referer);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // Set request body
        httpPost.setEntity(new StringEntity(requestBody));

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

        // Format and print the JSON response
        String formattedResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        System.out.println("Formatted Response Body:");
        System.out.println(formattedResponse);

        // Check if login status is successful
        int status = jsonNode.get("status").asInt();
        Assert.assertEquals(status, 1, "Login was not successful");

        // Extract and print info and data
        String info = jsonNode.get("info").asText();
        String data = jsonNode.get("data").asText();
        System.out.println("Info: " + info);
        System.out.println("Data: " + data);

        // Decode the response body
        String decodedInfo = URLDecoder.decode(jsonNode.get("info").toString(), "UTF-8");

        System.out.println("Actual Response Body: " + decodedInfo);
        // Verify if "登录成功" is present in the decoded response body
        Assert.assertTrue(decodedInfo.contains("登录成功"));
        Assert.assertTrue(formattedResponse.contains("登录成功"));

    }

}

