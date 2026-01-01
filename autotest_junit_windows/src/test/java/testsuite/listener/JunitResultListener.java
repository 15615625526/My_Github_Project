package testsuite.listener;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import testsuite.mail.MailUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class JunitResultListener implements TestExecutionListener {

    private PrintStream originalOut;
    private PrintStream originalErr;

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private String consoleLog = "";

    private int total = 0;
    private int failed = 0;
    private StringBuilder failures = new StringBuilder();

    /**
     * ⭐ 测试计划开始：接管控制台
     */
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        originalOut = System.out;
        originalErr = System.err;

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * ⭐ 单个测试开始：统计 total
     */
    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (testIdentifier.isTest()) {
            total++;
        }
    }

    /**
     * ⭐ 单个测试结束：判断结果
     */
    @Override
    public void executionFinished(TestIdentifier testIdentifier,
                                  TestExecutionResult testExecutionResult) {

        if (!testIdentifier.isTest()) {
            return;
        }

        switch (testExecutionResult.getStatus()) {
            case SUCCESSFUL:
                System.out.println("✅ 测试通过：" + testIdentifier.getDisplayName());
                break;

            case FAILED:
                failed++; // ⭐⭐⭐ 核心修复点

                System.out.println("❌ 测试失败：" + testIdentifier.getDisplayName());

                testExecutionResult.getThrowable().ifPresent(t -> {
                    failures.append("❌ ")
                            .append(testIdentifier.getDisplayName())
                            .append("\n")
                            .append(t.getMessage())
                            .append("\n\n");

                    t.printStackTrace();
                });
                break;

            case ABORTED:
                System.out.println("⚠️ 测试中断：" + testIdentifier.getDisplayName());
                break;
        }

        // 每个测试结束后，输出日志换行
        System.out.println("\n--- 测试项: " + testIdentifier.getDisplayName() + " 完成");
        System.err.println("---" + testIdentifier.getDisplayName() + "--- 日志如上⬆\uFE0F\n");
    }

    /**
     * ⭐ 测试计划结束：恢复控制台 + 发送邮件
     */
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        // ① 恢复原始控制台输出
        System.setOut(originalOut);
        System.setErr(originalErr);

        // 强制刷新输出流
        System.out.flush();
        System.err.flush();

        // ② 汇总完整控制台日志
        consoleLog = outContent.toString() + "\n\uD83D\uDCCC以下为失败详细日志:\n============================\n" + errContent.toString();

        // ③ 构建并发送报告
        String reportContent = buildReport();

        MailUtil.sendEmailBy587(
                "3548916316@qq.com",
                "wviixqctsutudbfd",
                "i_want_you520@163.com",
                "JUnit—Jenkins-Windows 测试报告",
                reportContent
        );
    }

    /**
     * ⭐ 构建邮件内容
     */
    public String buildReport() {
        return "JUnit—Jenkins-Windows测试报告\n"
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
