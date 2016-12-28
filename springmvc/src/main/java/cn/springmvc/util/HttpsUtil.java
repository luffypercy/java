package cn.springmvc.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpsUtil
 * @Description: http工具类
 * @author ready likun_557@126.com
 * @date 2014-10-18 下午9:36:49
 */
public class HttpsUtil {
	private static final Log logger = LogFactory.getLog(HttpsUtil.class);

	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String PATH_SIGN = "/";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String CONTENT_TYPE = "Content-Type";

	public static final int CONNECTION_TIMEOUT = 10000;
	public static final int SO_TIMEOUT = 30000;
	public static final int SO_TIMEOUT_60S = 60000;

	public static final int BUFFER = 1024;

	public static class UploadFileModel {

		private InputStream in;
		private String filename;

		public UploadFileModel() {
		}

		public UploadFileModel(InputStream in, String filename) {
			this.in = in;
			this.filename = filename;
		}

		public InputStream getIn() {
			return in;
		}

		public String getFilename() {
			return filename;
		}

	}

	/**
	 * HttpResponse包装类
	 * 
	 * @author ready
	 */
	public static class HttpResponseWrapper {
		private HttpResponse httpResponse;
		private HttpClient httpClient;

		public HttpResponseWrapper(HttpClient httpClient, HttpResponse httpResponse) {
			this.httpClient = httpClient;
			this.httpResponse = httpResponse;
		}

		public HttpResponseWrapper(HttpClient httpClient) {
			this.httpClient = httpClient;
		}

		public HttpResponse getHttpResponse() {
			return httpResponse;
		}

		public void setHttpResponse(HttpResponse httpResponse) {
			this.httpResponse = httpResponse;
		}

		/**
		 * 获得流类型的响应
		 */
		public InputStream getResponseStream() throws IllegalStateException, IOException {
			return httpResponse.getEntity().getContent();
		}

		/**
		 * 获得字符串类型的响应
		 */
		public String getResponseString(String responseCharacter) throws ParseException, IOException {
			HttpEntity entity = getEntity();
			String responseStr = EntityUtils.toString(entity, responseCharacter);
			if (entity.getContentType() == null) {
				responseStr = new String(responseStr.getBytes("iso-8859-1"), responseCharacter);
			}
			EntityUtils.consume(entity);
			return responseStr;
		}

		public String getResponseString() throws ParseException, IOException {
			return getResponseString(CHARACTER_ENCODING);
		}

		/**
		 * 获得响应状态码
		 */
		public int getStatusCode() {
			return httpResponse.getStatusLine().getStatusCode();
		}

		/**
		 * 获得响应状态码并释放资源
		 */
		public int getStatusCodeAndClose() {
			close();
			return getStatusCode();
		}

		public HttpEntity getEntity() {
			return httpResponse.getEntity();
		}

		/**
		 * 释放资源
		 */
		public void close() {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * POST方式提交上传文件请求
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostUpload(String url, Map<String, File> requestFiles, Map<String, Object> requestParas, String requestCharacter) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		HttpPost httpPost = new HttpPost(url);
		MultipartEntity multipartEntity = new MultipartEntity();
		if (requestFiles == null || requestFiles.size() == 0) {
			return null;
		}
		for (Map.Entry<String, File> entry : requestFiles.entrySet()) {
			multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue(), "application/octet-stream", requestCharacter));
		}
		if (requestParas != null && requestParas.size() > 0) {
			// 对key进行排序
			List<String> keys = new ArrayList<String>(requestParas.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				Object value = requestParas.get(key);
				if (value != null && value.getClass().isArray()) {
					Object[] obs = (Object[]) value;
					for (Object object : obs) {
						multipartEntity.addPart(key, new StringBody(StringUtil.convertObjToString(object), Charset.forName(requestCharacter)));
					}
				} else {
					multipartEntity.addPart(key, new StringBody(StringUtil.convertObjToString(value), Charset.forName(requestCharacter)));
				}
			}
		}
		httpPost.setEntity(multipartEntity);
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * POST方式提交上传文件请求
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostUpload(String url, Map<String, File> requestFiles, Map<String, Object> requestParas) throws Exception {
		return requestPostUpload(url, requestFiles, requestParas, CHARACTER_ENCODING);
	}

