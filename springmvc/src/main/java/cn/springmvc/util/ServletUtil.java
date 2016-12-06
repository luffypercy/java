package cn.springmvc.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.ServletRequestDataBinder;

import cn.springmvc.enums.Constant;
import cn.springmvc.enums.Constant.ContentTypeEnum;
import cn.springmvc.enums.Constant.HttpScopeEnum;
import cn.springmvc.enums.Constant.PropertiesFileEnum;
import cn.springmvc.session.ISessionService;
import cn.springmvc.session.SessionServiceFactory;
import cn.springmvc.session.SessionServiceFactory.SessionTypeEnum;


/**
 * web工具类
 * 
 * @author ready
 * @date 2014/6/6
 */
public class ServletUtil {

	private static Logger logger = Logger.getLogger(ServletUtil.class);

	/**
	 * 标志ajax请求的参数名称
	 */
	public static final String ISAJAX = "isajax";

	/**
	 * 标志jsonp回调函数的参数名称
	 */
	public static final String JSONP_CALLBACK = "callback";

	/**
	 * 页面url中回调的url参数名称
	 */
	public static final String returnUrl = "returnUrl";

	/**
	 * 资源文件中session失效时间对应的key
	 */
	public static final String SESSION_TIME_OUT = "SESSION_TIME_OUT";

	/**
	 * 保存在request中的操作结果的键的名称
	 */
	public final static String OPERATERESULT_REQUEST = "operateResult";

	/**
	 * 保存在request中的操作异常的键的名称
	 */
	public final static String OPERATEEXCEPTIONINFO_REQUEST = "OPERATEEXCEPTIONINFO_REQUEST";

	/**
	 * 用户在session中的信息的key
	 */
	public final static String USER_SESSION = "USER_SESSION";

	/**
	 * request中session的key
	 */
	public static final String SESSION_ATTRS_MAP = "SESSION_ATTRS_MAP";

	/**
	 * 每页显示最大数据量
	 */
	public final static int maxPageSize = 200;

	/**
	 * session默认失效时间(秒)，获取session的失效时间都通过getSessionTimeOut方法获取
	 */
	private static int sessionTimeOut = 10;

	/**
	 * session获取标志
	 */
	private static boolean sessionTimeFlag = true;

	/**
	 * springmvc请求后缀在web.properties中的key
	 */
	public final static String URLSTUFFKEY = "urlStuff";

	/**
	 * 获取某种范围(request,session,applicatioin)的值
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public static Object getValueFormScope(HttpServletRequest request,
			HttpServletResponse response, String key, HttpScopeEnum scope) {
		if (scope == HttpScopeEnum.REQUEST) {
			return request.getAttribute(key);
		} else if (scope == HttpScopeEnum.SESSION) {
			return getSession(request, response, key);
		} else if (scope == HttpScopeEnum.APPLICATION) {
			return request.getSession().getServletContext().getAttribute(key);
		}
		return null;
	}

	/**
	 * 
	 * 向某种范围(request,session.applicatioin)中存入值
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public static void putValueToScope(HttpServletRequest request,
			HttpServletResponse response, String key, Object value,
			HttpScopeEnum scope) {
		if (scope == HttpScopeEnum.REQUEST) {
			request.setAttribute(key, value);
		} else if (scope == HttpScopeEnum.SESSION) {
			putSession(request, response, key, value);
		} else if (scope == HttpScopeEnum.APPLICATION) {
			request.getSession().getServletContext().setAttribute(key, value);
		}
	}

	/**
	 * 获取request中参数的集合,如果有多个值的对于只取其中一个
	 * 
	 * @return
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		return getParameterMap(request, false);
	}

	/**
	 * 获取request中的参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,
			String paramName) {
		return (String) getParameterMap(request).get(paramName);
	}

	/**
	 * 获取request中的参数
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String[] getParameterValues(HttpServletRequest request,
			String paramName) {
		return (String[]) getParameterMap(request, true).get(paramName);
	}

	/**
	 * 获取request中参数集合，如果有多个值的，获取的是数组
	 * 
	 * @param request
	 * @param isParams
	 *            多个值的是否是数组
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> getParameterMap(
			HttpServletRequest request, boolean isParams) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Map paramMap = request.getParameterMap();
		for (Iterator<String> iter = paramMap.keySet().iterator(); iter
				.hasNext();) {
			String key = iter.next();
			Object value = null;
			Object obj = paramMap.get(key);
			if (isParams) {
				value = obj;
			} else {
				if ((obj.getClass().isArray()) && Array.getLength(obj) >= 1) {
					value = Array.get(obj, 0);
				}
			}
			if (value != null) {
				if (value.getClass().isArray()) {
					int len = Array.getLength(value);
					for (int i = 0; i < len; i++) {
						Object temp = Array.get(value, i);
						Array.set(value, i, temp != null ? temp.toString()
								.trim() : null);
					}
				} else {
					value = value != null ? value.toString().trim() : null;
				}
			}
			parameterMap.put(key, value);
		}
		return parameterMap;
	}

	/**
	 * 获取请求的参数，包含分页参数的初始化
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getPageParameterMap(
			HttpServletRequest request) {
		Map<String, Object> parameterMap = getParameterMap(request);
		return getPageParameterMap(parameterMap);
	}

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @param page
	 *            页码
	 * @param rows
	 *            每页数据量的大小
	 * @return
	 */
	public static Map<String, Object> getPageParameterMap(
			HttpServletRequest request, String page, String rows) {
		Map<String, Object> parameterMap = getParameterMap(request);
		parameterMap.put("page", page);
		parameterMap.put("rows", rows);
		return getPageParameterMap(parameterMap);
	}

