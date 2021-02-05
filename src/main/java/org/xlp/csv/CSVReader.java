package org.xlp.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.xlp.assertion.AssertUtils;
import org.xlp.assertion.IllegalObjectException;
import org.xlp.utils.XLPCharsetUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.collection.XLPCollectionUtil;
import org.xlp.utils.io.XLPIOUtil;
import org.xlp.utils.snumber.XLPMathUtil;

/**
 * <p>
 * 创建时间：2021年1月20日 下午11:15:50
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description csv文件读取类
 */
public class CSVReader {
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_CHARSET = XLPCharsetUtil.UTF8;

	/**
	 * 默认字符数组大小
	 */
	private static final int DEFAULT_CAPACITY = 255;

	/**
	 * 字符常量：回车符 {@code '\r'}
	 */
	public static final char CR = '\r';

	/**
	 * 字符常量：换行符 {@code '\n'}
	 */
	public static final char LF = '\n';

	/**
	 * csv文件读取配置对象
	 */
	private CSVReaderConfig config = new CSVReaderConfig();

	/**
	 * csv文件内容总行数（包括空行）
	 */
	private long allRowCount = 0;

	/**
	 * csv文件内容有效行数（不包括空行）
	 */
	private long validRowCount = 0;

	/**
	 * csv文件每行数据记录集合
	 */
	private List<CSVRow> csvRows = new ArrayList<CSVRow>();

	/**
	 * 构造函数
	 */
	public CSVReader() {

	}

	/**
	 * 构造函数
	 * 
	 * @param csvReaderConfig
	 */
	public CSVReader(CSVReaderConfig csvReaderConfig) {
		setConfig(csvReaderConfig);
	}

	/**
	 * 获取csv文件读取配置对象
	 * 
	 * @return
	 */
	public CSVReaderConfig getConfig() {
		return config;
	}

	/**
	 * 设置csv文件读取配置对象
	 * 
	 * @param csvReaderConfig
	 */
	public void setConfig(CSVReaderConfig csvReaderConfig) {
		if (csvReaderConfig != null) {
			this.config = csvReaderConfig;
		}
	}

	/**
	 * 获取csv文件内容总行数（包括空行）
	 * 
	 * @return
	 */
	public long getAllRowCount() {
		return allRowCount;
	}

	/**
	 * 获取csv文件内容有效行数（不包括空行）
	 * 
	 * @return
	 */
	public long getValidRowCount() {
		return validRowCount;
	}

	/**
	 * @return csv文件数据
	 */
	public List<CSVRow> getCsvRows() {
		return csvRows;
	}

	/**
	 * 从InputStream中读取CSV数据
	 * 
	 * @param csvIn
	 *            CSV文件输入流
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	public void read(InputStream csvIn) {
		read(csvIn, DEFAULT_CHARSET);
	}

	/**
	 * 从InputStream中读取CSV数据
	 * 
	 * @param csvIn
	 *            CSV文件输入流
	 * @param charsetName
	 *            文件字符编码
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	public void read(InputStream csvIn, String charsetName) {
		AssertUtils.isNotNull(csvIn, "csvIn paramter is null!");
		charsetName = XLPStringUtil.isEmpty(charsetName) ? DEFAULT_CHARSET : charsetName.trim();
		read(XLPIOUtil.getReader(csvIn, charsetName));
	}

	/**
	 * 从Reader中读取CSV数据
	 * 
	 * @param csvReader
	 *            CSV文件输入流
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	public void read(Reader csvReader) {
		AssertUtils.isNotNull(csvReader, "csvReader paramter is null!");
		List<char[]> charList = readToChars(csvReader);
		read(charList);
	}

	/**
	 * 从File中读取CSV数据
	 * 
	 * @param csvFlie
	 *            CSV文件
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws IllegalObjectException 假如给定的文件是目录或不存在，则抛出该异常
	 * @throws CSVException
	 *             从csv文件中读取数据失败，则抛出该异常
	 */
	public void read(File csvFlie) {
		AssertUtils.assertFile(csvFlie);
		Reader reader = null;
		try {
			reader = new BufferedReader(new FileReader(csvFlie));
			read(reader); 
		} catch (FileNotFoundException e) {
			throw new CSVException(e);
		}finally {
			XLPIOUtil.closeReader(reader);
		}
	}

