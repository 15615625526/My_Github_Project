package package5;

public class FangFaChongZai {
    public static void main(String[] args) {
        fire(12, "美国");
        fire("美国", 12);
    }

    public static void fire() {
        System.out.println("默认发射一枚导弹");
        fire(12, "日本");//重载方法中可以调用重载方法
    }

    public static void fire(String location) {
        System.out.println("发射给" + location);
    }

    public static void fire(String location, int number) {
        fire(12, "美国");
    }

//    public static void fire(String loca, int num) {//注意这个方法不是重载方法,而是重复方法,只是形参名称不同而已==========
//
//    }

    public static void fire(int number, String location) {
        System.out.println("发射给" + location + number + "枚导弹");
    }
}