	/**
	 * POST方式提交上传文件请求
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostUploadByIs(String url, Map<String, UploadFileModel> requestInps, Map<String, Object> requestParas, String requestCharacter)
			throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		HttpPost httpPost = new HttpPost(url);
		MultipartEntity multipartEntity = new MultipartEntity();
		for (Map.Entry<String, UploadFileModel> entry : requestInps.entrySet()) {
			multipartEntity.addPart(entry.getKey(), new InputStreamBody(entry.getValue().getIn(), entry.getValue().getFilename()));
		}
		if (requestParas != null && requestParas.size() > 0) {
			// 对key进行排序
			List<String> keys = new ArrayList<String>(requestParas.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				Object value = requestParas.get(key);
				if (value != null && value.getClass().isArray()) {
					Object[] obs = (Object[]) value;
					for (Object object : obs) {
						multipartEntity.addPart(key, new StringBody(StringUtil.convertObjToString(object), Charset.forName(requestCharacter)));
					}
				} else {
					multipartEntity.addPart(key, new StringBody(StringUtil.convertObjToString(value), Charset.forName(requestCharacter)));
				}
			}
		}
		httpPost.setEntity(multipartEntity);
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * POST方式提交上传文件请求
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostUploadByIs(String url, Map<String, UploadFileModel> requestInps, Map<String, Object> requestParas) throws Exception {
		return requestPostUploadByIs(url, requestInps, requestParas, CHARACTER_ENCODING);
	}

	/**
	 * POST方式提交表单数据，返回响应对象
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostFormResponse(String url, Map<String, Object> requestParas, String requestCharacter) throws Exception {
		return requestPostFormResponse(url, requestParas, requestCharacter, null);
	}

	/**
	 * POST方式提交表单数据，返回响应对象
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostFormResponse(String url, Map<String, Object> requestParas, String requestCharacter, Map<String, String> headerParas)
			throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formParams = initNameValuePair(requestParas);
		httpPost.setEntity(new UrlEncodedFormEntity(formParams, requestCharacter));
		if (headerParas != null) {
			for (Entry<String, String> header : headerParas.entrySet()) {
				httpPost.setHeader(header.getKey(), header.getValue());
			}
		}
		// 执行POST请求
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * 自定义操作表单提交的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostFormResponse2(String url, Map<String, Object> requestParas, int timeOut) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient(CONNECTION_TIMEOUT, timeOut * 1000);
		} else {
			client = createHttpClient(CONNECTION_TIMEOUT, timeOut * 1000);
		}
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formParams = initNameValuePair(requestParas);
		httpPost.setEntity(new HttpParamEncodeEntity(formParams, CHARACTER_ENCODING));
		// 执行POST请求
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * POST方式提交表单数据，返回响应对象，utf8编码
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostFormResponse(String url, Map<String, Object> requestParas) throws Exception {
		return requestPostFormResponse(url, requestParas, CHARACTER_ENCODING);
	}

	/**
	 * POST方式提交表单数据，不会自动重定向
	 * 
	 * @throws Exception
	 */
	public static String requestPostForm(String url, Map<String, Object> requestParas, String requestCharacter, String responseCharacter) throws Exception {
		HttpResponseWrapper httpResponseWrapper = null;
		try {
			httpResponseWrapper = requestPostFormResponse(url, requestParas, requestCharacter);
			return httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (httpResponseWrapper != null) {
				httpResponseWrapper.close();
			}
		}
	}

	/**
	 * POST方式提交表单数据，不会自动重定向
	 * 
	 * @throws Exception
	 */
	public static String requestPostForm(String url, Map<String, Object> requestParas) throws Exception {
		return requestPostForm(url, requestParas, CHARACTER_ENCODING, CHARACTER_ENCODING);
	}

	public static HttpResponseWrapper requestGetResponse(String url) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = client.execute(httpGet);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param xForWardedFor
	 * @return
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestGetResponse(String url, String xForWardedFor) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		HttpGet httpGet = new HttpGet(url);
		if (StringUtil.isNotEmpty(xForWardedFor)) {
			httpGet.addHeader("X-Forwarded-For", xForWardedFor);
		}
		HttpResponse httpResponse = client.execute(httpGet);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * GET方式提交URL请求，会自动重定向
	 * 
	 * @throws Exception
	 */
	public static String requestGet(String url, String responseCharacter, String xForWardedFor) throws Exception {
		HttpResponseWrapper httpResponseWrapper = null;
		try {
			httpResponseWrapper = requestGetResponse(url, xForWardedFor);
			return httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (httpResponseWrapper != null) {
				httpResponseWrapper.close();
			}
		}
	}

