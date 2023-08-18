package package3;


import static package2.package21.FangFa.sums;

public class FangFa4 {
    public static void main(String[] args) {
        //这是调用同一个模块,不同包下的sums方法
        int a = sums(3, 4);
        System.out.println(a);
    }
}
