package package3;

import static package2.package21.FangFa.sums;

public class FangFa5 {
    public static void main(String[] args) {
        //这是调用不同模块下的sums方法
        int a = sums(5, 6);
        System.out.println(a);
    }
}

//调用其他模块中的方法需要设置:File→Project Structure→选中当前模块→Dependencies→点击"+"号,添加需要依赖的模块即可