	/**
	 * 从File中读取CSV数据
	 * 
	 * @param csvFlie
	 *            CSV文件
	 * @param charsetName
	 *            文件字符编码
	 * @throws NullPointerException
	 *             假如参数为null，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	public void read(File csvFlie, String charsetName) {
		AssertUtils.assertFile(csvFlie);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(csvFlie);
			read(inputStream, charsetName); 
		} catch (FileNotFoundException e) {
			throw new CSVException(e);
		}finally {
			XLPIOUtil.closeInputStream(inputStream);
		}
	}
	
	/**
	 * 从csv格式字符串解析成CSV数据
	 * 
	 * @param csvFormatData
	 *            csv格式字符串
	 * @throws CSVException
	 *             解析数据失败，则抛出该异常
	 */
	public void parse(String csvFormatData) {
		if (csvFormatData != null) {
			read(XLPCollectionUtil.initList(csvFormatData.toCharArray()));
		}
	}

	/**
	 * 从CSV文件路径中读取CSV数据
	 * 
	 * @param csvFliePath
	 *            CSV文件路径
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 * @throws IllegalObjectException 假如给定的文件是目录或不存在，则抛出该异常
	 * @throws CSVException
	 *             从csv文件中读取数据失败，则抛出该异常
	 */
	public void read(String csvFliePath) {
		AssertUtils.isNotNull(csvFliePath, "csvFliePath paramter is null or empty!");
		read(new File(csvFliePath)); 
	}

