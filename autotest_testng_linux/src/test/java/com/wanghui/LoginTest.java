package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class LoginTest {

    private WebDriver driver;
    private String baseUrl = "http://192.168.19.137/login-register/login.php";
    private String expectedRedirectUrl = "http://192.168.19.137/login-register/1.html";
    private Map<String, Object> config;

    @BeforeMethod   // TestNG
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
        driver.manage().window().maximize();  // 最大化窗口

        // 加载 YAML 配置
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configStream =
                getClass().getClassLoader().getResourceAsStream("static/login.yaml");

        if (configStream == null) {
            throw new FileNotFoundException("login.yaml not found in classpath");
        }
        config = mapper.readValue(configStream, Map.class);
    }

    @Test  // TestNG
    public void testLogin() {
        driver.get(baseUrl);
        System.out.println("========================================================================");
        System.out.println("开始登录测试，访问URL: " + baseUrl);

        String currentElement = null;
        try {
            currentElement = "username";
            WebElement usernameInput = driver.findElement(
                    By.xpath(((Map<String, String>) config.get("elements"))
                            .get(currentElement)));

            currentElement = "password";
            WebElement passwordInput = driver.findElement(
                    By.xpath(((Map<String, String>) config.get("elements"))
                            .get(currentElement)));

            currentElement = "login_button";
            WebElement loginButton = driver.findElement(
                    By.xpath(((Map<String, String>) config.get("elements"))
                            .get(currentElement)));

            System.out.println("找到了输入框，开始输入用户名和密码...");

            usernameInput.sendKeys(
                    ((Map<String, String>)
                            ((Map<String, Object>) config.get("credentials"))
                                    .get("user1")).get("name"));

            passwordInput.sendKeys(
                    ((Map<String, String>)
                            ((Map<String, Object>) config.get("credentials"))
                                    .get("user1")).get("password"));

            loginButton.click();
            System.out.println("点击了登录按钮，等待显示断言信息...");

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "\n\nElement not found:\n\nCheck the " +
                            currentElement + " element.\n\n" + e.getMessage());
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.tagName("body"), "登录成功"));

            System.out.println("✅ 【用例通过】页面出现期望提示: 登录成功");

        } catch (TimeoutException te) {
            System.out.println("❌ 【用例失败】未在规定时间内出现期望提示");
            System.out.println("👉 期望文本: 登录成功");

            String actualText =
                    driver.findElement(By.tagName("body")).getText();

            System.out.println("👉 页面实际文本如下：");
            System.out.println("--------------------------------------------------");
            System.out.println(actualText);
            System.out.println("--------------------------------------------------");

            assertTrue(false, "登录成功消息未显示");
        } catch (NoSuchElementException ne) {
            System.out.println("❌ 【严重错误】页面元素不存在: " + ne.getMessage());
            throw ne;
        }
    }

    @AfterMethod   // TestNG
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
