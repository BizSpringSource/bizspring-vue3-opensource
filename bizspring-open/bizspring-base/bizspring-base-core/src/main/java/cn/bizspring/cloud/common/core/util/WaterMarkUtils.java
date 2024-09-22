package cn.bizspring.cloud.common.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class WaterMarkUtils {

	/**
	 * 图片添加水印
	 *
	 * @param imgFile
	 *            需要添加水印的图片
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param waterMarkContent
	 *            水印的文字
	 * @return  水印图片
	 */
	public static File markStr(File imgFile, Color markContentColor, String waterMarkContent) {
		try {
			// 加水印
			BufferedImage bufImg = ImageIO.read(imgFile);
			int width = bufImg.getWidth(); //图片宽
			int height = bufImg.getHeight(); //图片高
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(bufImg, 0, 0, width, height, null);
			Font font = new Font("微软雅黑", Font.ITALIC, 45);
			g.setColor(markContentColor); // 根据图片的背景设置水印颜色

			g.setFont(font);
			int x = width -(20+getWatermarkLength(waterMarkContent, g)); //这是一个计算水印位置的函数，可以根据需求添加
//			int y = height - 1*getWatermarkLength(waterMarkContent, g);
			int y = height -(font.getSize());
			g.drawString(waterMarkContent, x, y);
			g.dispose();

			ImageIO.write(bufImg, "png", imgFile);
			return imgFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取水印文字总长度
	 *
	 * @param waterMarkContent
	 *            水印的文字
	 * @param g
	 * @return 水印文字总长度
	 */
	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
}
