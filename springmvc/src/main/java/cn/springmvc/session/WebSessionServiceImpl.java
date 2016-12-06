package cn.springmvc.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.springmvc.util.ServletUtil;

/**
 * @ClassName: WebSessionServiceImpl
 * @Description: web容器默认的session管理
 * @author ready likun_557@163.com
 * @date 2014-8-18 上午10:08:36
 */
public class WebSessionServiceImpl implements ISessionService {

	private static WebSessionServiceImpl sessionService;

	private WebSessionServiceImpl() {

	}

	public static WebSessionServiceImpl getInstance() {
		if (sessionService == null) {
			synchronized (WebSessionServiceImpl.class) {
				if (sessionService == null) {
					sessionService = new WebSessionServiceImpl();
				}
			}
		}
		return sessionService;
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(WebSessionServiceImpl.class);

	@Override
	public int sessionTimeOut() {
		return ServletUtil.getSessionTimeOut();
	}

	@Override
	public Map<String, Object> getSessionMap(HttpServletRequest request,
			HttpServletResponse response) {
		return getSessionMap(request, response, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSessionMap(HttpServletRequest request,
			HttpServletResponse response, boolean notExistsIsCreate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> keys = request.getSession(notExistsIsCreate)
				.getAttributeNames();
		while (keys.hasMoreElements()) {
			String string = (String) keys.nextElement();
			map.put(string, request.getSession().getAttribute(string));
		}
		return map;
	}

	@Override
	public Map<String, Object> getSessionMap(String sessionId) {
		throw new RuntimeException("web session 不支持该方法");
	}

	@Override
	public void putSession(HttpServletRequest request,
			HttpServletResponse response, String key, Object obj) {
		request.getSession().setAttribute(key, obj);
	}

	@Override
	public Object getSession(HttpServletRequest request,
			HttpServletResponse response, String key) {
		return request.getSession().getAttribute(key);
	}

	@Override
	public void removeSession(HttpServletRequest request,
			HttpServletResponse response, String key) {
		request.getSession().removeAttribute(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cleanAllSession(HttpServletRequest request,
			HttpServletResponse response, boolean isRemoveSessionId) {
		Enumeration<String> keys = request.getSession().getAttributeNames();
		while (keys.hasMoreElements()) {
			String string = (String) keys.nextElement();
			request.getSession().removeAttribute(string);
		}
	}

	@Override
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		return request.getSession().getId();
	}

	@Override
	public String initSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		return request.getSession().getId();
	}

	@Override
	public void flush(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public Object getAttribute(HttpServletRequest request,
			HttpServletResponse response, String key) {
		return request.getSession().getAttribute(key);
	}

}
