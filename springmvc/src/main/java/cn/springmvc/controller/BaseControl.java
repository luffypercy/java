package cn.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import cn.springmvc.dto.OperParamDto;
import cn.springmvc.enums.Constant.ResultEnum;
import cn.springmvc.model.Model;
import cn.springmvc.model.ResultModel;
import cn.springmvc.util.AdminDataUtil;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.ServletUtil;
import cn.springmvc.util.StringUtil;


/**
 * <b>description</b>：control的基类 <br>
 * <b>time</b>：2014-11-3下午3:47:57 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public abstract class BaseControl {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	public static final String RESULT_PAGE = "/exception/operateException";
	
	/**
	 * 获取操作信息
	 * 
	 * @return
	 */
	public OperParamDto getOperParamDto() {
		return AdminDataUtil.getOperParamDto(request, response);
	}
	
	/**
	 * 将request中的数据绑定到当前control对应的model中
	 * 
	 * @param request
	 * @return
	 */
	public <T extends Model> T getModelByRequest(HttpServletRequest request) {
		return ServletUtil.bindRequestToModel(request, getModelType());
	}

	/**
	 * 获取当前control对应的model的类型
	 * 
	 * @return
	 */
	public Class<?> getModelType() {
		return null;
	}

	/**
	 * @Title: getSessionCachedValue
	 * @Description: 获取session中的值
	 * @param key
	 * @return Object
	 */
	public Object getSessionCached(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return ServletUtil.getSessionAttribute(request, response, key);
	}

	/**
	 * @Title: setSessionCached
	 * @Description: 向session中放入对象
	 * @param key
	 * @param value
	 * @return void
	 */
	public void setSessionCached(String key, Object value) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		ServletUtil.putSession(request, response, key, value);
	}

	/**
	 * @Title: setReqAndResp
	 * @Description: 初始化ServletAPI
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 判断是否是get方法
	 * 
	 * @param request
	 * @return boolean
	 */
	protected boolean isGet(HttpServletRequest request) {
		String method = request.getMethod();
		if ("GET".equalsIgnoreCase(method)) {
			return true;
		}
		return false;
	}

	/**
	 * 跳转到正确结果页面
	 * 
	 * @param referer
	 *            回调url，可以为空
	 * @param msgKey
	 *            消息在default.properties文件中的key
	 * @param msgKeyparam
	 *            msgKey中的占位符替换对象
	 * @return
	 */
	public String toSuccessPage(String referer, String msgKey,
			Object... msgKeyparam) {
		ResultModel resultModel = new ResultModel(ResultEnum.SUCCESS, null,
				FrameUtil.getResource(msgKey, msgKeyparam), referer);
		request.setAttribute("result", resultModel.toMap());
		return RESULT_PAGE;
	}

	/**
	 * 跳转到错误结果页面
	 * 
	 * @param referer
	 *            回调url，可以为空
	 * @param msgKey
	 *            消息在default.properties文件中的key
	 * @param msgKeyparam
	 *            msgKey中的占位符替换对象
	 * @return
	 */
	public String toErrorPage(String referer, String msgKey,
			Object... msgKeyparam) {
//		ResultModel resultModel = new ResultModel(ResultEnum.ERROR, null,
//				FrameUtil.getResource(msgKey, msgKeyparam), referer);
//		request.setAttribute("result", resultModel.toMap());
//		return RESULT_PAGE;
		return toErrorPageMoreTime(referer,msgKey,2,msgKeyparam);
	}
	
	/**
	 * 
	 * @param referer  回调url，可以为空
	 * @param msgKey  回调url，可以为空
	 * @param time 指定页面停留的秒数
	 * @param msgKeyparam
	 * @return
	 */
	public String toErrorPageMoreTime(String referer, String msgKey,int time,
			Object... msgKeyparam) {
		ResultModel resultModel = new ResultModel(ResultEnum.ERROR, null,
				FrameUtil.getResource(msgKey, msgKeyparam), referer);
		request.setAttribute("result", resultModel.toMap());
		request.setAttribute("waitSeconds",time);
		return RESULT_PAGE;
	}

	/**
	 * 向客户端输出成功信息
	 * 
	 * @param referer
	 *            回调url，可以为空
	 * @param msgKey
	 *            消息在default.properties文件中的key
	 * @param msgKeyparam
	 *            msgKey中的占位符替换对象
	 * @throws Exception
	 */
	public void successMsg(String referer, String msgKey, Object... msgKeyparam)
			throws Exception {
		this.msg(ResultEnum.SUCCESS, null, referer,
				FrameUtil.getResource(msgKey, msgKeyparam));
	}

	/**
	 * 向客户端输出成功信息
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void successMsg(String msg) throws Exception {
		ServletUtil.outputJsonMessage(request, response, new ResultModel(
				ResultEnum.SUCCESS, null, msg, null).toMap());
	}
	
	/**
	 *  向客户端输出失败信息
	 * @param msg
	 * @throws Exception
	 */
	public void errorMsg(String msg) throws Exception {
		ServletUtil.outputJsonMessage(request, response, new ResultModel(
				ResultEnum.ERROR, null, msg, null).toMap());
	}


	/**
	 * 输出数据
	 * 
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void successData(Map data) throws Exception {
		ResultModel rm = new ResultModel(ResultEnum.SUCCESS, null, null, null);
		rm.setData(data);
		ServletUtil.outputJsonMessage(request, response, rm.toMap());
	}
	
	public void successDataPutAll(Map data) throws Exception {
		ResultModel rm = new ResultModel(ResultEnum.SUCCESS, null, null, null);
		rm.getData().putAll(data);
		ServletUtil.outputJsonMessage(request, response, rm.toMap());
	}

	/**
	 * 向客户端输出错误信息
	 * 
	 * @param referer
	 *            回调url，可以为空
	 * @param msgKey
	 *            消息在default.properties文件中的key
	 * @param msgKeyparam
	 *            msgKey中的占位符替换对象
	 * @throws Exception
	 */
	public void errorMsg(String referer, String msgKey, Object... msgKeyparam)
			throws Exception {
		this.msg(ResultEnum.ERROR, null, referer,msgKey, msgKeyparam);
	}

	/**
	 * 向客户端输出信息
	 * 
	 * @param resultEnum
	 * @param code
	 * @param referer
	 * @param msgKey
	 * @param msgKeyparam
	 * @throws Exception
	 */
	public void msg(ResultEnum resultEnum, String code, String referer,
			String msgKey, Object... msgKeyparam) throws Exception {
		ServletUtil.outputJsonMessage(request, response, new ResultModel(
				resultEnum, code, FrameUtil.getResource(msgKey, msgKeyparam),
				referer).toMap());
	}

	public String getParameter(String key) throws UnsupportedEncodingException {
		String pram = "";
		if (StringUtil.isNotEmpty(request.getParameter(key)))
			pram = new String(request.getParameter(key).getBytes("ISO-8859-1"),
					"utf-8");
		return pram;
	}


	/**
	 * 设置html的头部的title
	 * 
	 * @param title
	 * @param appendDefaultTitle
	 *            是否加上默认的title
	 */
	public void setHtmlTitle(String title, boolean appendDefaultTitle) {
		if (appendDefaultTitle) {
			ServletUtil.putHtmlHeadTitle(request,
					title + " -" + FrameUtil.getWebResource("title"));
		} else {
			ServletUtil.putHtmlHeadTitle(request, title);
		}
	}