	/**
	 * 获取请求的参数，包含分页参数的初始化
	 * 
	 * @param
	 * @return
	 */
	public static Map<String, Object> getPageParameterMap(
			Map<String, Object> parameterMap) {
		// 当前页码
		String page = parameterMap.containsKey("page") ? parameterMap.get(
				"page").toString() : null;
		// 每页显示数量
		String rows = parameterMap.containsKey("rows") ? parameterMap.get(
				"rows").toString() : null;
		if (StringUtil.isNotEmpty(page) && FrameUtil.isNumeric(page)) {
			int pg = Integer.valueOf(page);
			if (pg > 0) {
				parameterMap.put("page", Integer.valueOf(page));
			} else {
				parameterMap.put("page", 1);
			}
		} else {
			parameterMap.put("page", 1);
		}
		if (StringUtil.isNotEmpty(rows) && FrameUtil.isNumeric(rows)) {
			int rs = Integer.valueOf(rows);
			if (rs >= 1 && rs <= maxPageSize) {
				parameterMap.put("rows", rs);
			}
		} else {
			parameterMap.put("rows", PagerUtil.pageSize);
		}
		return parameterMap;
	}

	/**
	 * 获取请求的参数，如果参数中有分页的参数，则将其去掉
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMapNotPagerParam(
			HttpServletRequest request) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<String> parameternames = request.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String name = (String) parameternames.nextElement();
			parameterMap.put(name, request.getParameter(name));
		}
		parameterMap.remove("page");
		parameterMap.remove("rows");
		return parameterMap;
	}

	/**
	 * 向客户端输出数据
	 * 
	 * @param message
	 * @throws Exception
	 */
	public static void outputMessage(HttpServletResponse response,
			String message) throws Exception {
		outputMessage(response, message, ContentTypeEnum.TEXT_HTML);
	}

	/**
	 * 向客户端输出数据
	 * 
	 * @param response
	 * @param message
	 * @param contentTypeEnum
	 * @throws Exception
	 */
	public static void outputMessage(HttpServletResponse response,
			String message, ContentTypeEnum contentTypeEnum) throws Exception {
		outputMessage(response, message, contentTypeEnum.getValue());
	}

	/**
	 * 将一个对象以json格式输出到客户端
	 * 
	 * @param object
	 * @throws Exception
	 */
	public static void outputJsonMessage(HttpServletResponse response,
			Object object) throws Exception {
		outputMessage(response, FrameUtil.json(object),
				ContentTypeEnum.TEXT_JSON);
	}

