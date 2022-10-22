package package2;
//=============================除了基本数据类型还有引用数据类型,字符串和数组都是引用数据类型=============================

import java.util.Scanner;

public class FangFa {
    //定义一个求和功能的方法:
    public static int sum(int a, int b) {
        int c = a + b;
        return c;
        //return后面不能再有代码了,因为永远执行不到
    }

    public static void print(String a) {
        System.out.println(a);
        System.out.println(a);
        System.out.println(a);
    }

    public static void fangFaQianTao(String a, String b) {
        System.out.println(a);
        print(b);
    }

    public static int qiuHe(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static void jiOu(int a) {
        if (a % 2 == 0) {
            System.out.println("此数为偶数!");
        } else {
            System.out.println("此数为奇数!");
        }
    }

    public static int shuZuMax(int[] arg) {
        int max = arg[0];
        for (int i = 0; i < arg.length; i++) {
            if (arg[i] > max) {
                max = arg[i];
            }
        }
        return max;
    }

    public static void change(int a) {//=======================注意!===========================
        System.out.println(a);
        a = 20;
        System.out.println(a);
    }

    public static void change2(int[] arr) {//===================注意============================
        System.out.println(arr[0]);
        arr[0] = 100;
        System.out.println(arr[0]);
    }

    public static void printArray(int[] arr) {//=========================构造一个打印数组的方法=========================
        if (arr.length == 0) {
            System.out.println("要打印的数组为:{}");
        } else {
            System.out.print("要打印的数组为:{");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]);
                if (i == arr.length - 1) {
                    System.out.print("}");
                    break;
                } else {
                    System.out.print(", ");
                }
            }
        }
    }//也可以用三元运算符来做

    public static int printIndex(int[] arr, int b) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == b) {
                return i;
            }
        }
        return -1;
    }

    public static boolean panDuan(int[] a, int[] b) {
        if (a.length == b.length) {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    //在main方法中调用上面定义过的方法:
    public static void main(String[] args) {
        //这是调用同一个类中的sum方法
        int a = sum(1, 2);
        System.out.println(a);
        print("haha");//没有返回值的方法直接调用即可,不需要用其他变量来接它
//        String b = print("haah");//注意这样写是不对的,因为print方法是没有return返回值的,所以不能用一个字符串b来接它
        fangFaQianTao("haha", "heihei");
        System.out.println(qiuHe(5));
        jiOu(9);
        System.out.println(shuZuMax(new int[]{34, 12, 56, 36, 78, 27, 89, 35, 66}));//注意数组传参必须初始化数组,用new int[]
        //也可以在调用之前先初始化一个数组,在进行调用,eg:
        int[] arrgy = {1, 4, 7, 6, 5, 9, 3};
        System.out.println(shuZuMax(arrgy));
        System.out.println("========================下面的需要注意!============================");
        int b = 10;
        change(b);//b仅仅只是在栈内存中的change方法中变成了20,change方法跑完后移出栈内存,栈内存中的main方法中b仍然是10
        System.out.println(b);
        int[] arr = {12, 13, 14};
        change2(arr);//数组通过change2方法改变了堆内存中对象的数据,所以即使数组栈内存中的地址没有变化,但是堆内存中的对象是改变了的,通过地址寻找到的对象就是变化了的
        //虽然字符串和数组都是引用数据类型,但是打印字符串就是打印的字符串,打印数组确实打印的数组的地址!==================如下:
        System.out.println(arr[0]);
        String aaa = "nihaoa zhongguo";
        System.out.println(aaa);
        int[] bbb = {12, 13, 14, 15, 16};
        System.out.println(bbb);
        System.out.println("======================================================");
        int[] abc = {1, 2, 3, 4, 5, 6};
        printArray(abc);
        int[] ccc = {};
        System.out.println();
        printArray(ccc);
        System.out.println("===================================");
        int[] m = {34, 7, 8, 56, 4, 90};
        printIndex(m, 56);//注意:这样只是把方法跑一下,return的结果不会打印,必须用变量接一下打印
        int h = printIndex(m, 3);
        System.out.println(h);
        int[] aaaa = {1, 2, 3, 4, 5, 6};
        int[] bbbb = {1, 2, 3, 4, 5, 6};
        System.out.println(panDuan(aaaa, bbbb));
        System.err.println("这是什么意思?");//===========这是输出错误提示=================
        System.err.println();//=============这是输出错误提示的意思======================用红色打印出来
    }
}

