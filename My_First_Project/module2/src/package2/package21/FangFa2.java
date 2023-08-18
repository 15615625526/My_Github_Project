package package2.package21;

import static package2.package21.FangFa.sums;

public class FangFa2 {
    public static void main(String[] args) {
        //这是调用同一个二级包下,不同类下的sums方法
        int a = sums(1, 2);
        System.out.println(a);
    }
}
