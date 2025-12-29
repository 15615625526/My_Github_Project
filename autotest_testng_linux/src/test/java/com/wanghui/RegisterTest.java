package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class RegisterTest {
    private WebDriver driver; // WebDriver实例
    private Map<String, Object> config; // 存储从YAML文件读取的配置信息

    @BeforeTest   //Testng框架
    public void setUp() throws IOException {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver"); // 指定GeckoDriver的路径
        // 设置Firefox浏览器参数
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true); // 无头模式
//        firefoxOptions.addArguments("--disable-gpu");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();

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

    @org.testng.annotations.Test   //Testng框架
    public void testRegister() throws InterruptedException {
        // 定位用户名、密码、确认密码输入框和注册按钮元素
        WebElement usernameInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_username")));
        WebElement passwordInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_password")));
        WebElement confirmPasswordInput = driver.findElement(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_confirm_password")));

        Thread.sleep(1000);  //等待1秒

        //        创建一个WebDriverWait实例，设置等待时间为5秒
        WebDriverWait wait = new WebDriverWait(driver, 5);
//        使用ExpectedConditions的visibilityOfElementLocated方法等待元素出现
        WebElement registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((String) ((Map<String, Object>) config.get("elements")).get("register_button"))));

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
        Assert.assertTrue(pageText.contains("注册成功"), "注册成功消息未显示");

    }

    // 测试完成后关闭浏览器并清理资源
    @AfterTest    //Testng框架
    public void tearDown() {
        driver.quit(); // 关闭WebDriver实例
    }
}

