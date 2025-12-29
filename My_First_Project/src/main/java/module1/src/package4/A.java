package module1.src.package4;

public class A {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        int h = b.x;
        int j = b.y;
        int n = b.z;
        int m = b.sums(2, 3);
        System.out.println(h);
        System.out.println(j);
        System.out.println(n);
        System.out.println(m);
    }
}
