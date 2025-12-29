package testsuite.junit;

import com.wanghui.LoginAPITest;
import com.wanghui.LoginQudongTest;
import com.wanghui.LoginTest;
import com.wanghui.RegisterTest;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegisterTest.class,
        LoginTest.class,
        LoginQudongTest.class,
        LoginAPITest.class
        // 这里添加你需要执行的测试类
})

@ExtendWith(AllureJunit5.class)
public class JunitTests {

    public static void main(String[] args) {
        TestResultListener listener = new TestResultListener();
        JUnitCore core = new JUnitCore();
        core.addListener(listener);
        core.run(JunitTests.class);
        sendTestReportByEmail(listener.getTestReport());
    }

    public static void sendTestReportByEmail(String testReportContext) {
        String from = "3548916316@qq.com"; // 发件人邮箱地址
        String to = "i_want_you520@163.com"; // 收件人邮箱地址
        String host = "smtp.qq.com"; // SMTP服务器地址
        String port = "465"; // SMTP服务器端口
        String password = "ofdnmevfsabvcjbi"; // 发件人邮箱密码

        // 发送邮件
        sendEmail(from, to, host, port, password, testReportContext);
    }

    private static void sendEmail(String from, String to, String host, String port, String password, String testReportContext) {
        // 设置邮件属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");

        // 获取默认的Session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 创建MimeMessage对象
            MimeMessage message = new MimeMessage(session);

            // 设置发件人和收件人
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // 设置邮件主题和正文
            message.setSubject("JUnit测试报告");
            message.setText(testReportContext);

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件已发送");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

@ExtendWith(AllureJunit5.class)
class TestResultListener extends RunListener {
    private StringBuilder testReport = new StringBuilder();
    private StringBuilder failureReasons = new StringBuilder();
    private boolean allTestsPassed = true;

    @Override
    public void testRunFinished(Result result) throws Exception {
        int successfulTests = result.getRunCount() - result.getFailureCount();

        if (allTestsPassed) {
            testReport.append("Test Result: Passed\n\n\n");
        } else {
            testReport.append("Test Result: Failed\n\n\n");
            testReport.append(failureReasons).append("\n\n");
        }
        testReport.append("Total Test Cases: ").append(result.getRunCount()).append("\n");
        testReport.append("Successful Test Cases: ").append(successfulTests).append("\n");
        testReport.append("Failed Test Cases: ").append(result.getFailureCount()).append("\n");
        testReport.append("Ignored Test Cases: ").append(result.getIgnoreCount()).append("\n");
        testReport.append("Total Run Time: ").append(result.getRunTime()).append("ms\n");
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        allTestsPassed = false;
        failureReasons.append("Failure Reason: ").append(failure.toString()).append("\n");
    }

    public String getTestReport() {
        return testReport.toString();
    }
}



