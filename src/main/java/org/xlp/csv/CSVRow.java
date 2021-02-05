package org.xlp.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xlp.utils.XLPStringUtil;

/**
 * <p>
 * 创建时间：2021年1月21日 下午8:50:52
 * </p>
 * 
 * @author xlp
 * @version 1.0
 * @Description csv每行数据对象
 */
public class CSVRow {
	private CSVReaderConfig config;
	
	/**
	 * 行号
	 */
	private long lineNo;

	/**
	 * 每行数据
	 */
	private List<String> rowData = new ArrayList<String>();

	/**
	 * csv标题数据，key：列号从0开始，value：标题名称
	 */
	private Map<Integer, String> headerMap;

	/**
	 * 是否是标题行，默认非标题行
	 */
	private boolean isHeader = false;

	/**
	 * 构造函数
	 */
	public CSVRow() {
		this(null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param config
	 */
	public CSVRow(CSVReaderConfig config) {
		setConfig(config);
	}

	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 */
	public CSVRow(long lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 * @param config
	 */
	public CSVRow(long lineNo, CSVReaderConfig config) {
		this(lineNo);
		setConfig(config);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 * @param rowData
	 */
	public CSVRow(long lineNo, List<String> rowData) {
		this(lineNo, rowData, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 * @param rowData
	 * @param headerMap
	 */
	public CSVRow(long lineNo, List<String> rowData, Map<Integer, String> headerMap) {
		this(lineNo, rowData, headerMap, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param lineNo
	 * @param rowData
	 * @param headerMap
	 * @param isHeader
	 */
	public CSVRow(long lineNo, List<String> rowData, Map<Integer, String> headerMap, boolean isHeader) {
		this.lineNo = lineNo;
		setRowData(rowData);
		setHeaderMap(headerMap);
		this.isHeader = isHeader;
	}

	/**
	 * 获取行号
	 * 
	 * @return
	 */
	public long getLineNo() {
		return lineNo;
	}

	/**
	 * 设置行号
	 * 
	 * @param lineNo
	 */
	public void setLineNo(long lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取每行数据
	 * 
	 * @return
	 */
	public List<String> getRowData() {
		return rowData;
	}

	/**
	 * 设置每行数据
	 * 
	 * @param rowData
	 */
	public void setRowData(List<String> rowData) {
		if (rowData != null) {
			this.rowData = rowData;
		}
	}

	/**
	 * 设置单元数据
	 * 
	 * @param cellData
	 */
	public void addCellData(String cellData) {
		String textWrap = config.getTextDelimiter() + "";
		rowData.add(unwrap(textWrap, textWrap, cellData).replace(textWrap + textWrap, textWrap));
	}

	/**
	 * 设置单元数据
	 * 
	 * @param index
	 * @param cellData
	 */
	public void addCellData(int index, String cellData) {
		String textWrap = config.getTextDelimiter() + "";
		rowData.add(index, unwrap(textWrap, textWrap, cellData).replace(textWrap + textWrap, textWrap));
	}

	/**
	 * 去除前后包装字符
	 * 
	 * @param prefix 前缀包装字符
	 * @param suffix 后缀包装字符
	 * @param cellData 要去除包装字符的字符串
	 * @return 返回去除包装字符的字符串，假如要去除包装字符的字符串为null，返回""
	 */
	protected String unwrap(String prefix, String suffix, String cellData){
		cellData = XLPStringUtil.nullToEmpty(cellData);
		if (cellData.startsWith(prefix)) {
			cellData = cellData.substring(prefix.length());
		}
		if (cellData.endsWith(suffix)) {
			cellData = cellData.substring(0, cellData.length() - suffix.length());
		}
		return cellData;
	}
	
	/**
	 * 获取csv头部数据，key：列号从0开始，value：头部名称
	 * 
	 * @return
	 */
	public Map<Integer, String> getHeaderMap() {
		if (isHeader && headerMap == null) {
			headerMap = new HashMap<>();
			int size = getAllColumnCount();
			for (int i = 0; i < size; i++) {
				headerMap.put(Integer.valueOf(i), rowData.get(i)); 
			}
		}
		return headerMap;
	}

	/**
	 * 设置csv头部数据，key：列号从0开始，value：头部名称
	 * 
	 * @param headerMap
	 */
	public void setHeaderMap(Map<Integer, String> headerMap) {
		this.headerMap = headerMap;
	}

	/**
	 * 获取总数据列数（包括空列）
	 * 
	 * @return
	 */
	public int getAllColumnCount() {
		return rowData == null ? 0 : rowData.size();
	}

	/**
	 * 是否是标题行，默认非标题行
	 * 
	 * @return true时标题行，false非标题行
	 */
	public boolean isHeader() {
		return isHeader;
	}

	/**
	 * 是否是标题行，默认非标题行
	 * 
	 * @param isHeader
	 *            true时标题行，false非标题行
	 */
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}

	/**
	 * 根据列索引获取该列的数据
	 * 
	 * @param columnIndex
	 *            列索引号，从0开始
	 * @return 假如索引号不小于该行列数或小于0，则返回null，否则该列值
	 */
	public String getCellData(int columnIndex) {
		return (columnIndex < 0 || getAllColumnCount() <= columnIndex) ? null : rowData.get(columnIndex);
	}

	/**
	 * 根据标题栏名称获取该列值
	 * 
	 * @param tilteName
	 *            标题栏名称
	 * @return 假如标题栏名称不存在，则返回null，否则该列值
	 */
	public String getCellData(String tilteName) {
		headerMap = getHeaderMap();
		if (headerMap == null) {
			return null;
		}
		int columnIndex = -1;
		for (Entry<Integer, String> entry : headerMap.entrySet()) {
			if (entry.getValue().equals(tilteName)) {
				columnIndex = entry.getKey().intValue();
				break;
			}
		}
		return getCellData(columnIndex);
	}

	/**
	 * 把每行数据与标题栏结合，转换成map
	 * <ul>
	 * <li>1. key: 标题栏名称， value：该列值</li>
	 * <li>2. 假如没有标题行，则用列索引做key值</li>
	 * <li>3. 假如标题栏列数小于数据列数，则用索引值扩充key值</li>
	 * <li>4. 假如标题栏列数大于数据列数，则用null作为该列值</li>
	 * <li>5. 假如标题栏名称重复，则加上标题栏索引值作为后缀</li>
	 * <li>6. 假如时标题行，则key=标题栏名称，value=标题栏名称，key可能会加上列索引作为后缀</li>
	 * </ul>
	 * 
	 * @return 转换成的map key=标题栏名称[+列索引], value=列值
	 */
	public Map<String, String> rowDataToMap() {
		headerMap = getHeaderMap();
		int rowDatasize = getAllColumnCount();
		int headerSize = headerMap == null ? 0 : headerMap.size();
		// 取标题栏与数据栏列数的最大值
		int maxSize = Math.max(rowDatasize, headerSize);
		// 取标题栏与数据栏列数的最小大值
		int minSize = Math.min(headerSize, rowDatasize);
		Map<String, String> map = new HashMap<String, String>(maxSize);
		// 处理相同列数情况的数据
		for (int i = 0; i < minSize; i++) {
			String title = headerMap.get(i);
			if (map.containsKey(title)) {
				title += i;
			}
			map.put(title, rowData.get(i));
		}

		// 标题栏列数小于数据列数，则用索引值扩充key值
		for (int i = headerSize; i < rowDatasize; i++) {
			map.put(String.valueOf(i), rowData.get(i));
		}

		// 题栏列数大于数据列数，则用null作为该列值
		for (int i = rowDatasize; i < headerSize; i++) {
			String title = headerMap.get(i);
			if (map.containsKey(title)) {
				title += i;
			}
			map.put(title, null);
		}
		return map;
	}
	
	/**
	 * 是否是空行
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		if (rowData == null) {
			return true;
		}
		for (String s : rowData) {
			if (!XLPStringUtil.isEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the config
	 */
	public CSVReaderConfig getConfig() {
		return config;
	}
	
	/**
	 * 设置config
	 */
	public void setConfig(CSVReaderConfig config) {
		if (config == null) {
			config = new CSVReaderConfig();
		}
		this.config = config; 
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSVRow [lineNo=").append(lineNo).append(", rowData=").append(rowData)
			.append(", isHeader=").append(isHeader).append("]");
		return builder.toString();
	}
}