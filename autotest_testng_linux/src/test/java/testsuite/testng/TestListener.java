package testsuite.testng;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class TestListener implements ITestListener {

    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;

    // 在此添加邮件发送功能的实现代码
    public void sendEmail(ITestContext context) {
        // 发件人邮箱
        String from = "3548916316@qq.com";
        // 发件人邮箱的授权码
        String authCode = "ofdnmevfsabvcjbi";
        // 收件人邮箱
        String to = "i_want_you520@163.com";

        // 邮件主题
        String subject = "TestNG Test Results";

        // 邮件正文
        // 定义一个变量以存储失败的堆栈跟踪信息或运行日志
        StringBuilder failureDetails = new StringBuilder();
        StringBuilder content = new StringBuilder();
        if (failedTests > 0) {
            content.append("测试结果: 测试失败\n");
            failureDetails.append("失败原因: \n");
            for (ITestResult failedTest : context.getFailedTests().getAllResults()) {
                failureDetails.append(failedTest.getThrowable().getMessage()).append("\n");
            }
        } else {
            content.append("测试结果: 测试通过\n");
        }
        content.append(failureDetails);
        content.append("Total tests: " + totalTests + "\n"
                + "Passed tests: " + passedTests + "\n"
                + "Failed tests: " + failedTests + "\n"
                + "Skipped tests: " + skippedTests + "\n");

        // 设置邮件服务器和端口
        String host = "smtp.qq.com";
        int port = 465;

        // 配置邮件属性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        // 创建邮箱会话
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, authCode);
            }
        });

        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content.toString());

            // 发送邮件
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }


    @Override
    public void onTestStart(ITestResult result) {
        totalTests++;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        // 在测试完成后发送邮件
        sendEmail(context);
    }
}

