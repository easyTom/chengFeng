package com.tom.cf.core.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResizeImageUtils {

	/**
	 * 设置缩略图的宽高
	 * @param bi
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage zoomImage(BufferedImage bi) {
		/* 原始图像的宽度和高度 */
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		if(width >= height && width > 200){
			double ratio = width / 200.0;
			width = 200;
			height /= ratio;
		}else if(height > width && height > 200){
			double ratio = height / 200.0;
			height = 200;
			width /= ratio;
		}
		
		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		result.getGraphics().drawImage(bi.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}
	
	/**
	 * 设置图片的缩放倍数
	 * @param bi
	 *            原始图像
	 * @param zoom
	 *            缩放倍数
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(BufferedImage bi, float zoom) {
		/* 原始图像的宽度和高度 */
		int width = bi.getWidth();
		int height = bi.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * zoom);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * zoom);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(bi.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}
	
	/**
	 * 将一个图片文件封装成BufferedImage对象并装进Map中返回
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> getImage(String filePath) throws IOException {
		File f = new File(filePath);
		Map<String, Object> imageMap = new HashMap<String, Object>();
		imageMap.put("imageName", f.getName());
		imageMap.put("imageData", javax.imageio.ImageIO.read(f));
		return imageMap;
	}

	/**
	 * 将一个目录下的所有指定类型的图片都封装成BufferedImage对象并装进Map中然后再将Map装进List中返回
	 * @param folderPath
	 *            要转化的图像的文件夹,就是存放图像的文件夹路径
	 * @param type
	 *            图片的后缀名组成的数组
	 * @return
	 */
	public static List<Map<String, Object>> getImageList(String folderPath, String[] type) throws IOException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (String s : type) {
			map.put(s, true);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File[] fileList = new File(folderPath).listFiles();
		for (File f : fileList) {
			if (f.length() == 0) {
				continue;
			}
			if (map.get(getExtension(f.getName())) == null) {
				continue;
			}
			Map<String, Object> imageMap = new HashMap<String, Object>();
			imageMap.put("imageName", f.getName());
			imageMap.put("imageData", javax.imageio.ImageIO.read(f));
			list.add(imageMap);
		}
		return list;
	}
	
	/**
	 * 返回文件的文件后缀名
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将BufferedImage对象生成缩略图
	 * 
	 * @param fileName
	 * @param bi
	 * @param outputFolder
	 * @return
	 */
	public static boolean writeThumbnails(String fileName, BufferedImage bi, String outputFolder) {
		try {
			File f = new File(outputFolder);
			if (!f.exists()) {
				f.mkdirs();
			}
			outputFolder = (outputFolder.endsWith("/") || outputFolder.endsWith("\\\\")) ? outputFolder : outputFolder + "/";
			/* 输出到文件流 */
			FileOutputStream fos = new FileOutputStream(outputFolder + fileName + "_min.png");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bi);
			/* 压缩质量 */
			//jep.setQuality(1f, true);
			/* 近JPEG编码 */
			encoder.encode(bi, jep);
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
