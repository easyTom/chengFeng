package com.tom.cf.api.v2.drawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaperDrawer extends BufferedImage implements Drawer{

    /**
     * 默认1mm 为 5 个像素
     */
    public static int DEFAULT_SCALE = 5;
    /**
     * 默认5mm为 一个方块宽度
     */
    public static int DEFAULT_UNIT = 5;

    /**
     *  多少个pixel代表1mm，也就是画布的一小格子
     */
    private int	scale;

    /**
     * 线之间间距 (5mm)
     */
    private int rowPitch;

    /**
     * 点之间间距 (1mm)
     */
    private int pointPitch;

    /**
     * 心电纸张中格线的颜色
     */
    private Color lineColor = new Color(255, 74, 74);
    private Graphics2D graphics2D;

    public PaperDrawer(){
        this(1400, 1250, BufferedImage.TYPE_INT_ARGB);
    }

    public PaperDrawer(int width, int height){
        this(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public PaperDrawer(int width, int height, int imageType) {
        super(width, height, imageType);
        init();
    }

    public void init(){
        graphics2D = super.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,getWidth(),getHeight());
        //消除锯齿边缘
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(lineColor);

        setScale(DEFAULT_SCALE);
    }

    /**
     * 画横竖线网格
     * @param g
     */
    public void drawXYLine(){
        //画x轴直线
        int sumY = getHeight() / rowPitch;
        for(int i=1;i<=sumY;i++){
            graphics2D.drawLine(0, i*rowPitch, getWidth(), i*rowPitch);
        }
        //画y轴直线
        int sumX = getWidth() / rowPitch;
        for(int i=1;i<=sumX;i++){
            graphics2D.drawLine(i*rowPitch,0 , i*rowPitch, getHeight());
        }

    }

    /**
     * 小网格内填充点
     * @param g
     */
    public void fillIn(){
        //填充点
        for(int x =1 ; x<=getWidth();x++){
            if(x%pointPitch == 0){
                for(int y =1;y<=getHeight();y++){
                    if(y%pointPitch == 0){
                        graphics2D.drawLine(x, y, x, y);
                    }
                }
            }
        }
    }

    @Override
    public PaperDrawer draw() {
        drawXYLine();
        fillIn();
        return this;
    }

    public void setScale(int scale) {
        this.scale = scale;
        this.rowPitch = DEFAULT_UNIT * scale;
        this.pointPitch =  scale;
    }

    /**
     * 25mm
     * @return
     */
    public int getRowPitch() {
        return rowPitch;
    }

    /**
     * 1mm
     * @return
     */
    public int getPointPitch() {
        return pointPitch;
    }

    /**
     *  获取实线行数
     * @return
     */
    public int getRow(){
        return this.getWidth() / this.getRowPitch();
    }

    /**
     * 获取实线列数
     * @return
     */
    public int getColumn(){
        return this.getHeight() / this.getRowPitch();
    }
}
