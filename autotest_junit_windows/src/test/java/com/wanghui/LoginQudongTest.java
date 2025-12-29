package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.util.Map;

public class LoginQudongTest {

    private WebDriver driver;
    private String baseUrl = "http://192.168.19.137/login-register/login.php"; // 这里替换为你的实际登录页面的 URL
    private String expectedRedirectUrl = "http://192.168.19.137/login-register/1.html"; // 这里替换为你的实际登录成功后的重定向页面的 URL
    private Map<String, Object> config;

    @BeforeEach  //Junit框架
    public void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe"); // 适用于本地运行
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\wanghui\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"); // 适用于jenkins中运行
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 加载配置文件
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("static/login.yaml");
        if (configStream == null) {
            throw new FileNotFoundException("login.yaml not found in classpath");
        }
        config = mapper.readValue(configStream, Map.class);

    }

    @Test  //Junit框架
    public void testLoginQudong() throws IOException {

        int failCount = 0;
        int passCount = 0;
        int totalCount = 0;

        driver.get(baseUrl);
        System.out.println("========================================================================");
        System.out.println("开始登录驱动测试，访问URL: " + baseUrl);

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/static/login_credentials.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            br.readLine(); // 跳过表头
            while ((line = br.readLine()) != null) {
                totalCount++;
                String[] credentials = line.split(",");
                String username = credentials[0];
                String password = credentials[1];
                String expectedMessage = credentials[2];

                System.out.println("\n---------- 执行第 " + totalCount + " 条用例 ----------");
                System.out.println("用户名:" + username + ", 密码:" + password + ", 断言:" + expectedMessage);

                try {
                    // 等待并获取输入框和按钮元素
                    WebElement usernameInput = waitForElement("username");
                    WebElement passwordInput = waitForElement("password");
                    WebElement loginButton = waitForElement("login_button");

                    System.out.println("找到了输入框，开始输入用户名和密码...");

                    usernameInput.clear();
                    passwordInput.clear();
                    usernameInput.sendKeys(username);
                    passwordInput.sendKeys(password);
                    loginButton.click();

                    System.out.println("点击了登录按钮，等待显示断言信息...");

                    try {
                        WebDriverWait wait = new WebDriverWait(driver, 1);

                        boolean textAppeared = wait.until(
                                ExpectedConditions.textToBePresentInElementLocated(
                                        By.tagName("body"), expectedMessage)
                        );

                        System.out.println("✅ 【用例通过】页面出现期望提示: " + expectedMessage);
                        passCount++;

                    } catch (TimeoutException te) {
                        // ⭐ 关键：等待超时，说明期望信息没出现
                        System.out.println("❌ 【用例失败】未在规定时间内出现期望提示");
                        System.out.println("👉 期望文本: " + expectedMessage);

                        // ⭐ 获取页面实际文本
                        String actualText = driver.findElement(By.tagName("body")).getText();

                        System.out.println("👉 页面实际文本如下：");
                        System.out.println("--------------------------------------------------");
                        System.out.println(actualText);
                        System.out.println("--------------------------------------------------");
                        failCount++;

                    } catch (NoSuchElementException ne) {
                        System.out.println("❌ 【严重错误】页面元素不存在: " + ne.getMessage());
                        throw ne;
                    }


                } catch (NoSuchElementException e) {
                    System.out.println("❌ 未找到元素: " + e.getMessage());
                    throw new NoSuchElementException("登录过程中未找到元素: " + e.getMessage());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n========== 测试执行完毕 ==========");
        System.out.println("总用例数: " + totalCount);
        System.out.println("通过用例数: " + passCount);
        System.out.println("失败用例数: " + failCount);

        if (failCount > 0) {
            fail("登录测试存在失败用例，共失败 " + failCount + " 条");
        }
    }

    private WebElement waitForElement(String elementKey) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(((Map<String, String>) config.get("elements")).get(elementKey))));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element not found after waiting: " + elementKey);
        }
    }

    @AfterEach   //Junit框架
    public void tearDown() {
        driver.quit();
    }
}