	/**
	 * 从CSV文件路径中读取CSV数据
	 * 
	 * @param csvFliePath
	 *            CSV文件路径
	 * @param charsetName
	 *            文件字符编码
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 * @throws IllegalObjectException
	 *             假如给定的文件是目录或不存在，则抛出该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	public void read(String csvFliePath, String charsetName) {
		AssertUtils.isNotNull(csvFliePath, "csvFliePath paramter is null or empty!");
		read(new File(csvFliePath), charsetName); 
	}
	
	/**
	 * 从CSV数据字符数组集合中读取CSV数据
	 * 
	 * @param charList
	 *            CSV数据字符数组集合
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	void read(List<char[]> charList) {
		AssertUtils.isNotNull(charList, "charList paramter is null!");
		int size = charList.size();
		// 分别为csv文件行号，实际有效行号（跳过空行），csv每行数据对应的行号（包括空行）
		long lineNo = 0, suitLineNo = 0, realityNo = 1;
		// 是否在特殊字符包含内，默认特殊字符是"
		boolean inQuotes = false;
		char preChar = 0;
		char[] cs;
		int arrLen;
		CSVRow csvRow = new CSVRow(realityNo, config);
		boolean hasHeader = config.isHasHeader();
		int delimiterCount = 0;
		String temp = null;
		Map<Integer, String> headerMap = null;
		int count = 0;
		for (int i = 0; i < size; i++) {
			cs = charList.get(i);
			arrLen = cs.length;
			for (int j = 0; j < arrLen; j++) {
				// 判断是否在特殊字符包含内
				if (cs[j] == config.getTextDelimiter()) {
					delimiterCount++;
				} else if (delimiterCount > 0) {
					if (XLPMathUtil.isEven(delimiterCount) && !inQuotes) {
						preChar = config.getTextDelimiter();
					} else if (!XLPMathUtil.isEven(delimiterCount) && !inQuotes) {
						inQuotes = true;
						preChar = config.getTextDelimiter();
					} else if (!XLPMathUtil.isEven(delimiterCount) && inQuotes) {
						inQuotes = false;
						preChar = config.getTextDelimiter();
					}
					delimiterCount = 0;
				}

				// 判断是否在特殊字符内
				if (!inQuotes && delimiterCount == 0) {
					// 判断前一个特殊字符是否是"
					if (preChar == config.getTextDelimiter()) {
						if (cs[j] != config.getFieldSeparator() && cs[j] != LF && cs[j] != CR) {
							throw new CSVException("..." + new String(cs) + "...不是csv格式数据，解析失败！");
						}
					}

					if (cs[j] == config.getFieldSeparator()) {
						String cellData = String.valueOf(cs, j - count, count);
						csvRow.addCellData(temp == null ? cellData : temp + cellData);
						preChar = config.getFieldSeparator();
						temp = null;
						count = 0;
					} else if (cs[j] == CR || (cs[j] == LF && preChar != CR)) {
						String cellData = String.valueOf(cs, j - count, count);
						csvRow.addCellData(temp == null ? cellData : temp + cellData);
						boolean rowIsEmpty = csvRow.isEmpty();
						if (headerMap == null && hasHeader && !rowIsEmpty) {
							csvRow.setHeader(true);
							headerMap = csvRow.getHeaderMap();
						}
						// 设置标题行
						csvRow.setHeaderMap(headerMap);
						// 判断是否跳过空行
						if ((config.isSkipEmptyRow() && !rowIsEmpty) || !config.isSkipEmptyRow()) {
							csvRows.add(csvRow);
						}
						if (!rowIsEmpty) {
							suitLineNo++;
						}
						temp = null;
						count = 0;
						preChar = cs[j] == LF ? LF : CR;
						csvRow = new CSVRow(++realityNo, config);
						lineNo++;
					} else if (cs[j] == LF && preChar == CR) {
						continue;
					} else {
						count++;
						if (j == arrLen - 1) {
							temp = String.valueOf(cs, j - count + 1, count);
							count = 0;
						}
					}
				} else {
					count++;
					if (j == arrLen - 1) {
						temp = String.valueOf(cs, j - count + 1, count);
						count = 0;
					}
				}
			}
		}
		// 处理最后一个单元格数据
		if (temp != null) {
			csvRow.addCellData(temp);
			boolean rowIsEmpty = csvRow.isEmpty();
			if (headerMap == null && hasHeader && !rowIsEmpty) {
				csvRow.setHeader(true);
				headerMap = csvRow.getHeaderMap();
			}
			// 设置标题行
			csvRow.setHeaderMap(headerMap);
			// 判断是否跳过空行
			if ((config.isSkipEmptyRow() && !rowIsEmpty) || !config.isSkipEmptyRow()) {
				csvRows.add(csvRow);
			}
			if (!rowIsEmpty) {
				suitLineNo++;
			}
			temp = null;
			lineNo++;
		}
		allRowCount = lineNo;
		validRowCount = suitLineNo;
		charList = null;
	}

	/**
	 * 把Reader流转成字符数组
	 * 
	 * @param csvReader
	 * @param capacity
	 *            字符数组的大小
	 * @return
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 * @throws IllegalArgumentException
	 *             假如给定的字符数组的大小不大于0，则抛出该异常
	 * @throws CSVException
	 *             从csv输入流中读取数据失败，则抛出该异常
	 */
	protected List<char[]> readToChars(Reader csvReader, int capacity) {
		AssertUtils.isNotNull(csvReader, "csvReader paramter is null!");
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity paramter must than zero!");
		}
		List<char[]> charList = new ArrayList<char[]>();
		char[] buff = new char[capacity];
		int len;
		try {
			while ((len = csvReader.read(buff)) > 0) {
				if (len < buff.length) {
					buff = Arrays.copyOfRange(buff, 0, len);
					charList.add(buff);
				} else {
					charList.add(buff);
					buff = new char[capacity];
				}
			}
		} catch (IOException e) {
			throw new CSVException("从csv输入流中读取数据失败", e);
		}
		return charList;
	}

	/**
	 * 把Reader流转成字符数组
	 * 
	 * @param csvReader
	 * @return
	 * @throws NullPointerException
	 *             假如参数为空，则抛该异常
	 */
	protected List<char[]> readToChars(Reader csvReader) {
		return readToChars(csvReader, DEFAULT_CAPACITY);
	}
}