	/**
	 * 判断当前请求是否是ajax请求,如果传入的参数isajax==1表示为ajax请求，其他非ajax请求
	 * 
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String isajax = request.getParameter(ServletUtil.ISAJAX);
		if (StringUtil.isNotEmpty(isajax)) {
			return "1".equals(isajax);
		} else {
			return "XMLHttpRequest".equalsIgnoreCase(request
					.getHeader("X-Requested-With"));
		}
	}

	/**
	 * 获取jsonp中callback对于的函数名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getJsonpCallFunName(HttpServletRequest request) {
		return request.getParameter(ServletUtil.JSONP_CALLBACK);
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName
	 *            文件名
	 * @param bs
	 *            文件数据
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletResponse response,
			String fileName, byte[] bs) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		if (bs != null) {
			response.getOutputStream().write(bs);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	/**
	 * 向客户端输出数据
	 * 
	 * @param message
	 * @throws Exception
	 */
	public static void outputMessage(HttpServletResponse response,
			String message, String contextType) throws IOException {
		response.setContentType(contextType);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(message);
	}

	/**
	 * 将一个对象以json格式输出到客户端，支持jsonp格式
	 * 
	 * @param object
	 * @throws Exception
	 */
	public static void outputJsonMessage(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		String json = FrameUtil.json(object);
		if (logger.isDebugEnabled()) {
			logger.debug("输出参数:\n" + json);
		}
		String jsonpCallFunName = getJsonpCallFunName(request);
		outputMessage(
				response,
				StringUtil.isNotEmpty(jsonpCallFunName) ? String.format(
						"%s(%s)", jsonpCallFunName, json) : json,
				ContentTypeEnum.TEXT_JSON);
	}

	/**
	 * 判断request中的一个参数是否不为空
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static boolean isNotEmpty(HttpServletRequest request,
			String paramName) {
		return StringUtil.isNotEmpty(request.getParameter(paramName));
	}

	/**
	 * 判断request中的一个参数是否是一个数字。参数必须在request中存在，若不存在将出现异常
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static boolean isNumber(HttpServletRequest request, String paramName) {
		return FrameUtil.isNumeric(request.getParameter(paramName));
	}

	/**
	 * 将request中的数据绑定到指定的类型之上
	 * 
	 * @param <T>
	 * @param request
	 *            httprequest请求
	 * @param classType
	 *            需要将请求绑定到的数据模型的类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Object> T bindRequestToModel(
			HttpServletRequest request, Class classType) {
		T model = (T) BeanUtils.instantiateClass(classType);
		new ServletRequestDataBinder(model).bind(request);
		return model;
	}

	/**
	 * 获取session失效时间，秒
	 * 
	 * @return
	 */
	public static int getSessionTimeOut() {
		if (sessionTimeFlag) {
			Object st = PropertiesCacheUtil.getValue(
					PropertiesFileEnum.CONST.getFilename(), SESSION_TIME_OUT);
			if (st != null) {
				sessionTimeOut = Integer.parseInt(st.toString());
			}
			sessionTimeFlag = false;
		}
		return sessionTimeOut;
	}

