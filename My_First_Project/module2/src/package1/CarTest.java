package package1;

public class CarTest {
    public static void main(String[] args) {
        Car c1 = new Car();
        c1.name = "奔驰";
        c1.price = 31.56;
        System.out.println(c1.name);
        System.out.println(c1.price);
        c1.start();
        c1.run();

        Car c2 = new Car();
        c2.name = "保时捷";
        c2.price = 100.45;
        System.out.println(c2.name);
        System.out.println(c2.price);
        c2.start();
        c2.run();
    }
}
