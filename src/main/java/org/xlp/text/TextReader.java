package org.xlp.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.xlp.assertion.AssertUtils;
import org.xlp.assertion.IllegalObjectException;
import org.xlp.utils.XLPCharsetUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.io.XLPIOUtil;

/**
 * <p>
 * 创建时间：2021年2月18日 下午6:26:04
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description 文本文件读取类
 */
public class TextReader {
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_CHARSET = XLPCharsetUtil.UTF8;

	/**
	 * 记录是否跳过空行，默认跳过空行
	 */
	private boolean skipEmptyRow = true;

	/**
	 * 文本文件读取流
	 */
	private BufferedReader reader;

	/**
	 * 记录是否关闭读取流
	 */
	private boolean isAutoClose = false;

	/**
	 * 构造函数
	 * 
	 * @param reader
	 *            读取流
	 * @throws NullPointerException
	 *             假如参数为空，则抛出该异常
	 */
	public TextReader(Reader reader) {
		AssertUtils.isNotNull(reader, "reader paramter is null!");
		this.reader = XLPIOUtil.getBufferedReader(reader);
	}

	/**
	 * 构造函数
	 * 
	 * @param inputStream
	 *            txt文件输入流
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 */
	public TextReader(InputStream inputStream) {
		this(inputStream, DEFAULT_CHARSET);
	}

	/**
	 * 构造函数
	 * 
	 * @param inputStream
	 *            txt文件输入流
	 * @param charsetName
	 *            文件字符编码
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 */
	public TextReader(InputStream inputStream, String charsetName) {
		AssertUtils.isNotNull(inputStream, "inputStream paramter is null!");
		charsetName = XLPStringUtil.isEmpty(charsetName) ? DEFAULT_CHARSET : charsetName.trim();
		this.reader = XLPIOUtil.getReader(inputStream, charsetName);
	}

	/**
	 * 构造函数
	 * 
	 * @param txtFlie
	 *            txt文件
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public TextReader(File txtFlie) throws IOException {
		AssertUtils.assertFile(txtFlie);
		try {
			this.reader = new BufferedReader(new FileReader(txtFlie));
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} finally {
			isAutoClose = true;
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param txtFlie
	 *            txt文件
	 * @param charsetName
	 *            文件字符编码
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public TextReader(File txtFlie, String charsetName) throws IOException {
		AssertUtils.assertFile(txtFlie);
		try {
			InputStream inputStream = new FileInputStream(txtFlie);
			charsetName = XLPStringUtil.isEmpty(charsetName) ? DEFAULT_CHARSET : charsetName.trim();
			this.reader = XLPIOUtil.getReader(inputStream, charsetName);
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} finally {
			isAutoClose = true;
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param txtFliePath
	 *            txt文件路径
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null or empty，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public TextReader(String txtFliePath) throws IOException {
		AssertUtils.isNotNull(txtFliePath, "txtFliePath paramter is null or empty!");
		try {
			this.reader = new BufferedReader(new FileReader(txtFliePath.trim()));
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} finally {
			isAutoClose = true;
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param txtFliePath
	 *            txt文件路径
	 * @param charsetName
	 *            文件字符编码
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 * @throws NullPointerException
	 *             假如参数为null or empty，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 */
	public TextReader(String txtFliePath, String charsetName) throws IOException {
		AssertUtils.isNotNull(txtFliePath, "txtFliePath paramter is null or empty!");
		try {
			InputStream inputStream = new FileInputStream(txtFliePath.trim());
			charsetName = XLPStringUtil.isEmpty(charsetName) ? DEFAULT_CHARSET : charsetName.trim();
			this.reader = XLPIOUtil.getReader(inputStream, charsetName);
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} finally {
			isAutoClose = true;
		}
	}

	/**
	 * @return 是否跳过空行，默认跳过空行
	 */
	public boolean isSkipEmptyRow() {
		return skipEmptyRow;
	}

	/**
	 * @param skipEmptyRow
	 *            设置是否跳过空行，默认跳过空行
	 */
	public void setSkipEmptyRow(boolean skipEmptyRow) {
		this.skipEmptyRow = skipEmptyRow;
	}

	/**
	 * 关闭流
	 */
	public void close() {
		if (!isAutoClose) {
			XLPIOUtil.closeReader(reader);
		}
		reader = null;
	}

	/**
	 * 获取文本文件每行处理后的数据
	 * 
	 * @param lineHandler
	 *            行处理器
	 * @param lineFilter
	 *            行过滤器
	 * @return 返回文本文件每行处理后的数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public <T> List<T> read(LineHandler<T> lineHandler, LineFilter lineFilter) throws IOException {
		List<T> list = new ArrayList<T>();
		int lineNo = 1;
		String line = null;
		LineData lineData;
		try {
			//读取每行数据
			while ((line = reader.readLine()) != null) {
				lineData = new LineData(lineNo++, line);
				//判断是否跳过空行
				if (skipEmptyRow && lineData.isEmpty()) {
					continue;
				}
				if (lineFilter == null || lineFilter.accept(lineData)) { 
					list.add(lineData.lineHandle(lineHandler));
				}
			}
		} finally {
			if (isAutoClose) {
				XLPIOUtil.closeReader(reader);
				reader = null;
			}
		}
		return list;
	}
	
	/**
	 * 获取文本文件每行数据
	 * 
	 * @return 返回文本文件每行数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public List<LineData> read() throws IOException {
		return read(null, null);
	}
	
	/**
	 * 获取文本文件每行数据
	 * 
	 * @param lineFilter
	 *            行过滤器
	 * @return 返回文本文件每行数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public List<LineData> read(LineFilter lineFilter) throws IOException {
		return read(null, lineFilter);
	}
	
	/**
	 * 获取文本文件每行处理后的数据
	 * 
	 * @param lineHandler
	 *            行处理器
	 * @return 返回文本文件每行处理后的数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public <T> List<T> read(LineHandler<T> lineHandler) throws IOException {
		return read(lineHandler, null);
	}
	
	/**
	 * 获取文本文件每行数据
	 * 
	 * @return 返回文本文件每行数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public List<String> readToStrings() throws IOException {
		return readToStrings(null);
	}
	
	/**
	 * 获取文本文件每行数据
	 * 
	 * @param lineFilter
	 *            行过滤器
	 * @return 返回文本文件每行数据, 假如为空文本文件，返回大小为0的空集合
	 * @throws IOException
	 *             假如io异常，则抛出该异常
	 */
	public List<String> readToStrings(LineFilter lineFilter) throws IOException {
		return read(lineData -> lineData.getLineData(), lineFilter);
	} 
}
