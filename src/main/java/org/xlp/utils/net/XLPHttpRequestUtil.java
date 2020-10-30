package org.xlp.utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.xlp.utils.io.XLPIOUtil;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-5-5
 *         </p>
 * 
 *         https或http请求发送工具类
 *         <p>
 *         主要功能是发送https或http请求并一般以json格式字符串货xml格式字符串数据返回
 */
public class XLPHttpRequestUtil {
	// 默认字符编码
	public final static String DEFAULT_ENCODING = "utf-8";
	// post请求
	public final static String POST = "POST";
	// get请求
	public final static String GET = "GET";

	/**
	 * 获取响应的编码,如果没有获取到响应头编码,则默认用 urf8
	 * 
	 * @param connection
	 *            <code>URLConnection</code>
	 * @return 响应头编码
	 */
	public static String getResponseCharSet(URLConnection connection) {
		if (connection == null)
			throw new IllegalArgumentException("参数必须不为null");
		// 获取输入流,获取响应结果
		Map<String, List<String>> map = connection.getHeaderFields();
		List<String> headerList = map.get("Content-Type");
		if (headerList != null && headerList.size() > 0)
			for (String header : headerList) {
				if (header != null && header.indexOf("charset") > 0) {
					return header.split("=")[1];
				}
			}
		return DEFAULT_ENCODING;// 如果没有获取到响应头编码,则默认用 urf8
	}

	/**
	 * 发起https请求并获取结果 (一般是json格式字符串货xml格式字符串)
	 * 
	 * @param requestUrl
	 *            请求Url
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param data
	 *            提交的数据,后台需什么数据你就传什么格式数据，一般为json，xml，这样后台好解析你给的数据
	 * @param x509TrustManager
	 *            自定义的 X509证书管理器，如果该值设置为null，其使用默认 X509证书管理器
	 * @return 一般是json格式字符串货xml格式字符串,假如访问出错或无数据，返回""
	 */
	public static String httpsRequest(String requestUrl, String requestMethod,
			String data, X509TrustManager x509TrustManager) {
		OutputStream outputStream = null;
		HttpsURLConnection connection = null;
		try {
			X509TrustManager manager = x509TrustManager == null ? new XLPX509TrustManager()
					: x509TrustManager;
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { manager };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(ssf);

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//设置超时时间
			//setConnectTimeout：设置连接主机超时（单位：毫秒）
			//setReadTimeout：设置从主机读取数据超时（单位：毫秒）
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// 设置请求方式（GET/POST）
			requestMethod = requestMethod == null ? POST : requestMethod
					.toUpperCase(Locale.US);
			connection.setRequestMethod(requestMethod);

			connection.connect();
			// System.out.println(charSet);
			// 当有数据需要提交时
			if (null != data && requestMethod.equals(POST)) {
				outputStream = connection.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(data.getBytes(DEFAULT_ENCODING));
			}

			int code = connection.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				// 获取字符编码
				String charSet = XLPHttpRequestUtil
						.getResponseCharSet(connection);
				// 将返回的输入流转换成字符串
				InputStream inputStream = connection.getInputStream();

				return XLPIOUtil.toString(inputStream, charSet);
			} else {
				throw new Exception("响应码不为200为[" + code + "],获取相应数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
				outputStream = null;
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return "";
	}

	/**
	 * 发起https请求并获取结果 (一般是json格式字符串货xml格式字符串),其使用默认 X509证书管理器
	 * 
	 * @param requestUrl
	 *            请求Url
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param data
	 *            提交的数据,后台需什么数据你就传什么格式数据，一般为json，xml，这样后台好解析你给的数据
	 * @return 一般是json格式字符串货xml格式字符串,假如访问出错或无数据，返回""
	 */
	public static String httpsRequest(String requestUrl, String requestMethod,
			String data) {
		return httpsRequest(requestUrl, requestMethod, data, null);
	}

	/**
	 * 发起http请求并获取结果 (一般是json格式字符串货xml格式字符串)
	 * 
	 * @param requestUrl
	 *            请求Url
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param data
	 *            提交的数据,后台需什么数据你就传什么格式数据，一般为json，xml，这样后台好解析你给的数据
	 * @return 一般是json格式字符串货xml格式字符串,假如访问出错或无数据，返回""
	 */
	public static String httpRequest(String requestUrl, String requestMethod,
			String data) {
		OutputStream outputStream = null;
		HttpURLConnection connection = null;
		try {

			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//设置超时时间
			//setConnectTimeout：设置连接主机超时（单位：毫秒）
			//setReadTimeout：设置从主机读取数据超时（单位：毫秒）
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// 设置请求方式（GET/POST）
			requestMethod = requestMethod == null ? POST : requestMethod
					.toUpperCase(Locale.US);
			connection.setRequestMethod(requestMethod);

			connection.connect();
			// System.out.println(charSet);
			// 当有数据需要提交时
			if (null != data && requestMethod.equals(POST)) {
				outputStream = connection.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(data.getBytes(DEFAULT_ENCODING));
			}

			int code = connection.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				// 获取字符编码
				String charSet = XLPHttpRequestUtil
						.getResponseCharSet(connection);
				// 将返回的输入流转换成字符串
				InputStream inputStream = connection.getInputStream();
				return XLPIOUtil.toString(inputStream, charSet);
			} else {
				throw new Exception("响应码不为200为[" + code + "],获取相应数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
				outputStream = null;
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return "";
	}
}
