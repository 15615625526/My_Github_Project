package testsuite.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    /**
     * 使用 QQ 邮箱 SMTP 587 端口发送邮件（推荐）
     */
    public static void sendEmailBy587(String from,
                                      String authCode,
                                      String to,
                                      String subject,
                                      String content) {

        Properties props = new Properties();

        // ===== SMTP 基本配置 =====
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        // ===== 587 必须：STARTTLS =====
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        // ===== 调试（出问题时打开）=====
        // props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, authCode);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            // 发件人
            message.setFrom(new InternetAddress(from));

            // 收件人
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // 主题
            message.setSubject(subject, "UTF-8");

            // 正文
            message.setText(content, "UTF-8");

            // 发送
            Transport.send(message);

            System.out.println("✅ 邮件发送成功（587）");

        } catch (MessagingException e) {
            System.err.println("❌ 邮件发送失败（587）");
            e.printStackTrace();
        }
    }
}
