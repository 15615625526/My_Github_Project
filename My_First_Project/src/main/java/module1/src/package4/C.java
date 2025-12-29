package module1.src.package4;

public class C extends B {
    public static void main(String[] args) {
        C c = new C();
        B b = new B();
        System.out.println((c.x));
        System.out.println((c.y));
        System.out.println(c.z);
        System.out.println((b.x));
        System.out.println((b.y));
        System.out.println(b.z);
        int result1 = c.sums(4, 5);
        int result2 = b.sums(4, 5);
        int result3 = sums(4, 5);
        int result4 = sums(4, 5);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }
}
