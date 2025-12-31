package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class RegisterTest {

    private WebDriver driver;          // WebDriver 实例
    private Map<String, Object> config; // YAML 配置

    @BeforeMethod   // TestNG
    public void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://192.168.19.137/login-register/register.php");

        System.out.println("========================================================================");
        System.out.println("开始注册测试，访问URL: http://192.168.19.137/login-register/register.php");

        // 启动后截图（用于调试/报告）
        try {
            File screenshotFile =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File("screenshot/screenshot.png");
            FileUtils.copyFile(screenshotFile, destinationFile);

            System.out.println("屏幕截图已保存到：" + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 加载 YAML 配置
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        config = mapper.readValue(
                new File("src/test/resources/static/register.yaml"),
                Map.class
        );
    }

    /**
     * 生成指定长度的随机字符串
     */
    public static String getRandomString(int length) {
        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    @Test   // TestNG
    public void testRegister() {

        WebElement usernameInput = driver.findElement(
                By.xpath((String) ((Map<String, Object>) config.get("elements"))
                        .get("register_username")));

        WebElement passwordInput = driver.findElement(
                By.xpath((String) ((Map<String, Object>) config.get("elements"))
                        .get("register_password")));

        WebElement confirmPasswordInput = driver.findElement(
                By.xpath((String) ((Map<String, Object>) config.get("elements"))
                        .get("register_confirm_password")));

        WebElement registerButton = driver.findElement(
                By.xpath((String) ((Map<String, Object>) config.get("elements"))
                        .get("register_button")));

        // 生成随机用户名和密码
        String randomUsername = getRandomString(8);
//        String randomUsername = "wanghui";

        String randomPassword = getRandomString(10);
//        String randomPassword = "wanghui123456";

        System.out.println("找到了输入框，开始输入注册用户信息...");
        System.out.println("注册用户名：" + randomUsername);
        System.out.println("注册密码：" + randomPassword);

        usernameInput.clear();
        usernameInput.sendKeys(randomUsername);
        passwordInput.clear();
        passwordInput.sendKeys(randomPassword);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(randomPassword);
        registerButton.click();

        System.out.println("点击了注册按钮，等待显示断言信息...");

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.tagName("body"), "注册成功"));

            System.out.println("✅ 【用例通过】页面出现期望提示: 注册成功");

        } catch (TimeoutException te) {
            System.out.println("❌ 【用例失败】未在规定时间内出现期望提示");
            System.out.println("👉 期望文本: 注册成功");

            String actualText =
                    driver.findElement(By.tagName("body")).getText();

            System.out.println("👉 页面实际文本如下：");
            System.out.println("--------------------------------------------------");
            System.out.println(actualText);
            System.out.println("--------------------------------------------------");

            assertTrue(false, "注册成功消息未显示");
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
