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

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegisterTest.class,
        LoginTest.class,
        LoginQudongTest.class,
        LoginAPITest.class
        // 这里添加你需要执行的测试类
})

@ExtendWith(AllureJunit5.class)
public class Junit2Tests {

    public static void main(String[] args) {
        JUnitCore core = new JUnitCore();
        core.run(Junit2Tests.class);
    }

}



