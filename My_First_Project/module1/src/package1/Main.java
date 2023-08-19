package package1;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------基本功能-----------------------------------------");
        /**
         java SE标准版  EE企业版  ME小型版  LTS长期支持版
         ASCII编码中,'a'用97表示,'A'用65表示
         基本数据类型:
         byte   1个字节      默认值为0    -128~127,不能表示128
         short  2个字节      默认值为0
         int    4个字节      默认值为0
         long   8个字节      默认值为0
         float  4个字节      默认值为0.0
         double 8个字节      默认值为0.0
         char   2个字节      默认值为Unicode 字符 '\u0000'，也就是空字符
         boolean  1个字节    默认值为false
         引用数据类型:
         String,类,接口,数组    默认值为null
         char类型' '两个单引号之间不能什么都没有,至少要有一个空格;String类型""两个双引号之间可以什么都没有
         */

        System.out.println("=========================字符串去头尾空格===========================");
        String temp = "  aa  bb  cc  ";
        String temp2 = temp.trim();
        System.out.println(temp);
        System.out.println(temp2);

        String zifuchuan = "nihaoawoshiwanghui";
        int len = zifuchuan.length();//表示字符串的长度
        System.out.println(len);
//        char cha = zifuchuan[1];   这样是不对的,字符串不能用索引
//        String t = 100;   这样是不对的,String类型必须加双引号

        int a1 = 2;
        int a2 = 2;
        String b1 = new String("halou");
        String b2 = new String("halou");  //引用数据类型的定义方式
        String c1 = "halou";   //字面量的定义方式
        String c2 = "halou";
        String[] d1 = {"halou"};
        String[] d2 = {"halou"};
        System.out.println(a1 == a2);  //true
        System.out.println(b1 == b2);  //false,此处对比的是b1和b2的内存地址,new出来的内存地址就是不同的,即使内容是相同的
        System.out.println(c1 == c2);  //true,字面量的方式定义的,内存地址是相同的
        System.out.println(b1 == c1);  //false
        System.out.println(d1 == d2);  //false,此处对比的是两个数组的内存地址,是不同的
        System.out.println(b1.equals(b2));  //true,此处对比的是b1和b2的内容,而不是内存地址
        System.out.println("hello" == "hello");  //true
        System.out.println("hello".equals("hello"));  //true


//        "hello world".sout  回车(字符串必须加引号)
        System.out.println("hello world");
//        67.8.sout  回车(数字不用加引号)
        System.out.println(67.8);
//        'a'.sout  字符可以使用单引号,也可以使用双引号,但是单引号里面必须是一个字符
//        "a".sout  字符也可以用双引号
        System.out.println("a");

//        \n代表换行
        System.out.println("你好\n中国");
//        \t代表一个Tab空格
        System.out.println("你好\t中国");
//        println是打印之后换行,print是打印之后不换行,因为ln本身就是换行的意思
        System.out.print("你好,中国");
        System.out.print("我爱你,中国");
        System.out.println();//代表一次换行
        System.out.println("哈哈");//打印哈哈之后换行
        System.out.println("\n");//代表两次换行

        double grade = 98.5;//定义变量时,必须定义数据类型
        System.out.println(grade);
        grade = 80.0;//修改变量的值不用加数据类型
        System.out.println(grade);

        {
            int age;//定义变量时可以先不给变量赋值
            age = 21;
//            int age = 18;同一个大括号内不能定义相同的变量
            System.out.println(age);
        }

        int age = 20;//在括号外可以再次定义相同的变量
        System.out.println(age);
//        int wanghui = 23.4;此处是不对的,因为23.4是double类型,不能定义为int类型
//        int grade = 60;此处是不对的,因为再同一个大括号内已经定义了一个grade变量了,即使变量类型不同也不行
//        System.out.println(age);注意此处是不对的,因为age变量只在大括号内有效,在大括号外无效
        char ch = 'a';//char代表字符类型
        System.out.println('a' + 1);//在计算机底层,字符'a'使用97的二进制来存储的,所以结果为98(ASCII码表中可查询)
        System.out.println(ch + 1);//结果也是98,因为ch变量的值为'a'
//        byte m = 128;肯定会报错,因为byte类型最大是127
//        long m = 1234567890123;这样也是报错的,因为随便写个整数默认是int类型,想要表示long类型必须在后面加上L或者l
        long m = 1234567890123L;//这样就是正确的了
//        float b = 98.5;随意写个小数默认是double类型,如果表示float必须加上F或者f
        float b = 98.5F;
        boolean c = true;//布尔类型必须用小写的true和false
        String v = "你好啊";//字符串类型String首字母必须是大写
        int studentAge = 89;//定义变量建议用小驼峰命名法,定义类用大驼峰命名法,方法也是用小驼峰命名法
        System.out.println("------------------------数据类型转换-----------------------------------------");
        System.out.println("----------自动类型转换----------------");
        byte n = 98;
        int h = n;//范围小的变量可以赋值给范围大的变量
        System.out.println(h);
        System.out.println("--------------------");
        char x = 'a';
        int y = x;//char占用两个字节,int占用四个字节,所以char可以赋值给int
        System.out.println(y);
        System.out.println("-------表达式中的类型转换------------------");
        //表达式中的类型转换以最高范围数据类型为主
        byte q = 12;
        int w = 15;
        double e = 56.7;
        System.out.println(q + w + e);//最高数据类型为double,它占用8个字节,所以结果是double类型
        byte ii = 10;
        byte o = 20;
