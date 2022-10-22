package package1;

import java.util.ArrayList;
import java.util.Scanner;

public class Goods {
    private int id;  //商品编号
    private String name;  //商品名称
    private double price;  //商品价格
    private int stock;  //商品库存
    private int num;  //商品在购物车中的数量
    public static ArrayList<Goods> cart = new ArrayList<>();  //用(对象)类型的集合创造一个购物车容器,集合里的各个元素就是各个商品对象
    Scanner a = new Scanner(System.in);

    public Goods() {             //无参数构造器

    }

    public Goods(int id, String name, double price, int stock) {   //有参数构造器
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public void addGoodsToCart() {  //添加指定数量的商品到购物车中
        while (true) {
            System.out.print("请输入您要添加的数量\n:");
            int num1 = a.nextInt();
            if (num1 <= stock & num1 > 0) {
                cart.add(Goods.this);
                this.num = num1;
                stock -= num1;
                System.out.println("已将" + num1 + "个\"" + name + "\"商品添加到购物车");
                break;
            } else if (num1 < 1) {
                System.err.println("您输入的是无效数量,请重新输入!");
            } else {
                System.err.println("对不起,库存不足,请减少添加到购物车中的该商品数量!");

            }
        }
    }

    public void deleteGoodsFromCart() {   //将商品从购物车中删除
        if (num > 0) {
            cart.remove(name);
            stock += num;
            this.num = 0;
            System.out.println("已将\"" + name + "\"商品从购物车中删除");
        } else {
            System.err.println("您并未添加该商品到购物车!");
        }
    }

    public void addGoodsNum() {   //将购物车中该商品的数量加1
        if (num < stock & num > 0) {
            num += 1;
            stock -= 1;
            System.out.println(name + ":+1");
        } else if (num == 0) {
            System.err.println("请先将商品添加到购物车再执行此操作!");
        } else {
            System.err.println("对不起,库存不足,您无法继续添加更多该商品!");

        }
    }


    public void subGoodsNum() {   //将购物车中该商品的数量减1
        if (num > 1) {
            num -= 1;
            stock += 1;
            System.out.println(name + ":-1");
        } else if (num == 0) {
            System.err.println("您并未添加该商品到购物车!");
        } else {
            System.err.println("对不起,已经达到最低数量,如果想要删除该商品请点击\"删除\"!");

        }
    }

    public void selectStock() {   //查看商品库存
        System.out.println("\"" + name + "\"商品目前的库存为:" + stock);
    }

    public static void goodsAmount() {  //计算购物车中的商品总额
        double goodsAmount = 0;
        for (int i = 0; i < cart.size(); i++) {
            Goods g = cart.get(i);
            goodsAmount += g.price * g.num;
        }
        System.out.println("购物车中的商品总额为:" + goodsAmount + "元");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
