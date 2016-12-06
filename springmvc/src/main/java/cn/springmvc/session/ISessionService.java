package cn.springmvc.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session服务
 * 
 * @author ready
 * 
 */
public interface ISessionService {

	/**
	 * 获取session失效时间(秒)
	 * 
	 * @return
	 */
	public int sessionTimeOut();

	/**
	 * 获取session对应的值列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> getSessionMap(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 获取session中的值
	 * 
	 * @param request
	 * @param response
	 * @param notExistsIsCreate
	 *            如果session不存在是否创建
	 * @return
	 */
	public Map<String, Object> getSessionMap(HttpServletRequest request,
			HttpServletResponse response, boolean notExistsIsCreate);

	/**
	 * @param request
	 * @param 根据key获取值
	 * @return
	 */
	public Object getAttribute(HttpServletRequest request,
			HttpServletResponse response, String key);

	/**
	 * 根据sessionid获取session中的值
	 * 
	 * @param sessionId
	 * @return
	 */
	public Map<String, Object> getSessionMap(String sessionId);

	/**
	 * 把对象放置到Session
	 * 
	 * @param request
	 * @param key
	 * @param obj
	 */
	public void putSession(HttpServletRequest request,
			HttpServletResponse response, String key, Object obj);

	/**
	 * 从Session中取对象
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getSession(HttpServletRequest request,
			HttpServletResponse response, String key);

	/**
	 * 从Session中删除对象
	 * 
	 * @param request
	 * @param key
	 */
	public void removeSession(HttpServletRequest request,
			HttpServletResponse response, String key);

	/**
	 * 清除session所有数据
	 * 
	 * @param request
	 * @param response
	 * @param isRemoveSessionId
	 *            是否清除sessionid
	 */
	public void cleanAllSession(HttpServletRequest request,
			HttpServletResponse response, boolean isRemoveSessionId);

	/**
	 * 获取sessionid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 初始化sessionId，并返回sessionid
	 * 
	 * @param request
	 * @param response
	 */
	public String initSessionId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 刷新session
	 * 
	 * @param request
	 * @param response
	 */
	public void flush(HttpServletRequest request, HttpServletResponse response);
}