//        byte p = ii + o;运算中byte会自动转换为int类型进行运算,所以i+o肯定是int类型
        int p = ii + o;//定义p的时候必须是int类型,不能是byte类型
        System.out.println("----------强制类型转换----------------");
        int s = 12;
//        byte d = s;数据范围大的不能赋值给小的,所以不对
        byte d = (byte) s;//强制类型转换的格式,在变量前面加上括号和要转换为的数据类型
        double score = 99.5;
        int it = (int) score;
        System.out.println(it);//double转化成int会直接把小数部分抹掉
        /**  {
         int a = 10;
         int b = 3;
         注意:如果上面在括号外定义过变量a,b,那么就算在后面再添加括号并且在里面定义a,b也是不行的;
         如果上面是在括号里面定义过变量a,b,那么在后面括号外还是可以定义变量a,b的,一定要区别开
         }*/
        int g = 10;
        int j = 3;
        System.out.println(g / j);
        //因为在表达式中结果是由最高范围的数据类型决定的,所以结果是int类型,而不是小数
        double k = (double) g / j;//这样结果就是小数类型了
        System.out.println(k);
        double l = g * 1.0 / j;//这样也可以,因为0.1是double类型,所以结果就是double类型
        System.out.println(l);
        System.out.println("--------------------------" + "做连接符---------------------------");
        int a = 5;
        System.out.println("abc" + 'a');//abca  字符遇到字符串则表现为字符本身
        System.out.println("abc" + a);//abc5
        System.out.println(5 + a);//10
        System.out.println("abc" + 5 + 'a');//abc5a   从左往右依次运算
        System.out.println(15 + "abc" + 15);//15abc15
        System.out.println(a + 'a');//102   int和char运算结果为数据范围大的类型,也就是int类型
        System.out.println(a + "" + 'a');//5a    5+""变成字符串"5",'a'遇到字符串"5"表现为字符本身
        System.out.println(a + 'a' + " itheima ");//102 itheima
        System.out.println("itheima" + a + 'a');//itheima5a
        System.out.println("itheima" + (a + 'a'));//itheima102
        /**总结:能算则算,不能算就在一起
         只要遇到字符串,结果肯定是字符串*/
        System.out.println("------------------自增自减---------------------------");
        //a++和++a单独存在的时候是一样的,没有区别,都是相当于a=a+1,但是在与其他数据进行运算的时候,是有大的区别的
        //a++是先与a运算,再对a进行++重新赋值,++a是先对a进行++重新赋值,然后再与a进行运算
        int u = 10;
        int ab = 20;
        int abc = u + ab++;
        int abd = u + ++ab;
        System.out.println(abc);//
        System.out.println(abd);//
        System.out.println(ab);//
        System.out.println("------------------------------");
        int $ = 3;
        int z = 5;
        int rs = $++ + ++$ - --z + z-- - $-- + ++z + 2;
        System.out.println(rs);//注意:rs = $++第一步也是先用$再++
        System.out.println($);
        System.out.println(z);
        //逻辑运算符:&与    |或    !非     ^异或(两个条件结果不同则为真,相同则为假)  &&双与(只要一个为假,就停止运行)   ||双或(只要一个为真,就停止运行)
        System.out.println("---------------------------三元运算符-------------------------");
        double chengji = 98;
        String tip = chengji >= 60 ? "成绩合格" : "成绩不合格";
        System.out.println(tip);
        int haha = 45;
        int heihei = 60;
        int jdz = haha > heihei ? haha : heihei;
        System.out.println(jdz);
        int dyg = 105;
        int deg = 102;
        int dsg = 100;
