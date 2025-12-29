package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class RegisterTest {
    private WebDriver driver; // WebDriver实例
    private Map<String, Object> config; // 存储从YAML文件读取的配置信息

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

        driver.get("http://192.168.126.129/login-register/register.php"); // 打开注册页面
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
        String randomPassword = getRandomString(10); // 长度为10的随机密码

        // 使用随机生成的用户名和密码填充表单
        usernameInput.clear();
        usernameInput.sendKeys(randomUsername);
        passwordInput.clear();
        passwordInput.sendKeys(randomPassword);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(randomPassword);
        registerButton.click(); // 点击注册按钮

        // 检查成功消息是否显示
        String script = "return document.documentElement.innerText;";
        String pageText = (String) ((JavascriptExecutor) driver).executeScript(script);
        System.out.println(pageText);
        assertTrue(pageText.contains("注册成功"), "注册成功消息未显示");

    }

    // 测试完成后关闭浏览器并清理资源
    @After    //Junit框架
    public void tearDown() {
        driver.quit(); // 关闭WebDriver实例
    }
}

