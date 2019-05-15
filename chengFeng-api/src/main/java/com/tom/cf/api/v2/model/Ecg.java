package com.tom.cf.api.v2.model;

import java.util.LinkedList;

public class Ecg {

    //间隔多久采集一次点,单位：秒
    private double increment;
    //导联数据
    private LinkedList<Digit> digits;

    public static Ecg newInstance(){
        Ecg ecg = new Ecg();
        ecg.setIncrement(0.001);
        ecg.setDigits(new LinkedList<Digit>());
        return ecg;
    }

    public LinkedList<Digit> addDigit(Digit digit){
        if(digits == null){
            digits = new LinkedList<Digit>();
        }
        digits.add(digit);
        return digits;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public LinkedList<Digit> getDigits() {
        return digits;
    }

    public void setDigits(LinkedList<Digit> digits) {
        this.digits = digits;
    }
}