//        下面用三种方法来计算三个数的最大值,其中第二种和第三种是一种,只是第三种加上了括号容易理解些
        int max = (dyg > deg ? dyg : deg) > dsg ? (dyg > deg ? dyg : deg) : dsg;
        int max2 = dyg > deg ? dyg > dsg ? dyg : dsg : deg > dsg ? deg : dsg;
        int max3 = dyg > deg ? (dyg > dsg ? dyg : dsg) : (deg > dsg ? deg : dsg);
        System.out.println(max);
        System.out.println(max2);
        System.out.println(max3);
        System.out.println("-----------------------------Scanner类-----------------------------------");
        Scanner aaaa = new Scanner(System.in);//定义一个对象
        System.out.print("请输入您的年龄:");
        int bbbb = aaaa.nextInt();//通过对象调用nextInt方法
        System.out.print("请输入您的姓名:");
        String cccc = aaaa.next();
        System.out.print("请输入您的分数:");
        double dddd = aaaa.nextDouble();
        System.out.println("您的姓名是:" + cccc + "\t" + "您的年龄是:" + bbbb + "\t" + "您的分数是:" + dddd);
        System.out.println("-----------------------------数组-----------------------------------");
        //int[] aaa = new int[]{1,2,3,4,5};静态初始化完整格式,以下为静态初始化简化格式
        int[] aaa = {1, 2, 3, 4, 5};
        System.out.println(aaa);
        System.out.println(aaa[3]);
        String[] bbb = {"wanghui", "zhangsan", "lisi"};
        System.out.println(bbb);
        System.out.println(bbb[2]);
        bbb[2] = "halou";//数组重新赋值
        System.out.println(bbb[2]);
        System.out.println(bbb.length);//输出数组的数据个数
        int[] ccc = new int[5];//动态初始化,只定义数组的数据类型和长度lenth
        System.out.println("======================数组的遍历=============================");
        String[] aaaaa = {"wanghui", "xiaoming", "zhangsan", "lisi"};
        for (int i = 0; i < aaaaa.length; i++) {
            System.out.println(aaaaa[i]);
        }
        System.out.println("======================遍历求和及求最大值==================");
        int[] sale = {16, 26, 36, 6, 100};
        int sumSale = 0;
        int maxSale = sale[0];
        //方法①:
        /**for (int i = 0; i < sale.length; i++) {
         sumSale += sale[i];
         if (sale[i] > maxSale) {
         maxSale = sale[i];
         } else {
         maxSale = maxSale;
         }
         }
         System.out.println(sumSale);
         System.out.println(maxSale);*/
        //方法②:
        for (int i = 0; i < sale.length; i++) {
            sumSale += sale[i];
            maxSale = sale[i] > maxSale ? sale[i] : maxSale;
        }
        System.out.println(sumSale);
        System.out.println(maxSale);
        System.out.println("-----------------for循环-------------------------");
        for (int i = 0; i <= 10; i++) {
            if (i > 5) {
                System.out.println("我是王辉!");
            } else {
                System.out.println("我不是王辉!");
            }
        }
        System.out.println("-------------------while循环---------------------------");
        int i = 0;
        while (i <= 5) {
            System.out.println("哈哈");
            i++;
        }
        System.out.println("------------------do-while循环-------------------------");
        int i2 = 0;
        do {
            System.out.println("这是do-while循环!");
            i2++;
        } while (i <= 5);//注意do-while循环的while后面只有条件语句,没有代码块
        System.out.println("-------------------Switch分支结构-------------------------");
        Scanner aaaaaa = new Scanner(System.in);
        System.out.print("请输入月份:");
        int month = aaaaaa.nextInt();
        switch (month) {
            //如果不加break,就会一直打印直到遇到break停止,这就是switch分支结构的穿透性
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                System.out.println("31天");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                System.out.println("30天");
                break;
            case 2:
                System.out.println("平年有28天,闰年有29天");
                break;
        }
        System.out.println("=======================密码验证=======================");
        String truePassword = "jialing520";
        Scanner acc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入密码:");
            String printPassword = acc.next();
            if (printPassword.equals(truePassword)) {
                System.out.println("密码正确,欢迎进入系统!");
                break;
            } else {
                System.out.println("密码错误!请重新输入");
            }
        }
        System.out.println("==========================Random随机数==============================");
        Random cba = new Random();
        int num = cba.nextInt(10);//代表随机生成0-9之间的随机数
        System.out.println(num);
        //如何生成65-91之间的随机数呢?下面用例子介绍:
        //65-91(怎么变成0-x呢,先减去65,得到0-26,然后再加上65
        int data = cba.nextInt(27) + 65;//表示随机生成65-91之间的随机数
        System.out.println(data);
        System.out.println("==========================猜数字游戏===========================");
        //随机生成一个0-100之间的数字
        Random a3 = new Random();
        Scanner b3 = new Scanner(System.in);
        int datas = a3.nextInt(101);
        while (true) {
            System.out.println("请输入一个数字:");
            int guess = b3.nextInt();
            if (guess == datas) {
                System.out.println("恭喜您猜对了!");
                break;
            } else if (guess > datas) {
                System.out.println("您猜测的数字过大");
            } else {
                System.out.println("您猜测的数字过小");
            }
        }
        System.out.println("=========================if-else分支结构===========================");
        if ('a' != 97) {      //!=   表示  不等
            System.out.println("错误");
        } else if ('A' == 64) {   // ==   表示  等于
            System.out.println("错误");
        } else if ("helou".equals("halou")) {   //  "haha".equals("haha")  表示字符串相等
            System.out.println("错误");
        } else if (!"nihao".equals("nihei")) {   //  !"halou".equals("haha")    表示字符串不相等
            System.out.println("正确");
        } else {
            System.out.println("不知道");
        }
        System.out.println("=========================Arraylist集合与遍历===========================");


    }

}
