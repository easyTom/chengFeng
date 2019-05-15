package com.tom.cf.api.v2.drawer;


import com.tom.cf.api.v2.model.Digit;
import com.tom.cf.api.v2.model.Ecg;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class DataDrawer implements Drawer<PaperDrawer> {

    public static double DEFAULT_PAPER_SPEED = 25;
    public static double DEFAULT_SENSITIVITY = 0.01;
    /**
     * 导联间 间隔
     */
    public static int DEFAULT_ROW_SPACE = 4;

    private PaperDrawer paperDrawer;
    private Ecg ecgData;
    private Graphics2D graphics2D;
    //纸速： 默认为25， 单位mm/s
    private double paperSpeed = DEFAULT_PAPER_SPEED;
    //增益： 默认为0.01，单位mm/uV
    private double sensitivity = DEFAULT_SENSITIVITY;

    public DataDrawer(PaperDrawer paperDrawer,Ecg ecgData) {
        this.paperDrawer = paperDrawer;
        this.ecgData = ecgData;
        this.graphics2D = paperDrawer.createGraphics();
    }

    public DataDrawer(PaperDrawer paperDrawer,Ecg ecgData,double paperSpeed, double sensitivity) {
        this.paperDrawer = paperDrawer;
        this.ecgData = ecgData;
        this.graphics2D = paperDrawer.createGraphics();
        this.paperSpeed = paperSpeed;
        this.sensitivity = sensitivity;
    }

    /**
     * 绘制纸速和增益
     */
    public void drawTitle(){
        graphics2D.setFont(new Font("宋体", Font.BOLD, 14));
        graphics2D.setColor(new Color(0));
        StringBuilder title = new StringBuilder("纸速：");
        title.append((int)(this.paperSpeed) + "mm/s   ");
        title.append("增益：").append((int)(this.sensitivity * 1000) + "mm/mV");
        graphics2D.drawString(title.toString(), 25, 25);
    }

    /**
     * 绘制心电数据
     */
    private void drawData(){
        graphics2D.setColor(new Color(157, 96, 3));
        LinkedList<Digit> digitList = ecgData.getDigits();
        Digit digit = null;

//        int rows = paperDrawer.getRow();

        for (int i = 0; i < digitList.size(); i++) {
            digit = digitList.get(i);
            int y = paperDrawer.getRowPitch()* DEFAULT_ROW_SPACE * (i + 1);
            graphics2D.drawString(digit.getCodeName(),0,y);

            Point p1 = new Point(paperDrawer.getRowPitch(),y);
            Point p2 = null;
            for(int j=1;j<=digit.getDigits().length;j++){
                /**
                 *  x = 1mm的像素数 *  采集间隔时间（单位s）  *  纸速(25mm/s) * 第j个采集点
                 *  y = 1mm的像素数 * 电压（单位uV） *  增益（10mm/1000uV）
                 */
                p2 = new Point(paperDrawer.getPointPitch() * ecgData.getIncrement() * paperSpeed * j,y - (digit.getDigits()[j-1])* sensitivity * paperDrawer.getPointPitch());
                //x轴起始位置
                p2.x = paperDrawer.getRowPitch() + p2.x;

                graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
                p1 = p2;
            }
        }
    }

    @Override
    public PaperDrawer draw() {
        paperDrawer.draw();
        drawTitle();
        drawData();
        return paperDrawer;
    }

    public double getPaperSpeed() {
        return paperSpeed;
    }

    public void setPaperSpeed(double paperSpeed) {
        this.paperSpeed = paperSpeed;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    class Point{
        double x;
        double y;
        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }

    }
}
