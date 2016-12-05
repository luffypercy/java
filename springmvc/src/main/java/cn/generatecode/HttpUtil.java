package cn.generatecode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Http工具类
 */
public class HttpUtil {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "";

	/**
	 * get请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(url);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			HttpEntity entity = httpResponse.getEntity();
			InputStream content = entity.getContent();
			return convertStreamToString(content);
		}
		return null;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> rawParams)
			throws Exception {
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			HttpEntity entity = httpResponse.getEntity();
			InputStream content = entity.getContent();
			return convertStreamToString(content);
		}
		return null;
	}

	/**
	 * 获取服务器的响应，转换为字符串
	 */
	private static String convertStreamToString(InputStream is) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			byte[] bs = new byte[4048];
			int len = 0;
			while ((len = is.read(bs)) != -1) {
				bo.write(bs, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bo.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new String("%E6%9D%8E%E5%9D%A4"
				.getBytes("iso-8859-1"), "UTF-8"));
		System.out.println(URLDecoder.decode("%E6%9D%8E%E5%9D%A4", "UTF-8"));
		System.out
				.println(URLDecoder
						.decode("hxx%3A%7B%22lastdate%22%3A%221418885432%22%2C%22lastip%22%3A%22101.231.48.250%22%2C%22sex%22%3A%221%22%2C%22user_id%22%3A%22114953%22%2C%22password%22%3A%22cf1e505b19faeb451b8c4b65e9aa7641%22%2C%22uname%22%3A%22%25E6%259D%258E%25E5%259D%25A4%22%7D",
								"UTF-8"));
	}
}