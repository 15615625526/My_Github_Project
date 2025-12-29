package module2.src.package3;


import static module2.src.package2.package21.FangFa.sums;

public class FangFa4 {
    public static void main(String[] args) {
        //这是调用同一个模块,不同包下的sums方法
        int a = sums(3, 4);
        System.out.println(a);
    }
}
