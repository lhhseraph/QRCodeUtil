package com.zhaoruipu.QRCodeUtils.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.iharder.Base64;

public class QRCodeUtil {
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	
	/**
	 * 获取二维码，并返回一个base64编码的字符串
	 * @author zrp
	 * 方法创建时间：2018年3月29日
	 * @param contents				需要编码的内容
	 * @param width					生成的二维码高度
	 * @param height				生成的二维码长度
	 * @return						返回base64编码的字符串
	 * @throws WriterException
	 * @throws IOException
	 */
	public static String getQRCode(String contents, int width, int height) throws WriterException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(encode(contents, width, height), "png", out);
		
		return Base64.encodeBytes(out.toByteArray());
	}
	
	/**
	 * 获取二维码，并将二维码保存到对应的位置
	 * @author zrp
	 * 方法创建时间：2018年3月29日
	 * @param contents				需要编码的内容
	 * @param width					生成的二维码宽度
	 * @param height				生成的二维码高度
	 * @param location				需要保存到的位置
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void getQRCode(String contents, int width, int height, String location) throws WriterException, IOException{
		ImageIO.write(encode(contents, width, height), "png", new File(location));
	}
	
	/**
	 * 将信息编码成二维码
	 * @author zrp
	 * 方法创建时间：2018年3月29日
	 * @param contents
	 * @param width
	 * @param height
	 * @return
	 * @throws WriterException
	 */
	private static BufferedImage encode(String contents, int width, int height) throws WriterException{
		Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		
		return image;
	}
	
}
