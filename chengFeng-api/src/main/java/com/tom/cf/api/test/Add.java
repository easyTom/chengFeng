package com.tom.cf.api.test;

/**
 * @method:
 */
public class Add extends JiSuan {
    @Override
    public double GetResult() {
            double result = 0;
            result = getA() + getB();
            return result;
    }

    public static void main(String[] args) {
        JiSuan ooo = Fac.ooo("+");
        ooo.setA(12);
        ooo.setB(2);
        double v = ooo.GetResult();
        System.out.println(v);
    }
}
