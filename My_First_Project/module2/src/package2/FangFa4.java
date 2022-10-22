package package2;


import static package2.FangFa.sum;

public class FangFa4 {
    public static void main(String[] args) {
        //这是调用同一个模块,不同包下的sum方法
        int a = sum(3, 4);
        System.out.println(a);
    }
}
