package org.xlp.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.XLPOutputInfoUtil;

/**
 * 图片处理类
 * 
 * @author 徐龙平
 * @version 1.0 <br/>
 *          主要功能是把给定的图片处理成指定大小的图片或按比例缩放图片
 * 
 */
public class XLPImageUtil {
	// 原图片
	private BufferedImage srcImage;
	private File srcFile;
	// 处理后的图片
	private BufferedImage zoomImage;

	/**
	 * 要处理的图片文件
	 * 
	 * @param srcFile
	 * @throws IOException
	 *             当文件读取失败时，抛出此异常
	 */
	public XLPImageUtil(File srcFile) throws IOException {
		this.srcImage = ImageIO.read(srcFile);
		this.zoomImage = this.srcImage;
		this.srcFile = srcFile;
	}

	/**
	 * 要处理的图片文件路径
	 * 
	 * @param pathName
	 * @throws IOException
	 *             当文件读取失败时，抛出此异常
	 */
	public XLPImageUtil(String pathName) throws IOException {
		this(new File(pathName));
	}

	/**
	 * 要处理的图片文件流
	 * 
	 * @param srcImageStream
	 * @throws IOException
	 */
	public XLPImageUtil(InputStream srcImageStream) throws IOException {
		this.srcImage = ImageIO.read(srcImageStream);
		this.zoomImage = this.srcImage;
	}

	/**
	 * 获取处理后的图片
	 * 
	 * @return
	 */
	public BufferedImage getZoomImage() {
		return zoomImage;
	}

	/**
	 * 把指定的图片资源转换成给定宽度与高度的图片资源（可能失帧）
	 * 
	 * @param width
	 * @param height
	 */
	public void transform(int width, int height) {
		zoomImage = new BufferedImage(width, height, srcImage.getType());
		zoomImage.createGraphics().drawImage(srcImage, 0, 0, width, height,
				null, null);
	}

	/**
	 * 按给定比率缩放图片资源，假如给定的缩放率小于或等于0，不做任何操作
	 * 
	 * @param scale
	 */
	public void transform(double scale) {
		if (scale <= 0)
			return;
		transform((int) Math.round(srcImage.getWidth() * scale),
				(int) Math.round(srcImage.getHeight() * scale));
	}

	/**
	 * 获取缩放后图片的宽度
	 * 
	 * @return
	 */
	public int getWidth() {
		return zoomImage.getWidth();
	}

	/**
	 * 获取缩放后图片的高度
	 * 
	 * @return
	 */
	public int getHeight() {
		return zoomImage.getHeight();
	}

	/**
	 * 把新的图片资源写入给定的流中
	 * 
	 * @param imageType
	 *            图片格式
	 * @param out
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(String imageType, OutputStream out) {
		try {
			return ImageIO.write(zoomImage, imageType, out);
		} catch (Exception e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把新的图片资源以png格式写入给定的流中
	 * 
	 * @param out
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(OutputStream out) {
		return write("png", out);
	}

	/**
	 * 把新的图片资源写入给定的文件中
	 * 
	 * @param imageType
	 *            图片格式
	 * @param outFile
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(String imageType, File outFile) {
		try {
			File parent = outFile.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
				outFile = new File(parent, outFile.getName());
			}
			return ImageIO.write(zoomImage, imageType, outFile);
		} catch (Exception e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把新的图片资源以png格式写入给定的文件中
	 * 
	 * @param outfFile
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(File outfFile) {
		if (outfFile == null)
			return write();
		return write("png", outfFile);
	}

	/**
	 * 把新的图片资源写入给定的文件中
	 * 
	 * @param imageType
	 *            图片格式
	 * @param outfFilePathName
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(String imageType, String outfFilePathName) {
		return write(imageType, new File(outfFilePathName));
	}

	/**
	 * 把新的图片资源以png格式写入给定的文件中
	 * 
	 * @param outfFilePathName
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write(String outfFilePathName) {
		if (outfFilePathName == null)
			return write();
		return write("png", outfFilePathName);
	}

	/**
	 * 把新的图片资源以[源文件名+newW*newH+.png]格式写入与原文件相同的目录下
	 * 
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean write() {
		if (srcFile == null || srcFile.isDirectory())
			return false;
		String fileName = srcFile.getName();
		File dir = srcFile.getParentFile();
		// 获取后缀名
		int index = fileName.lastIndexOf('.');
		String suf = fileName.substring(index);
		fileName = fileName.substring(0, index) + getWidth() + "_"
				+ getHeight() + suf;
		return write(suf.substring(1), new File(dir, fileName));
	}

	/**
	 * 把新的图片资源以[源文件名+newW*newH+(yyyyMMddHHmmssZ)+.png]格式写入与原文件相同的目录下
	 * 
	 * @return 假如写入失败返回false，否则返回true
	 */
	public boolean writeAttachCurrentTime() {
		if (srcFile == null || srcFile.isDirectory())
			return false;
		String fileName = srcFile.getName();
		File dir = srcFile.getParentFile();
		// 获取后缀名
		int index = fileName.lastIndexOf('.');
		String suf = fileName.substring(index);
		fileName = fileName.substring(0, index) + getWidth() + "_"
				+ getHeight() + "_"
				+ XLPDateUtil.dateToString(new Date(), "yyyyMMddHHmmss.S")
				+ suf;
		return write(suf.substring(1), new File(dir, fileName));
	}

