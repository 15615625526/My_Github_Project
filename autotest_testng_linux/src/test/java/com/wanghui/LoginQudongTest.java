package com.wanghui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.*;
import java.util.Map;

public class LoginQudongTest {

    private WebDriver driver;
    private String baseUrl = "http://192.168.126.129/login-register/login.php"; // 这里替换为你的实际登录页面的 URL
    private String expectedRedirectUrl = "http://192.168.126.129/login-register/1.html"; // 这里替换为你的实际登录成功后的重定向页面的 URL
    private Map<String, Object> config;

    @BeforeTest   //Testng框架
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

        // 加载配置文件
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("static/login.yaml");
        if (configStream == null) {
            throw new FileNotFoundException("login.yaml not found in classpath");
        }
        config = mapper.readValue(configStream, Map.class);

    }

    @org.testng.annotations.Test    //Testng框架
    public void testLoginQudong() throws IOException {
        driver.get(baseUrl);

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/static/login_credentials.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8"); // 指定字符编码
        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String username = credentials[0];
                String password = credentials[1];
                String expectedMessage = credentials[2];

                String currentElement = null;
                WebElement usernameInput;
                WebElement passwordInput;
                try {
                    currentElement = "username";
                    usernameInput = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));
                    currentElement = "password";
                    passwordInput = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));
                    currentElement = "login_button";
                    WebElement loginButton = driver.findElement(By.xpath(((Map<String, String>) config.get("elements")).get(currentElement)));

                    usernameInput.sendKeys(username);
                    passwordInput.sendKeys(password);

                    loginButton.click();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("\n\nElement not found:\n\nCheck the " + currentElement + " element.\n\n" + e.getMessage());
                }

                // 检查成功消息是否显示
                String script = "return document.documentElement.innerText;";
                String pageText = (String) ((JavascriptExecutor) driver).executeScript(script);
                System.out.println(pageText);
                Assert.assertTrue(pageText.contains(expectedMessage), "登录成功消息未显示");
            }
        }
    }

    @AfterTest   //Testng框架
    public void tearDown() {
        driver.quit();
    }
}
