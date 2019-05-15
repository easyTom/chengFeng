package com.tom.cf.api.v2.model;
public class Digit {

    private String code;
    private String codeName;
    private double scale = 1;
    private String unit = "uV";
    private double [] digits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double[] getDigits() {
        return digits;
    }

    public String getCodeName() {
        return code.substring(code.lastIndexOf("_")+1);
    }

    public void setDigits(double[] digits) {
        for (int i = 0; i < digits.length; i++) {
            digits[i] = digits[i] * this.scale;
        }
        this.digits = digits;
    }

    public void setDigits(String[] digits) {
        if(digits == null || digits.length == 0){
            throw new NullPointerException("digits: 不可为空");
        }
        this.digits = new double[digits.length];
        for (int i = 0; i < digits.length; i++) {
            this.digits[i] = Double.parseDouble(digits[i]) * this.scale;
        }
    }
}
