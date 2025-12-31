package testsuite.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testsuite.mail.MailUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestngResultListener implements ITestListener {

    private PrintStream originalOut;
    private PrintStream originalErr;

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private String consoleLog = "";

    private int total = 0;
    private int failed = 0;
    private StringBuilder failures = new StringBuilder();

    /**
     * ⭐ TestNG 测试套开始：接管控制台
     */
    @Override
    public void onStart(ITestContext context) {
        originalOut = System.out;
        originalErr = System.err;

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * ⭐ 单个测试开始
     */
    @Override
    public void onTestStart(ITestResult result) {
        total++;
    }

    /**
     * ⭐ 测试成功
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ 测试通过：" + result.getMethod().getMethodName());
        System.out.println("\n--- 测试项: " + result.getMethod().getMethodName() + " 完成");
        System.err.println("---" + result.getMethod().getMethodName() + "--- 日志如上⬆️\n");
    }

    /**
     * ⭐ 测试失败
     */
    @Override
    public void onTestFailure(ITestResult result) {
        failed++;

        System.out.println("❌ 测试失败：" + result.getMethod().getMethodName());

        Throwable t = result.getThrowable();
        if (t != null) {
            failures.append("❌ ")
                    .append(result.getMethod().getMethodName())
                    .append("\n")
                    .append(t.getMessage())
                    .append("\n\n");

            t.printStackTrace();
        }

        System.out.println("\n--- 测试项: " + result.getMethod().getMethodName() + " 完成");
        System.err.println("---" + result.getMethod().getMethodName() + "--- 日志如上⬆️\n");
    }

    /**
     * ⭐ 测试跳过（等价于 JUnit ABORTED）
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ 测试跳过：" + result.getMethod().getMethodName());
    }

    /**
     * ⭐ TestNG 测试套结束：恢复控制台 + 发送邮件
     */
    @Override
    public void onFinish(ITestContext context) {
        // ① 恢复原始控制台
        System.setOut(originalOut);
        System.setErr(originalErr);

        System.out.flush();
        System.err.flush();

        // ② 汇总控制台日志
        consoleLog = outContent.toString() + "\n\uD83D\uDCCC以下为失败详细日志:\n============================\n" + errContent.toString();

        // ③ 构建并发送报告
        String reportContent = buildReport();

        MailUtil.sendEmailBy587(
                "3548916316@qq.com",
                "wviixqctsutudbfd",
                "i_want_you520@163.com",
                "TestNG 测试报告",
                reportContent
        );
    }

    /**
     * ⭐ 构建邮件内容（完全保持原样）
     */
    public String buildReport() {
        return "TestNG测试报告\n"
                + "============================\n"
                + "总计: " + total + "\n"
                + "失败: " + failed + "\n"
                + "成功: " + (total - failed) + "\n\n"
                + "【失败详情】\n"
                + (failures.length() == 0 ? "无\n\n" : failures.toString())
                + "============================\n"
                + "【控制台完整日志】\n"
                + consoleLog;
    }
}