//	/**
//	 * 获取登陆用户信息
//	 * 
//	 * @return
//	 */
//	protected UserSession getUserSession() {
//		return UserUtil.getUserModel(request, response, ClientChannelEnum.PIC);
//	}
//	
//	/**
//	* 获取用户id
//	* @return
//	 */
//	protected Long getSessionUserId(){
//		UserSession u = getUserSession();
//		return u!=null?u.getUser_id():null; 
//	}
//
//	/**
//	 * 用户是否登陆了
//	 * 
//	 * @return
//	 */
//	protected boolean userIsLogin() {
//		return this.getUserSession() != null;
//	}
	
	/**
	 * 提现角色列表的key
	 */
	private static final String CASH_ROLE = "cash_role";
	
//	/**
//	 * 判断用户是否有某个操作的权限
//	* @param code 权限编码
//	* @return boolean
//	 * @throws Exception 
//	 */
//	protected boolean hasAccessRight(String code) throws Exception{
//		
//		
//		
//		if(StringUtils.isBlank(code)){
//			FrameUtil.throwBaseException1("参数不完整");
//		}
//		OperParamDto oprate = AdminDataUtil.getOperParamDto(request, response);
//		if(oprate == null){
//			FrameUtil.throwBaseException1("请登录");
//		}
//		List<RoleModel> roles = (List<RoleModel>) CachedUtil.defaultCached().get(CASH_ROLE+ServletUtil.getSessionId(request, response));
//		//如果memcached没有则查数据库
//		if(roles == null  || roles.isEmpty()){
//			Long admin_id = oprate.getAdmin_id();
//			//获取用户角色列表
//			roles = roleService.getUserRole(admin_id.intValue());
//			CachedUtil.defaultCached().set(CASH_ROLE+ServletUtil.getSessionId(request, response), roles);
//		}
//		
//		if(roles.isEmpty()){
//			return false;
//		}
//		
//		for (RoleModel roleModel : roles) {
//			if(code.equals(roleModel.getRole())){
//				return true;
//			}
//		}
//		return false;
//		
//	}
	
}