	/**
	 * 把指定的图片资源以给定的宽高写入目标文件中
	 * 
	 * @param srcPathName
	 *            源文件名
	 * @param tagetFilePath
	 *            目标文件名
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(String srcPathName, String tagetFilePath,
			int width, int height) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcPathName);
			imageUtil.transform(width, height);
			return imageUtil.write(tagetFilePath);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把指定的图片资源以给定的宽高写入目标文件中
	 * 
	 * @param srcPathName
	 *            源文件名
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(String srcPathName, int width, int height) {
		return write(srcPathName, null, width, height);
	}

	/**
	 * 把指定的图片资源以给定的缩放率写入目标文件中
	 * 
	 * @param srcPathName
	 *            源文件名
	 * @param tagetFilePath
	 *            目标文件名
	 * @param scale
	 *            缩放率
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(String srcPathName, String tagetFilePath,
			double scale) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcPathName);
			imageUtil.transform(scale);
			return imageUtil.write(tagetFilePath);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把指定的图片资源以给定的缩放率写入目标文件中
	 * 
	 * @param srcPathName
	 *            源文件名
	 * @param scale
	 *            缩放率
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(String srcPathName, double scale) {
		return write(srcPathName, null, scale);
	}

	/**
	 * 把指定的图片资源以给定的宽高写入目标文件中
	 * 
	 * @param srcFile
	 *            源文件名
	 * @param targetFile
	 *            目标文件名
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(File srcFile, File targetFile, int width,
			int height) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcFile);
			imageUtil.transform(width, height);
			return imageUtil.write(targetFile);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把指定的图片资源以给定的缩放率写入目标文件中
	 * 
	 * @param srcFile
	 *            源文件名
	 * @param targetFile
	 *            目标文件名
	 * @param scale
	 *            缩放率
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(File srcFile, File targetFile, double scale) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcFile);
			imageUtil.transform(scale);
			return imageUtil.write(targetFile);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把指定的图片资源以给定的宽高写入目标流中
	 * 
	 * @param srcImage
	 *            源文件名
	 * @param targetOut
	 *            目标文件名
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(InputStream srcImage, OutputStream targetOut,
			int width, int height) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcImage);
			imageUtil.transform(width, height);
			return imageUtil.write(targetOut);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}

	/**
	 * 把指定的图片资源以给定的缩放率写入目标流中
	 * 
	 * @param srcFile
	 *            源文件名
	 * @param targetFile
	 *            目标文件名
	 * @param scale
	 *            缩放率
	 * @return 假如写入失败返回false，否则返回true
	 */
	public static boolean write(InputStream srcImage, OutputStream targetOut,
			double scale) {
		try {
			XLPImageUtil imageUtil = new XLPImageUtil(srcImage);
			imageUtil.transform(scale);
			return imageUtil.write(targetOut);
		} catch (IOException e) {
			XLPOutputInfoUtil.println("=================" + e.getMessage()
					+ "=================");
		}
		return false;
	}
}