	/**
	 * GET方式提交URL请求，会自动重定向
	 * 
	 * @throws Exception
	 */
	public static String requestGet(String url) throws Exception {
		return requestGet(url, CHARACTER_ENCODING, null);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求url(eg:https://api.lvmama.com/user/getUserInfo)
	 * @param params
	 *            参数
	 * @return 响应信息
	 * @throws Exception
	 */
	public static String requestGet(String url, Map<String, Object> params) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?").append(mapToUrl(params));
		return requestGet(urlBuilder.toString(), CHARACTER_ENCODING, null);
	}

	/**
	 * get方式提交表单数据，返回响应对象
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestGet(String url, Map<String, Object> requestParas, Map<String, String> headerParas) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient();
		} else {
			client = createHttpClient();
		}
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?").append(mapToUrl(requestParas));
		HttpGet httpGet = new HttpGet(urlBuilder.toString());
		if (headerParas != null) {
			for (Entry<String, String> header : headerParas.entrySet()) {
				httpGet.setHeader(header.getKey(), header.getValue());
			}
		}
		// 执行POST请求
		HttpResponse httpResponse = client.execute(httpGet);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * 将Map中的数据组装成url
	 * 
	 * @param params
	 * @return
	 */
	public static String mapToUrl(Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		try {
			boolean isFirst = true;
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value != null && value.getClass().isArray()) {
					Object[] obs = (Object[]) value;
					for (Object object : obs) {
						if (isFirst) {
							sb.append(key + "=" + URLEncoder.encode(StringUtil.convertObjToString(object), "utf-8"));
							isFirst = false;
						} else {
							if (object != null) {
								sb.append("&" + key + "=" + URLEncoder.encode(StringUtil.convertObjToString(object), "utf-8"));
							} else {
								sb.append("&" + key + "=");
							}
						}
					}
				} else {
					if (isFirst) {
						sb.append(key + "=" + URLEncoder.encode(StringUtil.convertObjToString(value), "utf-8"));
						isFirst = false;
					} else {
						if (value != null) {
							sb.append("&" + key + "=" + URLEncoder.encode(StringUtil.convertObjToString(value), "utf-8"));
						} else {
							sb.append("&" + key + "=");
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * GET方式提交URL请求，会自动重定向
	 * 
	 * @throws Exception
	 */
	public static String proxyRequestGet(String url, String xForWardedFor) throws Exception {
		return requestGet(url, CHARACTER_ENCODING, xForWardedFor);
	}

	/**
	 * POST方式提交非表单数据，返回响应对象
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostData(String url, String data, String contentType, String requestCharacter, int connectionTimeout, int soTimeout) throws Exception {
		HttpClient client = null;
		if (url.startsWith("https")) {
			client = createHttpsClient(connectionTimeout, soTimeout);
		} else {
			client = createHttpClient(connectionTimeout, soTimeout);
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(CONTENT_TYPE, contentType);
		httpPost.setEntity(new StringEntity(data, requestCharacter));
		HttpResponse httpResponse = client.execute(httpPost);
		return new HttpResponseWrapper(client, httpResponse);
	}

	/**
	 * POST方式提交非表单数据，返回响应对象
	 * 
	 * @throws Exception
	 */
	public static HttpResponseWrapper requestPostData(String url, String data, String contentType, String requestCharacter) throws Exception {
		return requestPostData(url, data, contentType, requestCharacter, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	/**
	 * POST非表单方式提交XML数据
	 */
	public static String requestPostXml(String url, String xmlData, String requestCharacter, String responseCharacter) {
		HttpResponseWrapper httpResponseWrapper = null;
		try {
			String contentType = "text/xml; charset=" + requestCharacter;
			httpResponseWrapper = requestPostData(url, xmlData, contentType, requestCharacter);
			return httpResponseWrapper.getResponseString(responseCharacter);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (httpResponseWrapper != null) {
				httpResponseWrapper.close();
			}
		}
		return null;
	}

	/**
	 * POST非表单方式提交XML数据
	 */
	public static String requestPostXml(String url, String xmlData) {
		return requestPostXml(url, xmlData, CHARACTER_ENCODING, CHARACTER_ENCODING);
	}

	public static HttpClient createHttpClient(int connectionTimeout, int soTimeout) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		return httpClient;
	}

	public static HttpClient createHttpClient() {
		return createHttpClient(CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static HttpClient createHttpsClient(int connectionTimeout, int soTimeout) throws Exception {
		try {
			HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
			HttpParams params = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
			HttpConnectionParams.setSoTimeout(params, soTimeout);
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { new TrustAnyTrustManager() }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			return httpClient;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static HttpClient createHttpsClient() throws Exception {
		return createHttpsClient(CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static List<NameValuePair> initNameValuePair(Map<String, Object> params) {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			// 对key进行排序
			List<String> keys = new ArrayList<String>(params.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				Object value = params.get(key);
				if (value != null && value.getClass().isArray()) {
					Object[] obs = (Object[]) value;
					for (Object object : obs) {
						formParams.add(new BasicNameValuePair(key, StringUtil.convertObjToString(object)));
					}
				} else {
					formParams.add(new BasicNameValuePair(key, StringUtil.convertObjToString(value)));
				}
			}
		}
		return formParams;
	}

	/**
	 * 通过httpclient下载的文件直接转为字节数组
	 * 
	 * @param path
	 * @author yanzhirong
	 * @return
	 * @throws Exception
	 */
	public static byte[] getHttpClientResponseByteArray(String path) throws Exception {
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		GetMethod httpGet = new GetMethod(path);

		byte[] byteArrays = new byte[0];
		try {
			client.executeMethod(httpGet);
			InputStream in = httpGet.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte[] b = new byte[BUFFER];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			byteArrays = bos.toByteArray();
			in.close();
			bos.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			httpGet.releaseConnection();
		}
		return byteArrays;
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

}
