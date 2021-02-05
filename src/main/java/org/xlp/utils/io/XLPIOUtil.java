package org.xlp.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.xlp.utils.XLPStringUtil;

/**
 * IO操作工具类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-03-20
 *         </p>
 * @version 1.0
 *          <p>
 *          主要功能是各种IO相互转换
 */
public class XLPIOUtil {
	/**
	 * 默认缓冲区大小
	 */
	private final static int DEFAULT_BUFFER_SIZE = 1024;
	/**
	 * 默认字符编码
	 */
	public final static String DEFAULT_ENCODIND = "utf-8";

	/**
	 * 把文件内容转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param file
	 *            要转换的文件流
	 * @return 假如file为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(File file) throws IOException {
		if (file == null) {
			return null;
		}

		return IOToByteArray(new FileInputStream(file), true);
	}

	/**
	 * 把文件流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param filePath
	 *            要转换的文件路径
	 * @return 假如filePath为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(String filePath) throws IOException {
		if (filePath == null) {
			return null;
		}
		return IOToByteArray(new File(filePath));
	}

	/**
	 * 把InputStream流转换成字节数组,调用此函数，传入的输入流自动关闭
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(InputStream in) throws IOException {
		return IOToByteArray(in, true);
	}

	/**
	 * 把InputStream流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(InputStream in, boolean closeStream) throws IOException {
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
		copy(in, bos, closeStream);

		return bos.toByteArray();
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>OutputStream</code>.
	 * 调用此函数传入的流不关闭，要手动关闭资源流
	 * 
	 * @param in
	 * @param out
	 * @return 返回<code>InputStream</code>字节流的大小
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常 假如in或out为空，则抛出空指针异常
	 */
	public static long copy(InputStream in, OutputStream out) throws IOException {
		return copy(in, out, false);
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>OutputStream</code>.
	 * 
	 * @param in
	 * @param out
	 * @param closeTheseStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @return 返回<code>InputStream</code>字节流的大小
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常 假如in或out为空，则抛出空指针异常
	 */
	public static long copy(InputStream in, OutputStream out, boolean closeTheseStream) throws IOException {
		if (in == null) {
			throw new NullPointerException("copy()函数中参数【InputStream:in】为空");
		}
		if (out == null) {
			throw new NullPointerException("copy()函数中参数【OutputStream:out】为空");
		}

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;

		int n = 0;
		try {
			while ((n = in.read(buffer)) != -1) {
				out.write(buffer, 0, n);
				count += n;
			}
		} catch (IOException e) {
			throw new IOException("字节流读取过程中出错");
		} finally {
			if (closeTheseStream) {
				closeInputStream(in);
				closeOutputStream(out);
			}
		}
		return count;
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>Writer</code>. 假如传入的流为空，则抛出空指针异常
	 * 
	 * @param reader
	 * @param writer
	 * @param closeTheseStream
	 *            closeTheseStream 是否关闭传入的流，=true，关闭，=false不关闭
	 * @return 字符流中字符的个数
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static long copy(Reader reader, Writer writer, boolean closeTheseStream) throws IOException {
		if (reader == null) {
			throw new NullPointerException("copy()函数中参数【Reader:reader】为空");
		}
		if (writer == null) {
			throw new NullPointerException("copy()函数中参数【Writer:writer】为空");
		}

		char[] buffer = new char[DEFAULT_BUFFER_SIZE];// 初始化缓冲区大小
		long count = 0;// 统计字符个数
		int len = 0; // 一次读取的字符数
		try {
			while ((len = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, len);
				writer.flush();
				count += len;
			}
		} catch (IOException e) {
			throw new IOException("字符流读取过程中出错");
		} finally {
			if (closeTheseStream) {
				closeReader(reader);
				closeWrite(writer);
			}
		}
		return count;
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>OutputStream</code>. 假如传入的流为空，则抛出空指针异常
	 * 
	 * @param reader
	 * @param out
	 * @param closeTheseStream
	 *            closeTheseStream 是否关闭传入的流，=true，关闭，=false不关闭
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(Reader reader, OutputStream out, boolean closeTheseStream) throws IOException {
		copy(reader, out, closeTheseStream, DEFAULT_ENCODIND);
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>OutputStream</code>. 假如传入的流为空，则抛出空指针异常.
	 * 此函数默认是不关闭传进去的流，要手动关闭
	 * 
	 * @param reader
	 * @param out
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(Reader reader, OutputStream out) throws IOException {
		copy(reader, out, false);
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>OutputStream</code>. 假如传入的流为空，则抛出空指针异常
	 * 
	 * @param reader
	 * @param out
	 * @param closeTheseStream
	 *            closeTheseStream 是否关闭传入的流，=true，关闭，=false不关闭
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式“utf-8”
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(Reader reader, OutputStream out, boolean closeTheseStream, String encoding)
			throws IOException {
		BufferedWriter bWriter = null;

		if (encoding == null || encoding.trim().length() == 0) {
			bWriter = new BufferedWriter(new OutputStreamWriter(out, DEFAULT_ENCODIND));
		} else {
			bWriter = new BufferedWriter(new OutputStreamWriter(out, encoding));
		}

		copy(reader, bWriter, closeTheseStream);
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>OutputStream</code>. 假如传入的流为空，则抛出空指针异常.
	 * 此函数默认是不关闭传进去的流，要手动关闭
	 * 
	 * @param reader
	 * @param out
	 * @param encoding
	 *            字符编码格式
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(Reader reader, OutputStream out, String encoding) throws IOException {
		copy(reader, out, false, encoding);
	}

	/**
	 * 把一个<code>Reader</code> 复制到一个<code>Writer</code>. 假如传入的流为空，则抛出空指针异常。
	 * 此函数默认是不关闭传进去的流，要手动关闭
	 * 
	 * @param reader
	 * @param writer
	 * @return 字符流中字符的个数
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static long copy(Reader reader, Writer writer) throws IOException {
		return copy(reader, writer, false);
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>Writer</code>. 假如传入的流为空，则抛出空指针异常
	 * 
	 * @param in
	 * @param writer
	 * @param closeTheseStream
	 *            closeTheseStream 是否关闭传入的流，=true，关闭，=false不关闭
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(InputStream in, Writer writer, boolean closeTheseStream, String encoding)
			throws IOException {
		BufferedReader reader = null;
		if (XLPStringUtil.isEmpty(encoding)) {
			reader = new BufferedReader(new InputStreamReader(in), DEFAULT_BUFFER_SIZE);
		} else {
			reader = new BufferedReader(new InputStreamReader(in, encoding));
		}

		copy(reader, writer, closeTheseStream);
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>Writer</code>.
	 * 假如传入的流为空，则抛出空指针异常,编码格式采用默认格式
	 * 
	 * @param in
	 * @param writer
	 * @param closeTheseStream
	 *            closeTheseStream 是否关闭传入的流，=true，关闭，=false不关闭
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(InputStream in, Writer writer, boolean closeTheseStream) throws IOException {
		copy(in, writer, closeTheseStream, DEFAULT_ENCODIND);
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>Writer</code>. 假如传入的流为空，则抛出空指针异常
	 * 此函数默认是不关闭传进去的流，要手动关闭
	 * 
	 * @param in
	 * @param writer
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(InputStream in, Writer writer, String encoding) throws IOException {
		copy(in, writer, false, encoding);
	}

	/**
	 * 把一个<code>InputStream</code> 复制到一个<code>Writer</code>.
	 * 假如传入的流为空，则抛出空指针异常.编码格式采用默认格式. 此函数默认是不关闭传进去的流，要手动关闭
	 * 
	 * @param in
	 * @param writer
	 * @throws IOException
	 *             当IO访问出错时，抛出该异常
	 */
	public static void copy(InputStream in, Writer writer) throws IOException {
		copy(in, writer, false);
	}

	/**
	 * 把Reader流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(Reader reader, boolean closeStream, String encoding) throws IOException {
		if (reader == null) {
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
		copy(reader, bos, closeStream, encoding);
		return bos.toByteArray();
	}

	/**
	 * 把Reader流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 编码格式采用默认格式
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(Reader reader, boolean closeStream) throws IOException {
		return IOToByteArray(reader, closeStream, DEFAULT_ENCODIND);
	}

	/**
	 * 把Reader流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式“utf-8”
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(Reader reader, String encoding) throws IOException {
		return IOToByteArray(reader, true, encoding);
	}

	/**
	 * 把Reader流转换成字节数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭,编码格式采用默认格式
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static byte[] IOToByteArray(Reader reader) throws IOException {
		return IOToByteArray(reader, true);
	}

	/**
	 * 把InputStream流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(InputStream in, boolean closeStream, String encoding) throws IOException {
		if (in == null) {
			return null;
		}

		CharArrayWriter writer = new CharArrayWriter(DEFAULT_BUFFER_SIZE);
		copy(in, writer, closeStream, encoding);
		return writer.toCharArray();
	}

	/**
	 * 把InputStream流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 编码格式采用默认格式
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(InputStream in, boolean closeStream) throws IOException {
		return IOToCharArray(in, closeStream, DEFAULT_ENCODIND);
	}

	/**
	 * 把InputStream流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(InputStream in, String encoding) throws IOException {
		return IOToCharArray(in, true, encoding);
	}

	/**
	 * 把InputStream流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭,编码格式采用默认格式
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(InputStream in) throws IOException {
		return IOToCharArray(in, true);
	}

	/**
	 * 把Reader流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param reader
	 *            输入流
	 * @param closeStream
	 *            假如reader为null，返回null
	 * @return 假如reader为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(Reader reader, boolean closeStream) throws IOException {
		if (reader == null) {
			return null;
		}

		CharArrayWriter writer = new CharArrayWriter(DEFAULT_BUFFER_SIZE);
		copy(reader, writer, closeStream);
		return writer.toCharArray();
	}

	/**
	 * 把Reader流转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(Reader reader) throws IOException {
		return IOToCharArray(reader, true);
	}

	/**
	 * 把文件内容转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param file
	 * @return 假如file为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(File file) throws IOException {
		if (file == null) {
			return null;
		}
		return IOToCharArray(new BufferedReader(new FileReader(file)));
	}

	/**
	 * 把文件内容转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param file
	 * @param encoding
	 *            编码格式
	 * @return 假如file为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(File file, String encoding) throws IOException {
		if (file == null) {
			return null;
		}
		if (XLPStringUtil.isEmpty(encoding))
			encoding = DEFAULT_ENCODIND;
		return IOToCharArray(new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding)));
	}

	/**
	 * 把文件内容转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param filePath
	 * @return 假如filePath为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(String filePath) throws IOException {
		if (filePath == null) {
			return null;
		}
		return IOToCharArray(new File(filePath));
	}

	/**
	 * 把文件内容转换成字符数组
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，文件内容过大
	 * </p>
	 * 
	 * @param filePath
	 * @param encoding
	 *            编码格式
	 * @return 假如filePath为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static char[] IOToCharArray(String filePath, String encoding) throws IOException {
		if (filePath == null) {
			return null;
		}
		return IOToCharArray(new File(filePath), encoding);
	}

	/**
	 * 把InputStream流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(InputStream in, boolean closeStream, String encoding) throws IOException {
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
		copy(in, bos, closeStream);
		return bos.toString(encoding);
	}

	/**
	 * 把InputStream流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 编码格式采用默认格式
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(InputStream in, boolean closeStream) throws IOException {
		return toString(in, closeStream, DEFAULT_ENCODIND);
	}

	/**
	 * 把InputStream流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(InputStream in, String encoding) throws IOException {
		return toString(in, true, encoding);
	}

	/**
	 * 把InputStream流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭,编码格式采用默认格式
	 * 
	 * @param in
	 *            输入流
	 * @return 假如in为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(InputStream in) throws IOException {
		return toString(in, true);
	}

	/**
	 * 把File转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param file
	 * @return 假如file为null，返回null
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(File file, String encoding) throws IOException {
		if (file == null) {
			return null;
		}
		return toString(new FileInputStream(file), encoding);
	}

	/**
	 * 把File转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param filePath
	 * @return 假如filePath为null，返回null
	 * @param encoding
	 *            字符编码格式， 当encoding为空时，用默认的字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(String filePath, String encoding) throws IOException {
		if (filePath == null) {
			return null;
		}
		return toString(new File(filePath), encoding);
	}

	/**
	 * 把Reader流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @param closeStream
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(Reader reader, boolean closeStream) throws IOException {
		if (reader == null) {
			return null;
		}

		CharArrayWriter writer = new CharArrayWriter(DEFAULT_BUFFER_SIZE);
		copy(reader, writer, true);
		return writer.toString();
	}

	/**
	 * 把Reader流转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 此函数默认是自动关闭传进去的流，不用手动关闭
	 * 
	 * @param reader
	 *            输入流
	 * @return 假如reader为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(Reader reader) throws IOException {
		return toString(reader, true);
	}

	/**
	 * 把File转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param file
	 * @return 假如file为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(File file) throws IOException {
		if (file == null) {
			return null;
		}
		return toString(new BufferedReader(new FileReader(file)));
	}

	/**
	 * 把File转换成字符串
	 * <p>
	 * 当抛出java.lang.OutOfMemoryError或java.lang.NegativeArraySizeException
	 * 时，输入流内容过大
	 * </p>
	 * 
	 * @param filePath
	 * @return 假如filePath为null，返回null
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 */
	public static String toString(String filePath) throws IOException {
		if (filePath == null) {
			return null;
		}
		return toString(new File(filePath));
	}

	/**
	 * 关闭字节输入流
	 * 
	 * @param in
	 * @return 当关闭输入流出错时，返回false，否则返回true
	 */
	public static boolean closeInputStream(InputStream in) {
		boolean closeSucc = false;
		if (in != null) {
			try {
				in.close();
				closeSucc = true;
			} catch (IOException e) {
			}
		}
		return closeSucc;
	}

	/**
	 * 关字节闭输出流
	 * 
	 * @param out
	 * @return 当关闭输入流出错时，返回false，否则返回true
	 */
	public static boolean closeOutputStream(OutputStream out) {
		boolean closeSucc = false;
		if (out != null) {
			try {
				out.close();
				closeSucc = true;
			} catch (IOException e) {
			}
		}
		return closeSucc;
	}

	/**
	 * 关闭字符输入流
	 * 
	 * @param in
	 * @return 当关闭输入流出错时，返回false，否则返回true
	 */
	public static boolean closeReader(Reader reader) {
		boolean closeSucc = false;
		if (reader != null) {
			try {
				reader.close();
				closeSucc = true;
			} catch (IOException e) {
			}
		}
		return closeSucc;
	}

	/**
	 * 关字符闭输出流
	 * 
	 * @param writer
	 * @return 当关闭输入流出错时，返回false，否则返回true
	 */
	public static boolean closeWrite(Writer writer) {
		boolean closeSucc = false;
		if (writer != null) {
			try {
				writer.close();
				closeSucc = true;
			} catch (IOException e) {
			}
		}
		return closeSucc;
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(byte[] data, OutputStream out) throws IOException {
		if (data != null) {
			out.write(data);
			out.flush();
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 *
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @param charSet
	 *            数据写入时，所用编码格式
	 * @param close
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	private static void write(String data, OutputStream out, String charSet, boolean close) throws IOException {
		if (data != null) {
			String temp = null;
			int len = data.length();
			try {
				for (int i = 0; i < len;) {
					if (len > 255) {
						temp = data.substring(0, 255);
						data = data.substring(255);
						len = data.length();
					} else {
						temp = data;
						len = len - 255;
					}

					if (XLPStringUtil.isEmpty(charSet))
						write(temp.getBytes(), out);
					else
						write(temp.getBytes(charSet), out);
				}
			} finally {
				if (close)
					closeOutputStream(out);
			}
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @param charSet
	 *            数据写入时，所用编码格式
	 * 
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(String data, OutputStream out, String charSet) throws IOException {
		write(data, out, charSet, false);
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 *
	 * 编码格式采用指定编码格式
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * 
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(String data, OutputStream out) throws IOException {
		write(data, out, null, false);
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @param close
	 *            是否关闭传入的流，=true，关闭，否则不关闭
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	private static void write(String data, Writer writer, boolean close) throws IOException {
		if (data != null) {
			try {
				writer.write(data);
				writer.flush();
			} finally {
				if (close)
					closeWrite(writer);
			}
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(String data, Writer writer) throws IOException {
		write(data, writer, false);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param file
	 *            文件对象
	 * @param charSet
	 *            编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件对象为null,则抛出该异常
	 */
	public static void writeToFile(String data, File file, String charSet) throws IOException {
		OutputStream out = new FileOutputStream(file);
		write(data, out, charSet, true);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param file
	 *            文件对象
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件对象为null,则抛出该异常
	 */
	public static void writeToFile(String data, File file) throws IOException {
		writeToFile(data, file, null);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param dir
	 *            文件目录
	 * @param filename
	 *            文件名
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件目录或文件名为null,则抛出该异常
	 */
	public static void writeToFile(String data, String filename, File dir) throws IOException {
		writeToFile(data, filename, dir, null);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param dir
	 *            文件目录
	 * @param filename
	 *            文件名
	 * @param charSet
	 *            编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件目录或文件名为null,则抛出该异常
	 */
	public static void writeToFile(String data, String filename, File dir, String charSet) throws IOException {
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dir, filename);
		writeToFile(data, file, charSet);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param filePath
	 *            文件路径
	 * @param charSet
	 *            编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件路径为null,则抛出该异常
	 */
	public static void writeToFile(String data, String filePath, String charSet) throws IOException {
		if (filePath == null)
			throw new NullPointerException("参数文件路径必须不为null");

		File file = new File(filePath);
		File dir = file.getParentFile();
		if (!dir.exists())
			dir.mkdirs();
		writeToFile(data, file, charSet);
	}

	/**
	 * 把指定的数据写入指定的文件中
	 * 
	 * @param data
	 *            输入数据
	 * @param filePath
	 *            文件路径
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数文件路径为null,则抛出该异常
	 */
	public static void writeToFile(String data, String filePath) throws IOException {
		writeToFile(data, filePath, (String) null);
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @param charSet
	 *            字符编码
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(byte[] data, Writer writer, String charSet) throws IOException {
		if (data != null) {
			if (XLPStringUtil.isEmpty(charSet))
				writer.write(new String(data));
			else
				writer.write(new String(data, charSet));
			writer.flush();
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @param charSet
	 *            字符编码
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(byte[] data, Writer writer, Charset charSet) throws IOException {
		if (data != null) {
			if (charSet == null)
				writer.write(new String(data));
			else
				writer.write(new String(data, charSet));
			writer.flush();
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(byte[] data, Writer writer) throws IOException {
		write(data, writer, (Charset) null);
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param writer
	 *            输出流
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(char[] data, Writer writer) throws IOException {
		if (data != null) {
			writer.write(data);
			writer.flush();
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @param charSetName
	 *            字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(char[] data, OutputStream out, String charSetName) throws IOException {
		if (data != null) {
			if (XLPStringUtil.isEmpty(charSetName))
				write(new String(data).getBytes(), out);
			else
				write(new String(data).getBytes(charSetName), out);
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @param charSet
	 *            字符编码格式
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(char[] data, OutputStream out, Charset charSet) throws IOException {
		if (data != null) {
			if (charSet == null)
				write(new String(data).getBytes(), out);
			else
				write(new String(data).getBytes(charSet), out);
		}
	}

	/**
	 * 把指定的数据写入指定的输出流中
	 * 
	 * @param data
	 *            输入数据
	 * @param out
	 *            输出流
	 * @throws IOException
	 *             当IO操作出错时，抛出该异常
	 * @throws NullPointerException
	 *             假如参数输出流为null,则抛出该异常
	 */
	public static void write(char[] data, OutputStream out) throws IOException {
		write(data, out, (String) null);
	}

	/**
	 * 获得一个BufferedReader
	 *
	 * @param in
	 *            输入流
	 * @param charsetName
	 *            字符集
	 * @return BufferedReader对象
	 */
	public static BufferedReader getReader(InputStream in, String charsetName) {
		Charset charset = XLPStringUtil.isEmpty(charsetName) ? null : Charset.forName(charsetName.trim());
		return getReader(in, charset);
	}
	
	/**
	 * 获得一个BufferedReader
	 *
	 * @param in
	 *            输入流
	 * @param charset
	 *            字符集
	 * @return BufferedReader对象
	 */
	public static BufferedReader getReader(InputStream in, Charset charset) {
		if (null == in) {
			return null;
		}

		InputStreamReader reader;
		if (charset == null) {
			reader = new InputStreamReader(in);
		} else {
			reader = new InputStreamReader(in, charset);
		}

		return new BufferedReader(reader);
	}
	
	/**
	 * 获得一个BufferedReader
	 *
	 * @param reader
	 *            输入流
	 * @return BufferedReader对象
	 */
	public static BufferedReader getBufferedReader(Reader reader) {
		if (null == reader) {
			return null;
		}
		
		return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
	}
	
	/**
	 * 获得一个BufferedInputStream
	 *
	 * @param inputStream
	 *            输入流
	 * @return BufferedInputStream对象
	 */
	public static BufferedInputStream getBufferedInputStream(InputStream inputStream) {
		if (null == inputStream) {
			return null;
		}
		
		return (inputStream instanceof BufferedInputStream) ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
	}
	
	/**
	 * 获得一个BufferedOutputStream
	 *
	 * @param outputStream
	 *            输出流
	 * @return BufferedOutputStream对象
	 */
	public static BufferedOutputStream getBufferedOutputStream(OutputStream outputStream) {
		if (null == outputStream) {
			return null;
		}
		
		return (outputStream instanceof BufferedOutputStream) ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
	}
	
	/**
	 * 获得一个BufferedWriter
	 *
	 * @param out
	 *            输出流
	 * @param charsetName
	 *            字符集
	 * @return BufferedWriter对象
	 */
	public static BufferedWriter getWriter(OutputStream out, String charsetName) {
		Charset charset = XLPStringUtil.isEmpty(charsetName) ? null : Charset.forName(charsetName.trim());
		return getWriter(out, charset);
	}
	
	/**
	 * 获得一个BufferedWriter
	 *
	 * @param out
	 *            输出流
	 * @param charset
	 *            字符集
	 * @return BufferedWriter对象
	 */
	public static BufferedWriter getWriter(OutputStream out, Charset charset) {
		if (null == out) {
			return null;
		}

		OutputStreamWriter writer;
		if (charset == null) {
			writer = new OutputStreamWriter(out);
		} else {
			writer = new OutputStreamWriter(out, charset);
		}

		return new BufferedWriter(writer);
	}
	
	/**
	 * 获得一个BufferedWriter
	 *
	 * @param writer
	 *            输出流
	 * @return BufferedWriter对象
	 */
	public static BufferedWriter getBufferedWriter(Writer writer) {
		if (null == writer) {
			return null;
		}
		
		return (writer instanceof BufferedWriter) ? (BufferedWriter) writer : new BufferedWriter(writer);
	}
}
