package package2;

import static java.lang.Integer.sum;

public class FangFa6 {
    public static void main(String[] args) {
        //这是调用不同工程下的sum方法
        int a = sum(1, 2);
        System.out.println(a);
    }
}