	/**
	 * 获取session中的所有键值
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String, Object> getSessionMap(HttpServletRequest request,
			HttpServletResponse response) {
		return getSessionService().getSessionMap(request, response);
	}

	/**
	 * @Title: getSessionAttribute
	 * @Description: 获取sesion中的key对应的值
	 * @param @param request
	 * @param @param response
	 * @param @param key
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object getSessionAttribute(HttpServletRequest request,
			HttpServletResponse response, String key) {
		return getSessionService().getAttribute(request, response, key);
	}

	public static Map<String, Object> getSessionMap(String sessionId) {
		return getSessionService().getSessionMap(sessionId);
	}

	/**
	 * 把对象放置到Session
	 * 
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void putSession(HttpServletRequest request,
			HttpServletResponse response, String key, Object obj) {
		getSessionService().putSession(request, response, key, obj);
	}

	/**
	 * 从Session中取对象
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSession(HttpServletRequest request,
			HttpServletResponse response, String key) {
		return getSessionService().getSession(request, response, key);
	}

	/**
	 * 从Session中删除对象
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeSession(HttpServletRequest request,
			HttpServletResponse response, String key) {
		getSessionService().removeSession(request, response, key);
	}

	/**
	 * 获取sessionid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		return getSessionService().getSessionId(request, response);
	}

	/**
	 * 初始化sessionId
	 * 
	 * @param request
	 * @param response
	 */
	public static void initSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		getSessionService().initSessionId(request, response);
		getSessionService().flush(request, response);
	}

	/**
	 * 获取主域名(.yijiedai.com)
	 * 
	 * @return
	 */
	public static String domainHost() {
		return PropertiesCacheUtil.getValue(
				PropertiesFileEnum.WEB.getFilename(), "domainHost").toString();
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		if (StringUtil.isEmpty(cookieName)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	/**
	 * 获取cookie的值
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		Cookie cookie = ServletUtil.getCookie(request, cookieName);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String domain,
			boolean httpOnly) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie != null) {
			addCookie(response, cookieName, cookie.getValue(), 0, domain,
					cookie.getPath(), httpOnly);
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效时间（秒）
	 * @param domain
	 *            域
	 * @param path
	 *            cookie的path
	 * @param httpOnly
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, Integer maxAge, String domain, String path,
			boolean httpOnly) {
		Cookie cookie = new Cookie(name, value);
		if (StringUtil.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		if (StringUtil.isNotEmpty(path)) {
			cookie.setPath(path);
		} else {
			cookie.setPath("/");
		}
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setSecure(false);
		response.addCookie(cookie);
	}

	/**
	 * 放入cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie的name
	 * @param value
	 *            值
	 * @param useHost
	 *            是否使用主域名
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, boolean useHost, boolean httpOnly) {
		addCookie(response, name, value, null, useHost, httpOnly);
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie的name
	 * @param value
	 *            cookie的值
	 * @param expirt
	 *            过期时间，秒
	 * @param useHost
	 *            是否使用主域名
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, Integer expirt, boolean useHost, boolean httpOnly) {
		String domain = useHost ? domainHost() : null;
		addCookie(response, name, value, expirt, domain, null, httpOnly);
	}

	/**
	 * 清除session所有数据
	 * 
	 * @param request
	 * @param response
	 * @param isRemoveSessionId
	 *            是否移除sessionid
	 */
	public static void cleanAllSession(HttpServletRequest request,
			HttpServletResponse response, boolean isRemoveSessionId) {
		getSessionService().cleanAllSession(request, response,
				isRemoveSessionId);
	}

	public static ISessionService getSessionService() {
			return SessionServiceFactory
					.getSessionService(SessionTypeEnum.WEBSESSION);
	}

	/**
	 * 返回用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = getIpAddr1(request);
		if (StringUtil.isNotEmpty(ip) && ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	private static String getIpAddr1(HttpServletRequest request) {
		// 微信站要通过这个参数来传递用户的ip地址
		String ip = request.getParameter("regip");
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("wl-proxy-client-ip");
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			if (StringUtil.isNotEmpty(ip)) {
				ip = ip.split(",")[0];
			}
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		} else {
			return disposeIp(ip);
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		} else {
			return disposeIp(ip);
		}
		return disposeIp(ip);
	}

	private static String disposeIp(String ip) {
		if (StringUtils.isBlank(ip)) {
			return ip;
		}

		if (ip.indexOf(",") != -1) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}

	public static void main(String[] args) {
		System.out.println("12".split(",")[0]);
		System.out.println("12,2".split(",")[0]);
		System.out.println(disposeIp("168.123.25.69,789.123.568.53"));
	}

	/**
	 * @Title: getRootPath
	 * @Description: 获取请求的跟路径
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getRootPath() {
		return FrameUtil.getResourceByFile(
				PropertiesFileEnum.WEB.getFilename(), "rootPath");
	}

	/**
	 * 获取springmvc请求的后缀
	 * 
	 * @return
	 */
	public static String getUrlStuff() {
		return PropertiesCacheUtil.getValue(
				Constant.PropertiesFileEnum.WEB.getFilename(), URLSTUFFKEY)
				.toString();
	}

	/**
	 * 获取完整的url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getUrl(String url, Map<String, Object> paramMap) {
		StringBuilder s = new StringBuilder();
		s.append(getRootPath()).append(url).append(getUrlStuff())
				.append(mapToRequestParam(paramMap));

		return s.toString();
	}

	public static String mapToRequestParam(Map<String, Object> paramMap) {
		StringBuilder s = new StringBuilder();
		if (paramMap != null && paramMap.size() >= 1) {
			s.append("?");
			int i = 0;
			for (Entry<String, Object> param : paramMap.entrySet()) {
				Object value = param.getValue();
				if (value != null) {
					if (value.getClass().isArray()) {
						Object[] obs = (Object[]) value;
						for (int j = 0; j < obs.length; j++) {
							s.append(param.getKey())
									.append("=")
									.append(StringUtil
											.convertObjToString(obs[j]));
							if (j < obs.length - 1) {
								s.append("&");
							}
						}
					} else {
						s.append(param.getKey())
								.append("=")
								.append(StringUtil.convertObjToString(param
										.getValue()));
					}
					if (i < paramMap.size() - 1) {
						s.append("&");
					}
				}
				i++;
			}
		}
		return s.toString();
	}

	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	/**
	 * 获取完整的url
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrl(String url) {
		return getUrl(url, null);
	}

	/**
	 * 获取完整url
	 * 
	 * @param url
	 * @param urlTypeEnum
	 * @param paramMap
	 * @param stuff
	 * @return
	 */
	public static String getUrl1(String url, UrlTypeEnum urlTypeEnum,
			Map<String, Object> paramMap, String stuff) {
		StringBuilder s = new StringBuilder();
		if (urlTypeEnum == UrlTypeEnum.FRONT) {
			return s.append(FrameUtil.getWebResource("front_rootPath"))
					.append(url)
					.append(FrameUtil.getWebResource("front_urlStuff"))
					.toString();
		}
		s.append(FrameUtil.getWebResource(urlTypeEnum.getResourcesKey()))
				.append(url);
		if (StringUtil.isNotEmpty(stuff)
				&& (UrlTypeEnum.ROOT.equals(urlTypeEnum)
						|| UrlTypeEnum.USER_HTTPS.equals(urlTypeEnum) || UrlTypeEnum.STATIC
							.equals(urlTypeEnum))) {
			s.append(stuff);
		}
		return s.toString();
	}

	/**
	 * 获取返回路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getReturnUrl(HttpServletRequest request) {
		return request.getParameter(ServletUtil.returnUrl);
	}

	public static enum UrlTypeEnum {
		ROOT(1, "rootPath", "动态资源前缀url"), USER_HTTPS(2, "userRootPathHTTPS",
				"用户中心"), STATIC(3, "staticRootPath", "静态资源http", false), STATIC_HTTPS(
				4, "staticRootPathHTTPS", "静态资源https", false), PICS(5,
				"picsRootPath", "图片http", false), PICS_HTTPS(6, "picsRootPath",
				"图片https", false), FRONT(7, "FRONT", "front的url"), yjd_gold_front_rootPath(
				8, "yjd_gold_front_rootPath", "医信宝");
		private Integer value;
		private String resourcesKey;
		private String description;
		// 是否追加springmvc后缀
		private Boolean appendStuff = Boolean.TRUE;

		private UrlTypeEnum(Integer value, String resourcesKey,
				String description) {
			this.value = value;
			this.resourcesKey = resourcesKey;
			this.description = description;
		}

		private UrlTypeEnum(Integer value, String resourcesKey,
				String description, Boolean appendStuff) {
			this.value = value;
			this.resourcesKey = resourcesKey;
			this.description = description;
			this.appendStuff = appendStuff;
		}

		public Integer getValue() {
			return value;
		}

		public String getResourcesKey() {
			return resourcesKey;
		}

		public String getDescription() {
			return description;
		}

		public Boolean getAppendStuff() {
			return appendStuff;
		}

		public void setAppendStuff(Boolean appendStuff) {
			this.appendStuff = appendStuff;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public void setResourcesKey(String resourcesKey) {
			this.resourcesKey = resourcesKey;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public static UrlTypeEnum get(int urltype) {
			UrlTypeEnum[] urlTypeEnums = UrlTypeEnum.values();
			for (UrlTypeEnum urlTypeEnum : urlTypeEnums) {
				if (urlTypeEnum.getValue().equals(urltype)) {
					return urlTypeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 获取请求真实的url（包含反向代理的情况）
	 * 
	 * @param request
	 * @return
	 */
	public static StringBuffer getRequestUrl(HttpServletRequest request) {
		// 反向代理会把用户请求的真是域名填充到http的header中
		String SERVER_NAME = request.getHeader("SERVER_NAME");
		if (StringUtil.isNotEmpty(SERVER_NAME)) {
			return new StringBuffer(SERVER_NAME)
					.append(request.getRequestURI());
		}
		/*
		 * StringBuffer r = new StringBuffer(); String realProtocal =
		 * request.getHeader("protocal"); if
		 * (StringUtil.isNotEmpty(realProtocal)) { r.append(realProtocal); }
		 * else { r.append(request.getScheme()); }
		 * r.append(":").append("//").append(request.getServerName())
		 * .append(request.getServletPath());
		 */
		return request.getRequestURL();
	}

	/**
	 * 获取请求的url，包含参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestUrlAndParam(HttpServletRequest request) {
		StringBuffer s = getRequestUrl(request).append(
				mapToRequestParam(ServletUtil.getParameterMap(request, true)));
		return s.toString();
	}

	/**
	 * 获取请求的完整路径，并带上查询参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestFullUrl(HttpServletRequest request) {
		StringBuffer r = getRequestUrl(request);
		if (StringUtil.isNotEmpty(request.getQueryString())) {
			r.append("?").append(request.getQueryString());
		}
		return r.toString();
	}

	private static String SESSION_ID_COOKIE_NAME;

	/**
	 * 获取session的cookie名称
	 * 
	 * @return
	 */
	public static String getSessionCookieName() {
		if (SESSION_ID_COOKIE_NAME == null) {
			synchronized (ServletUtil.class) {
				if (SESSION_ID_COOKIE_NAME == null) {
					SESSION_ID_COOKIE_NAME = FrameUtil
							.getConstResource("session_cookie_name");
				}
			}
		}
		return SESSION_ID_COOKIE_NAME;
	}

	/**
	 * 向html的head中放入title数据
	 * 
	 * @param request
	 * @param title
	 */
	public static void putHtmlHeadTitle(HttpServletRequest request, String title) {
		putHtmlHead(request, "TITLE", title);
	}

	/**
	 * 向html的head中放入数据
	 * 
	 * @param request
	 * @param name
	 */
	public static void putHtmlHead(HttpServletRequest request, String name,
			String value) {
		request.setAttribute("HEAD_" + name, value);
	}

	/**
	 * 获取请求的uri不包含contextpath
	 * 
	 * @param request
	 * @return
	 */
	public static String getRquestUriNotContainContextPath(
			HttpServletRequest request) {
		String contextpath = request.getContextPath();
		String uri = request.getRequestURI();
		if (StringUtil.isNotEmpty(contextpath) && uri.startsWith(contextpath)) {
			return uri.substring(contextpath.length());
		}
		return uri;
	}

	public static String getFrontUrl(String url) {
		String frontRootPath = FrameUtil.getWebResource("frontRootPath");
		String frontUrlStuff = FrameUtil.getWebResource("frontUrlStuff");
		return frontRootPath + url + frontUrlStuff;
	}

	/**
	 * 将session放入request中
	 * 
	 * @param request
	 * @param response
	 */
	public static void putRequestSession(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getAttribute(SESSION_ATTRS_MAP) == null) {
			request.setAttribute(SESSION_ATTRS_MAP, ServletUtil.getSessionMap(
					(HttpServletRequest) request,
					(HttpServletResponse) response));
		}
	}

}
