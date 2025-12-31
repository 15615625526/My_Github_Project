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

import java.io.*;
import java.util.Map;

import static org.testng.Assert.fail;

public class LoginQudongTest {

    private WebDriver driver;
    private String baseUrl = "http://192.168.19.137/login-register/login.php";
    private String expectedRedirectUrl = "http://192.168.19.137/login-register/1.html";
    private Map<String, Object> config;

    @BeforeMethod  // TestNG
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
    public void testLoginQudong() {

        int failCount = 0;
        int passCount = 0;
        int totalCount = 0;

        driver.get(baseUrl);
        System.out.println("========================================================================");
        System.out.println("开始登录驱动测试，访问URL: " + baseUrl);

        try (
                FileInputStream fileInputStream =
                        new FileInputStream("src/test/resources/static/login_credentials.csv");
                InputStreamReader inputStreamReader =
                        new InputStreamReader(fileInputStream, "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader)
        ) {

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
                    WebElement usernameInput = waitForElement("username");
                    WebElement passwordInput = waitForElement("password");
                    WebElement loginButton = waitForElement("login_button");

                    usernameInput.clear();
                    passwordInput.clear();
                    usernameInput.sendKeys(username);
                    passwordInput.sendKeys(password);
                    loginButton.click();

                    try {
                        WebDriverWait wait = new WebDriverWait(driver, 1);
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                                By.tagName("body"), expectedMessage));

                        System.out.println("✅ 【用例通过】页面出现期望提示: " + expectedMessage);
                        passCount++;

                    } catch (TimeoutException te) {
                        System.out.println("❌ 【用例失败】未在规定时间内出现期望提示");
                        System.out.println("👉 期望文本: " + expectedMessage);

                        String actualText =
                                driver.findElement(By.tagName("body")).getText();

                        System.out.println("👉 页面实际文本：");
                        System.out.println("--------------------------------------------------");
                        System.out.println(actualText);
                        System.out.println("--------------------------------------------------");
                        failCount++;
                    }

                } catch (NoSuchElementException e) {
                    System.out.println("❌ 未找到元素: " + e.getMessage());
                    throw e;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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
                    By.xpath(((Map<String, String>) config.get("elements"))
                            .get(elementKey))));
        } catch (TimeoutException e) {
            throw new NoSuchElementException(
                    "Element not found after waiting: " + elementKey);
        }
    }

    @AfterMethod  // TestNG
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
