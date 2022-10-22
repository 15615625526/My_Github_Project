package package2;

import static package2.FangFa.print;
import static package2.FangFa.sum;

public class FangFa2 {
    public static void main(String[] args) {
        //这是调用同一个二级包下,不同类下的sum方法
        int a = sum(1, 2);
        System.out.println(a);
        print("halou");
    }
}
