package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTest {
    private WebDriver driver; // WebDriver实例
    private Map<String, Object> config; // 存储从YAML文件读取的配置信息

    @BeforeEach   //Junit框架
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

        driver.get("http://192.168.19.137/login-register/register.php"); // 打开注册页面
        System.out.println("========================================================================");
        System.out.println("开始注册测试，访问URL: http://192.168.19.137/login-register/register.php");
        try {
            // 截取屏幕截图并保存为文件
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 指定保存截图的文件路径
            File destinationFile = new File("screenshot/screenshot.png");

            // 使用Apache Commons IO库复制截图文件到指定路径
            FileUtils.copyFile(screenshotFile, destinationFile);

            System.out.println("屏幕截图已保存到：" + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从YAML文件中读取配置信息
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        config = mapper.readValue(new File("src/test/resources/static/register.yaml"), Map.class);
    }

    public static String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

    @Test   //Junit框架
    public void testRegister() {
        // 定位用户名、密码、确认密码输入框和注册按钮元素
        WebElement usernameInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_username")));
        WebElement passwordInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_password")));
        WebElement confirmPasswordInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_confirm_password")));
        WebElement registerButton = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_button")));

        // 生成随机用户名和密码
        String randomUsername = getRandomString(8); // 长度为8的随机用户名
//        String randomUsername = "wanghui";
        String randomPassword = getRandomString(10); // 长度为10的随机密码
//        String randomPassword = "wanghui123456";

        System.out.println("找到了输入框，开始输入注册用户信息...");

        // 使用随机生成的用户名和密码填充表单
        usernameInput.clear();
        usernameInput.sendKeys(randomUsername);
        passwordInput.clear();
        passwordInput.sendKeys(randomPassword);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(randomPassword);
        registerButton.click(); // 点击注册按钮

        System.out.println("点击了注册按钮，等待显示断言信息...");

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);

            boolean textAppeared = wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(
                            By.tagName("body"), "注册成功")
            );

            System.out.println("✅ 【用例通过】页面出现期望提示: 注册成功");

        } catch (TimeoutException te) {
            // ⭐ 关键：等待超时，说明期望信息没出现
            System.out.println("❌ 【用例失败】未在规定时间内出现期望提示");
            System.out.println("👉 期望文本: 注册成功");

            // ⭐ 获取页面实际文本
            String actualText = driver.findElement(By.tagName("body")).getText();

            System.out.println("👉 页面实际文本如下：");
            System.out.println("--------------------------------------------------");
            System.out.println(actualText);
            System.out.println("--------------------------------------------------");

//            String script = "return document.documentElement.innerText;";
//            String pageText = (String) ((JavascriptExecutor) driver).executeScript(script);
//            assertTrue(pageText.contains("注册成功"), "注册成功消息未显示");
            assertTrue(false, "注册成功消息未显示");
        } catch (NoSuchElementException ne) {
            System.out.println("❌ 【严重错误】页面元素不存在: " + ne.getMessage());
            throw ne;
        }
    }

    // 测试完成后关闭浏览器并清理资源
    @AfterEach    //Junit框架
    public void tearDown() {
        driver.quit(); // 关闭WebDriver实例
    }
}

