package package1.package12;

public class GoodsTest {
    public static void main(String[] args) {
        Goods goods1 = new Goods(1, "Iphone1", 1000.0, 100);
        Goods goods2 = new Goods(2, "Iphone2", 2000.0, 100);
        Goods goods3 = new Goods(3, "Iphone3", 3000.0, 100);
        Goods goods4 = new Goods(4, "Iphone4", 4000.0, 100);
        Goods goods5 = new Goods(5, "Iphone5", 5000.0, 100);
        Goods goods6 = new Goods(6, "Iphone6", 6000.0, 100);
        Goods goods7 = new Goods(7, "Iphone7", 7000.0, 100);
        Goods goods8 = new Goods(8, "Iphone8", 8000.0, 100);
        Goods goods9 = new Goods(9, "Iphone9", 9000.0, 100);

        Goods.truncateTable();
        Goods.addGoods(goods1);
        Goods.addGoods(goods2);
        Goods.addGoods(goods3);
        Goods.addGoods(goods4);
        Goods.addGoods(goods5);
        Goods.addGoods(goods6);
        Goods.addGoods(goods7);
        Goods.addGoods(goods8);
        Goods.addGoods(goods9);

        Goods.goodsAmount(); //查询购物车中商品总额

        goods1.selectStock();  //查看goods1库存
        goods1.addGoodsToCart(5);   //添加goods1到购物车
        goods1.selectStock();  //查看goods1库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods2.selectStock();  //查看goods2库存
        goods2.addGoodsToCart(5);   //添加goods2到购物车
        goods2.selectStock();  //查看goods2库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods3.selectStock();  //查看goods3库存
        goods3.addGoodsToCart(5);   //添加goods3到购物车
        goods3.selectStock();  //查看goods3库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods1.addGoodsNum();  //数量+1
        goods1.addGoodsNum();
        goods1.addGoodsNum();
        goods1.addGoodsNum();
        goods1.addGoodsNum();
        goods1.addGoodsNum();
        goods1.addGoodsNum();
        goods1.subGoodsNum();//数量-1
        goods1.subGoodsNum();//数量-1
        goods1.selectStock();  //查看goods1库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods4.addGoodsNum();  //数量+1
        goods4.selectStock();  //查看goods4库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods1.deleteGoodsFromCart();
        goods1.selectStock();  //查看goods1库存
        Goods.goodsAmount();   //查询购物车中商品总额

        goods1.addGoodsToCart(5);
        goods1.addGoodsNum();
        goods1.selectStock();

        goods5.subGoodsNum();

        goods6.addGoodsToCart(5);
        goods6.subGoodsNum();
        goods6.selectStock();
    }
}
