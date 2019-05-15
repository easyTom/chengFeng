package com.tom.cf.api.v2.util;


import com.tom.cf.api.v2.drawer.DataDrawer;
import com.tom.cf.api.v2.drawer.PaperDrawer;
public class EcgUtil {

    /**
     *   根据采集的数据，自动算出画布所需要的宽度
     * @param scale  1mm 像素数
     * @param increment  采集间隔时间（单位s）
     * @param effectiveSecs  共采集时间（s）
     * @param paperSpeed  纸速（单位mm/s）
     * @return
     */
    public static int getWidth(int scale, double increment,int effectiveSecs,double paperSpeed){
        int mm = (int)((increment * effectiveSecs + 0.6) * paperSpeed);
        return mm * scale;
    }

    /**
     *   根据采集的数据，自动算出画布所需要的高度
     * @param scale  1mm 像素数
     * @param row  导联数
     * @return
     */
    public static int getHeight(int scale,int row){
        return (row + 1) * DataDrawer.DEFAULT_ROW_SPACE * scale * PaperDrawer.DEFAULT_UNIT;
    }

}