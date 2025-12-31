package testsuite.suite;

import com.wanghui.LoginAPITest;
import com.wanghui.LoginQudongTest;
import com.wanghui.LoginTest;
import com.wanghui.RegisterTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectPackages("com.wanghui")
@SelectClasses({
        RegisterTest.class,
        LoginTest.class,
        LoginQudongTest.class,
        LoginAPITest.class
        // 这里添加你需要执行的测试类
})
public class JunitTests {
    // 不要写任何代码
}


