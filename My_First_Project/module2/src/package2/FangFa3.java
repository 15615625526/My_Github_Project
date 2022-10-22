package package2;


import static package2.FangFa.sum;

public class FangFa3 {
    public static void main(String[] args) {
        //这是调用同一个包,不同二级包下的sum方法
        int a = sum(2, 3);
        System.out.println(a);
    }
}
