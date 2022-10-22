package package2;

import static java.lang.Integer.sum;//注意:调用不同模块下的方法导入的时候是这样的

public class FangFa5 {
    public static void main(String[] args) {
        //这是调用不同模块下的sum方法
        int a = sum(5, 6);
        System.out.println(a);
    }
}
