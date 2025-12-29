package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class LoginTest {

    private WebDriver driver;
    private String baseUrl = "http://192.168.126.129/login-register/login.php"; // 这里替换为你的实际登录页面的 URL
    private String expectedRedirectUrl = "http://192.168.126.129/login-register/1.html"; // 这里替换为你的实际登录成功后的重定向页面的 URL
    private Map<String, Object> config;

    @Before   //Junit框架
    public void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        // 设置浏览器参数
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); //无头浏览器，这样不会打开浏览器窗口
//        chromeOptions.addArguments("--no-sandbox");//禁用沙箱
//        chromeOptions.addArguments("--disable-dev-shm-usage");//禁用开发者shm
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--remote-debugging-port=9222");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize(); //窗口最大化

        // 加载配置文件
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("static/login.yaml");
        if (configStream == null) {
            throw new FileNotFoundException("login.yaml not found in classpath");
        }
        config = mapper.readValue(configStream, Map.class);

    }

    @Test  //Junit框架
    public void testLogin() {
        driver.get(baseUrl);

        String currentElement = null;
        try {
            currentElement = "username";
            WebElement usernameInput = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));
            currentElement = "password";
            WebElement passwordInput = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));
            currentElement = "login_button";
            WebElement loginButton = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));

            usernameInput.sendKeys(((Map<String, String>) ((Map<String, Object>) config.get("credentials")).get("user1")).get("name"));
            passwordInput.sendKeys(((Map<String, String>) ((Map<String, Object>) config.get("credentials")).get("user1")).get("password"));

            loginButton.click();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("\n\nElement not found:\n\nCheck the " + currentElement + " element.\n\n" + e.getMessage());
        }

        // 检查成功消息是否显示
        String script = "return document.documentElement.innerText;";
        String pageText = (String) ((JavascriptExecutor) driver).executeScript(script);
        System.out.println(pageText);
        assertTrue(pageText.contains("登录成功"), "登录成功消息未显示");
    }

    @After   //Junit框架
    public void tearDown() {
        driver.quit();
    }
}
