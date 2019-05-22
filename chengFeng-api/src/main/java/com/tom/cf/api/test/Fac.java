package com.tom.cf.api.test;

/**
 * @method:
 */
public class Fac {
    public static JiSuan ooo (String s){
        if (s.equals("+")){
            return new Add();
        }else return null;
    }
}
