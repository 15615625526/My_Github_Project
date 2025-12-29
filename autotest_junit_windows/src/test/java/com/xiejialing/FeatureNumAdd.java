package com.xiejialing;

import io.cucumber.java.en.And;

public class FeatureNumAdd {
    @And("计算{int}和{int}的和")
    public void 输入两个数字(int num1, int num2) {
        int numAdd = num1 + num2;
        System.out.println(numAdd);
    }
}
