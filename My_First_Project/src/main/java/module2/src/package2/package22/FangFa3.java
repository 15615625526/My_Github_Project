package module2.src.package2.package22;


import static module2.src.package2.package21.FangFa.sums;

public class FangFa3 {
    public static void main(String[] args) {
        //这是调用同一个包,不同二级包下的sums方法
        int a = sums(2, 3);
        System.out.println(a);
    }
}
